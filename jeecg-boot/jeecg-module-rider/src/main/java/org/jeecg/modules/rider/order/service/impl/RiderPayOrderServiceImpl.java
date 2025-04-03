package org.jeecg.modules.rider.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.order.entity.RiderPayOrder;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.jeecg.modules.rider.order.mapper.RiderPayOrderMapper;
import org.jeecg.modules.rider.order.service.IRiderPayOrderService;
import org.jeecg.modules.rider.order.service.IRiderUserOrderService;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.TradeStateEnum;
import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.entity.CallbackDecryptData;
import org.jeecg.modules.rider.pay.enums.OrderStateEnum;
import org.jeecg.modules.rider.pay.enums.PayMethodEnum;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Description: 支付订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Service
public class RiderPayOrderServiceImpl extends ServiceImpl<RiderPayOrderMapper, RiderPayOrder> implements IRiderPayOrderService {

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;

    @Autowired
    private IRiderUserOrderService riderUserOrderService;

    @Autowired
    private IRiderCustomerService riderCustomerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RiderPayOrder saveOrderinfo(WechatPayDTO paydto, TradeTypeEnum tradeTypeEnum) {
        // 构建订单参数
        RiderPayOrder orderinfo = new RiderPayOrder();
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
        this.save(orderinfo);
        return orderinfo;
    }

    @Override
    public RiderPayOrder getByOutTradeNo(String outTradeNo) {
        QueryWrapper<RiderPayOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderPayOrder::getOutTradeNo, outTradeNo)
                .eq(RiderPayOrder::getCloseState, WechatPayContants.PayCloseStatus.OPEN)
                .last("limit 1");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderinfo(RiderUserOrder tenantOrder, RiderPayOrder payOrderinfo, CallbackDecryptData consumeData) {
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
        QueryWrapper<RiderPayOrder> payOrderWrapper = new QueryWrapper<>();
        payOrderWrapper.lambda().eq(RiderPayOrder::getId, payOrderinfo.getId())
                .eq(RiderPayOrder::getUpdateTime, payOrderUpdateTime);
        this.baseMapper.update(payOrderinfo,payOrderWrapper);
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
        QueryWrapper<RiderUserOrder> tenantOrderWrapper = new QueryWrapper<>();
        tenantOrderWrapper.lambda().eq(RiderUserOrder::getId, tenantOrder.getId())
                .eq(RiderUserOrder::getUpdateTime, tenantOrderUpdateTime);
        int flag = riderUserOrderService.getBaseMapper().update(tenantOrder, tenantOrderWrapper);
        // 3.订单更新时间未修改，则更新客户信息为合伙人
        if(flag>0){
            RiderCustomer riderCustomer = new RiderCustomer();
            riderCustomer.setIdentity(CustomerIdentityEnum.RIDER.getCode());
            riderCustomer.setId(tenantOrder.getCustomerId());
            riderCustomerService.updateById(riderCustomer);
        }

    }
}
