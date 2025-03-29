package org.jeecg.modules.rider.pay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.order.entity.RiderPayOrder;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.jeecg.modules.rider.order.service.IRiderPayOrderService;
import org.jeecg.modules.rider.order.service.IRiderUserOrderService;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.BaseErrorCodeEnum;
import org.jeecg.modules.rider.pay.constants.EventTypeEnum;
import org.jeecg.modules.rider.pay.constants.TradeStateEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.entity.*;
import org.jeecg.modules.rider.pay.exception.WechatPayException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 微信支付异步回调通知处理
 */
@Slf4j
@Service
public class WeChatPayNotifyInvoke {

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;

    @Resource
    private IRiderUserOrderService tenantOrderService;

    @Resource
    private IRiderPayOrderService payOrderinfoService;

    @Resource
    private IRiderCustomerService sysUserService;

    @SneakyThrows
    public ResponseSignVerifyParams getRequestParam(HttpServletRequest request) {
        log.info("微信支付异步回调解析参数");
        // 获取请求header信息
        String serial = request.getHeader("Wechatpay-Serial");
        String signature = request.getHeader("Wechatpay-Signature");
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        String nonce = request.getHeader("Wechatpay-Nonce");
        // 获取请求body
        String body = request.getReader().lines().collect(Collectors.joining());
        // 对请求头进行验签 以确保是微信服务器的调用
        ResponseSignVerifyParams params = new ResponseSignVerifyParams();
        params.setWechatpaySerial(serial);
        params.setWechatpaySignature(signature);
        params.setWechatpayTimestamp(timestamp);
        params.setWechatpayNonce(nonce);
        params.setBody(body);
        // 是否开启验签
        if (!wxpayServiceConfig.isOpenSignCheck()) {
            return params;
        }
        // 对参数进行验签
        String message = timestamp + "\n" + nonce + "\n" + body + "\n";
        //验签不通过
        if (!wxpayServiceConfig.getValidator().verify(serial, message.getBytes(StandardCharsets.UTF_8), signature)) {
            log.info("微信支付异步回调参数验签失败!回调参数:{}",params.toString());
            throw new WechatPayException(BaseErrorCodeEnum.PARAM_ERROR.getStatus(), "微信支付异步回调参数验签失败!" );
        }
        return params;
    }

    @SneakyThrows
    public void execNotify(ResponseSignVerifyParams params) {
        log.info("微信支付异步回调信息：{}",params.getBody());
        ObjectMapper objectMapper = WechatPayContants.OBJECT_MAPPER;
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        CallbackParams callbackParams = objectMapper.readValue(params.getBody(), CallbackParams.class);
        if (!Objects.equals(callbackParams.getEventType(), EventTypeEnum.TRANSACTION.getEnvent())) {
            log.info("微信异步回调通知类型不匹配, callbackParams {}", callbackParams);
            throw new JeecgBootException("微信异步回调通知类型不匹配！");
        }
        // 1.解密响应数据
        CallbackParams.Resource resource = callbackParams.getResource();
        String data = wxpayServiceConfig.getAesUtil().decryptToString(
                resource.getAssociatedData().replace("\"", "")
                        .getBytes(StandardCharsets.UTF_8),
                resource.getNonce().replace("\"", "")
                        .getBytes(StandardCharsets.UTF_8),
                resource.getCiphertext().replace("\"", ""));
        CallbackDecryptData consumeData = objectMapper.readValue(data, CallbackDecryptData.class);
        // 2.获取交易状态
        TradeStateEnum tradeStateEnum = TradeStateEnum.getEnum(consumeData.getTradeState());
        if(Objects.isNull(tradeStateEnum)){
            throw new WechatPayException(BaseErrorCodeEnum.TRADE_STATE_NOT_EXSIT, consumeData.getTradeState());
        }
        // 3.根据租户订单号校验订单真实性
        RiderUserOrder tenantOrder = tenantOrderService.getByOutTradeNo(consumeData.getOutTradeNo());
        if(Objects.isNull(tenantOrder)){
            throw new WechatPayException(BaseErrorCodeEnum.ORDER_NO_TEXIST.getStatus(), String.format("用户订单【%s】不存在", consumeData.getOutTradeNo()));
        }
        RiderPayOrder payOrderinfo = payOrderinfoService.getByOutTradeNo(consumeData.getOutTradeNo());
        if(Objects.isNull(payOrderinfo)){
            throw new WechatPayException(BaseErrorCodeEnum.ORDER_NO_TEXIST.getStatus(), String.format("微信支付订单【%s】不存在或已关闭", consumeData.getOutTradeNo()));
        }
        // 4.查询订单是否已处理过（如果微信支付返回状态与本地数据库状态一致则视为已经处理过该订单）
        if(Objects.equals(tradeStateEnum.getStatus(),payOrderinfo.getTradeState())){
            log.error("微信支付重复回调，该订单本地状态与回调状态一致，无需处理！！！");
            throw new WechatPayException(BaseErrorCodeEnum.CALLBACK_HANDLED, consumeData.getOutTradeNo());
        }
        // 5.根据openid获取支付人信息
        RiderCustomer user = sysUserService.getByOpenId(consumeData.getPayer().getOpenid());
        if(Objects.nonNull(user)){
            tenantOrder.setPayId(user.getId());
            tenantOrder.setPayer(user.getName());
        }
        // 6.更新支付订单、租户订单及租户信息
        payOrderinfoService.updateOrderinfo(tenantOrder,payOrderinfo,consumeData);
    }

}
