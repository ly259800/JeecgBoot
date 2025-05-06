package org.jeecg.modules.rider.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.order.entity.RiderPayOrder;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.jeecg.modules.rider.order.service.IRiderPayOrderService;
import org.jeecg.modules.rider.order.service.IRiderUserOrderService;
import org.jeecg.modules.rider.pay.constants.BaseErrorCodeEnum;
import org.jeecg.modules.rider.pay.constants.TradeStateEnum;
import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.dto.*;
import org.jeecg.modules.rider.pay.enums.OrderStateEnum;
import org.jeecg.modules.rider.pay.exception.WechatPayException;
import org.jeecg.modules.rider.pay.service.WeChatPayApiInvoke;
import org.jeecg.modules.rider.pay.service.WeChatPayService;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.jeecg.modules.rider.pay.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
public class WeChatPayServiceImpl implements WeChatPayService {
    @Resource
    private WeChatPayApiInvoke weChatPayApiInvoke;
    @Resource
    private IRiderPayOrderService payOrderinfoService;
    @Resource
    private IRiderCustomerService riderCustomerService;
    @Resource
    private IRiderUserOrderService tenantOrderService;
    /**
     * 预下单处理
     * @param payDto
     * @throws Exception
     */
    @Override
    public Result preOrder(WechatPayDTO payDto) throws Exception{
        //1.参数校验
        if (!PriceUtils.checkZero(payDto.getTotalAmount())) {
            throw new WechatPayException(BaseErrorCodeEnum.PARAM_ERROR.getStatus(), "支付金额必须大于0！");
        }
        TradeTypeEnum tradeTypeEnum = TradeTypeEnum.getEnumByStatus(payDto.getTradeType());
        if (Objects.isNull(tradeTypeEnum)) {
            log.error("微信支付，交易类型[" + payDto.getTradeType() + "]不存在!");
            throw new WechatPayException(BaseErrorCodeEnum.TRADE_TYPE_NOT_EXSIT,payDto.getTradeType());
        }
        //根据订单号获取订单信息
        RiderUserOrder tenantOrder = tenantOrderService.getByOutTradeNo(payDto.getOutTradeNo());
        if(Objects.isNull(tenantOrder)){
            throw new WechatPayException(BaseErrorCodeEnum.ORDER_NO_TEXIST.getStatus(), String.format("用户订单【%s】不存在", payDto.getOutTradeNo()));
        }
        //判断订单是否已支付
        if(Objects.equals(tenantOrder.getOrderState(), OrderStateEnum.SUCCESS.getCode())){
            throw new WechatPayException(BaseErrorCodeEnum.REQ_FAIL.getStatus(), "订单已支付成功,无须支付!");
        }
        if(Objects.equals(tenantOrder.getOrderState(), OrderStateEnum.CANNEL.getCode())){
            throw new WechatPayException(BaseErrorCodeEnum.REQ_FAIL.getStatus(), "订单已取消支付!");
        }
        //若没传用户id,则取当前用户
        if(Objects.isNull(payDto.getUserId())){
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (oConvertUtils.isEmpty(loginUser)) {
                throw new JeecgBootException("请登录系统!");
            }
            RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
            if (oConvertUtils.isEmpty(riderCustomer)) {
                throw new JeecgBootException("请注册用户!");
            }
            if(StringUtils.isEmpty(riderCustomer.getWxOpenId())){
                throw new JeecgBootException("用户未绑定微信,请先绑定微信!");
            }
            payDto.setOpenid(riderCustomer.getWxOpenId());
            payDto.setMobile(riderCustomer.getPhone());
        } else {
            //根据用户id获取用户openid
            RiderCustomer userEntity = riderCustomerService.getById(payDto.getUserId());
            if(Objects.isNull(userEntity)){
                throw new JeecgBootException("用户不存在!");
            }
            if(StringUtils.isEmpty(userEntity.getWxOpenId())){
                throw new JeecgBootException("用户未绑定微信,请先绑定微信!");
            }
            payDto.setOpenid(userEntity.getWxOpenId());
            payDto.setMobile(userEntity.getPhone());
        }
        // 金额保留2位小数
        payDto.setTotalAmount(payDto.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        //1. 给该订单加锁,不允许订单号相同的多个线程同时进入
        Object intern = payDto.getOutTradeNo().intern();
        synchronized (intern){
            //2.订单重复支付申请校验
            RiderPayOrder payOrderinfo = payOrderinfoService.getByOutTradeNo(payDto.getOutTradeNo());
            if (Objects.nonNull(payOrderinfo)) {
                //若已存在支付订单,则判断是否已经生成预支付id
                if(StringUtils.isNotEmpty(payOrderinfo.getPrePayId())){
                    //已存在预支付id，则直接返回支付
                    ObjectNode signature = weChatPayApiInvoke.createSignature(payOrderinfo.getPrePayId());
                    return new Result().ok(signature);
                }
                payDto.setPayOrderId(payOrderinfo.getId());
            } else {
                //3.生成支付订单
                RiderPayOrder orderinfoEntity = payOrderinfoService.saveOrderinfo(payDto, tradeTypeEnum);
                payDto.setPayOrderId(orderinfoEntity.getId());
            }
        }
        //4.微信支付预下单
        switch (tradeTypeEnum) {
            // 小程序支付
            case JSAPI:
                return this.JSAPIPay(payDto);
            // 其它支付方式(待开发)
            default:
                log.error("微信支付，交易类型[" + payDto.getTradeType() + "]不存在!");
                throw new WechatPayException(BaseErrorCodeEnum.TRADE_TYPE_NOT_EXSIT,payDto.getTradeType());
        }
    }

    @Override
    public Result queryOrder(OrderQueryDTO queryDto) {
        Result<ObjectNode> result = new Result<>();
        //根据商户订单号查询支付订单
        WechatPayApiOutDTO outVO = weChatPayApiInvoke.queryByOutTradeNo(queryDto);
        if(Objects.equals(outVO.getStatus(),BaseErrorCodeEnum.REQ_SUCCESS.getCode())){
            //获取订单交易状态
            JSONObject jsonObject = (JSONObject) outVO.getData();
            String trade_state = jsonObject.getString("trade_state");
            TradeStateEnum tradeStateEnum = TradeStateEnum.getEnum(trade_state);
            if(Objects.isNull(tradeStateEnum)){
                throw new WechatPayException(BaseErrorCodeEnum.TRADE_STATE_NOT_EXSIT, trade_state);
            }
            //返回订单支付状态
            ObjectNode rootNode = WechatPayContants.OBJECT_MAPPER.createObjectNode();
            rootNode.put("trade_state",tradeStateEnum.getStatus());
            rootNode.put("trade_state_desc",tradeStateEnum.getMsg());
            result.ok(rootNode);
            return result;
        }
        return result.error(outVO.getStatus(),outVO.getMessage());
    }

    @Override
    public Result closeOrder(OrderCloseDTO closeDto) {
        Result result = new Result<>();
        // 调用微信支付关闭订单api
        WechatPayApiOutDTO outVO = weChatPayApiInvoke.close(closeDto);
        if(Objects.equals(outVO.getStatus(),204) || Objects.equals(outVO.getStatus(),200)){
            //订单关闭成功后更新支付订单状态
            RiderPayOrder orderinfoEntity = payOrderinfoService.getByOutTradeNo(closeDto.getOutTradeNo());
            if(Objects.nonNull(orderinfoEntity)){
                orderinfoEntity.setTradeState(TradeStateEnum.CLOSED.getStatus());
                orderinfoEntity.setCloseState(WechatPayContants.PayCloseStatus.CLOSE);
                payOrderinfoService.updateById(orderinfoEntity);
            }
        }
        return result.error(outVO.getStatus(),outVO.getMessage());
    }

    private Result JSAPIPay(WechatPayDTO payDto) throws Exception{
        Result result = new Result<>();
        WechatPayApiOutDTO outVO = weChatPayApiInvoke.JsapiPay(payDto);
        if(Objects.equals(outVO.getStatus(),BaseErrorCodeEnum.REQ_SUCCESS.getStatus())){
            //下单成功，则更新支付订单的预支付id
            JSONObject jsonObject = WechatPayContants.OBJECT_MAPPER.readValue(outVO.getData().toString(), JSONObject.class);
            RiderPayOrder orderinfoEntity = new RiderPayOrder();
            orderinfoEntity.setId(payDto.getPayOrderId());
            orderinfoEntity.setPrePayId(jsonObject.getString("package"));
            payOrderinfoService.updateById(orderinfoEntity);
            //返回支付订单id
            result.ok(outVO.getData());
            return result;
        }
        return result.error(outVO.getStatus(),outVO.getMessage());
    }

    @Override
    public Result transfer(WechatTransferDTO transferDto) throws Exception {
        if (StringUtils.isEmpty(transferDto.getOutBillNo())) {
            throw new JeecgBootException("商户单号不能为空！");
        }
        //1.参数校验
        if (Objects.isNull(transferDto.getAmount()) || transferDto.getAmount() <= 0) {
            throw new WechatPayException(BaseErrorCodeEnum.PARAM_ERROR.getStatus(), "提现金额必须大于0！");
        }
        //根据订单号获取订单信息
        RiderUserOrder tenantOrder = tenantOrderService.getByOutTradeNo(transferDto.getOutBillNo());
        if(Objects.isNull(tenantOrder)){
            throw new WechatPayException(BaseErrorCodeEnum.ORDER_NO_TEXIST.getStatus(), String.format("用户订单【%s】不存在", transferDto.getOutBillNo()));
        }
        //判断订单是否提现成功
        if(Objects.equals(tenantOrder.getOrderState(), OrderStateEnum.SUCCESS.getCode())){
            throw new WechatPayException(BaseErrorCodeEnum.REQ_FAIL.getStatus(), "订单已提现成功!");
        }
        if(!Objects.equals(tenantOrder.getOrderState(), OrderStateEnum.NOTPAY.getCode())){
            throw new WechatPayException(BaseErrorCodeEnum.REQ_FAIL.getStatus(), "订单状态【"+tenantOrder.getOrderState()+"】不正确!");
        }
        //判断微信用户是否存在
        RiderCustomer byOpenId = riderCustomerService.getByOpenId(transferDto.getOpenid());
        if(Objects.isNull(byOpenId)){
            throw new WechatPayException(BaseErrorCodeEnum.REQ_FAIL.getStatus(), "用户不存在!");
        }
        //1. 给该订单加锁,不允许订单号相同的多个线程同时进入
        Object intern = transferDto.getOutBillNo().intern();
        synchronized (intern){
            //2.订单重复提现申请校验
            RiderPayOrder payOrderinfo = payOrderinfoService.getByOutTradeNo(transferDto.getOutBillNo());
            if (Objects.nonNull(payOrderinfo)) {
                throw new WechatPayException(BaseErrorCodeEnum.ORDER_NO_TEXIST.getStatus(), String.format("提现订单号【%s】已存在", transferDto.getOutBillNo()));
            } else {
                //3.生成提现订单
                RiderPayOrder orderinfoEntity = payOrderinfoService.saveOrderinfo(transferDto);
                transferDto.setPayOrderId(orderinfoEntity.getId());
            }
        }
        Result result = new Result<>();
        WechatPayApiOutDTO transfer = weChatPayApiInvoke.transfer(transferDto);
        if(Objects.equals(transfer.getStatus(),BaseErrorCodeEnum.REQ_SUCCESS.getStatus())){
            //提现成功，则更新提现订单的信息
            JSONObject jsonObject = WechatPayContants.OBJECT_MAPPER.readValue(transfer.getData().toString(), JSONObject.class);
            RiderPayOrder orderinfoEntity = new RiderPayOrder();
            orderinfoEntity.setId(transferDto.getPayOrderId());
            orderinfoEntity.setPrePayId(jsonObject.getString("transfer_bill_no"));
            orderinfoEntity.setTransactionId(jsonObject.getString("package_info"));
            payOrderinfoService.updateById(orderinfoEntity);
            //返回响应
            result.ok(transfer.getData());
        }
        return result.error(transfer.getStatus(),transfer.getMessage());
    }
}
