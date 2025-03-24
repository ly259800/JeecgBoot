package org.jeecg.modules.rider.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.TradeStateEnum;
import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.dao.PayOrderinfoMapper;
import org.jeecg.modules.rider.pay.dao.TenantOrderMapper;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.entity.CallbackDecryptData;
import org.jeecg.modules.rider.pay.entity.PayOrderinfoEntity;
import org.jeecg.modules.rider.pay.entity.SysTenantOrderEntity;
import org.jeecg.modules.rider.pay.enums.OrderStateEnum;
import org.jeecg.modules.rider.pay.enums.PayMethodEnum;
import org.jeecg.modules.rider.pay.service.PayOrderinfoService;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Service
public class PayOrderinfoServiceImpl implements PayOrderinfoService {
    @Resource
    private PayOrderinfoMapper payOrderinfoMapper;

    @Resource
    private TenantOrderMapper tenantOrderMapper;

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;

    @Lazy
    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrderinfoEntity saveOrderinfo(WechatPayDTO paydto, TradeTypeEnum tradeTypeEnum) {
        // 构建订单参数
        PayOrderinfoEntity orderinfo = new PayOrderinfoEntity();
        orderinfo.setAppid(wxpayServiceConfig.getAppid());
        orderinfo.setMchid(wxpayServiceConfig.getMchid());
        orderinfo.setOutTradeNo(paydto.getOutTradeNo());//订单号
        orderinfo.setDescription(paydto.getDescription());//商品描述
        orderinfo.setTradeType(tradeTypeEnum.getStatus());// 交易类型
        orderinfo.setTradeState(TradeStateEnum.NOTPAY.getStatus());//交易状态
        orderinfo.setOpenid(paydto.getOpenid());
        orderinfo.setTotalAmount(paydto.getTotalAmount());//订单总金额,单位为元
        orderinfo.setCurrency("CNY");// 货币类型(CNY-人民币)
        orderinfo.setCloseState(WechatPayContants.PayCloseStatus.OPEN);// 订单关闭状态默认为开启
        payOrderinfoMapper.insert(orderinfo);
        return orderinfo;
    }

    @Override
    public PayOrderinfoEntity getByOutTradeNo(String outTradeNo) {
        QueryWrapper<PayOrderinfoEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PayOrderinfoEntity::getOutTradeNo, outTradeNo)
                .eq(PayOrderinfoEntity::getCloseState, WechatPayContants.PayCloseStatus.OPEN)
                .last("limit 1");
        return payOrderinfoMapper.selectOne(wrapper);
    }

    /**
     * 更新支付订单、租户订单和租户信息
     * @param payOrderinfo
     * @param payOrderinfo
     * @param consumeData
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderinfo(SysTenantOrderEntity tenantOrder, PayOrderinfoEntity payOrderinfo, CallbackDecryptData consumeData) {
        // 1.更新支付订单
        Date payOrderUpdateTime = payOrderinfo.getUpdateTime();
        TradeStateEnum tradeStateEnum = TradeStateEnum.getEnum(consumeData.getTradeState());
        //支付金额单位为分转换为元
        BigDecimal payAmount = PriceUtils.FenToYuan(consumeData.getAmount().getPayerTotal());
        payOrderinfo.setPayAmount(payAmount);//用户支付金额
        payOrderinfo.setPayCurrency(consumeData.getAmount().getPayerCurrency());//支付币种
        payOrderinfo.setTransactionId(consumeData.getTransactionId());//微信支付订单号
        payOrderinfo.setTradeState(tradeStateEnum.getStatus());
        if(Objects.equals(tradeStateEnum,TradeStateEnum.CLOSED)){
            //订单已关闭
            payOrderinfo.setCloseState(WechatPayContants.PayCloseStatus.CLOSE);
        }
        payOrderinfo.setBankType(consumeData.getBankType());//银行类型
        payOrderinfo.setSuccessTime(consumeData.getSuccessTime());//支付完成时间
        payOrderinfo.setUpdateTime(new Date());
        QueryWrapper<PayOrderinfoEntity> payOrderWrapper = new QueryWrapper<>();
        payOrderWrapper.lambda().eq(PayOrderinfoEntity::getId, payOrderinfo.getId())
                .eq(PayOrderinfoEntity::getUpdateTime, payOrderUpdateTime);
        payOrderinfoMapper.update(payOrderinfo,payOrderWrapper);
        // 2.更新租户订单
        Date tenantOrderUpdateTime = tenantOrder.getUpdateTime();
        tenantOrder.setActualAmount(payAmount);//实际支付金额
        tenantOrder.setSuccessTime(consumeData.getSuccessTime());//支付完成日期
        tenantOrder.setPaymentMethod(PayMethodEnum.WECHAT.getCode());//微信支付
        if(Objects.equals(tradeStateEnum,TradeStateEnum.SUCCESS)){
            tenantOrder.setOrderState(OrderStateEnum.SUCCESS.getCode());//支付成功
        } else if(Objects.equals(tradeStateEnum,TradeStateEnum.NOTPAY)){
            tenantOrder.setOrderState(OrderStateEnum.SUCCESS.getCode());//未支付
        } else {
            tenantOrder.setOrderState(OrderStateEnum.PAYERROR.getCode());//支付失败
        }
        tenantOrder.setUpdateTime(new Date());
        QueryWrapper<SysTenantOrderEntity> tenantOrderWrapper = new QueryWrapper<>();
        tenantOrderWrapper.lambda().eq(SysTenantOrderEntity::getId, tenantOrder.getId())
                .eq(SysTenantOrderEntity::getUpdateTime, tenantOrderUpdateTime);
        int flag = tenantOrderMapper.update(tenantOrder, tenantOrderWrapper);
        // 3.订单更新时间未修改，则更新租户信息
        if(flag>0){


        }
    }
    @Override
    public void updateById(PayOrderinfoEntity orderinfoEntity) {
        payOrderinfoMapper.updateById(orderinfoEntity);
    }
}
