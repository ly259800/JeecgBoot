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
import org.jeecg.modules.rider.pay.constants.TransferStateEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.dto.WechatTransferDTO;
import org.jeecg.modules.rider.pay.entity.CallbackDecryptData;
import org.jeecg.modules.rider.pay.entity.TransferCallbackDecryptData;
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
    @Transactional(rollbackFor = Exception.class)
    public RiderPayOrder saveOrderinfo(WechatTransferDTO transferDTO) {
        // 构建订单参数
        RiderPayOrder orderinfo = new RiderPayOrder();
        orderinfo.setAppid(wxpayServiceConfig.getAppid());
        orderinfo.setMchid(wxpayServiceConfig.getMchid());
        orderinfo.setOutTradeNo(transferDTO.getOutBillNo());//订单号
        orderinfo.setDescription(transferDTO.getRemark());//商品描述
        orderinfo.setTradeType(TradeTypeEnum.TRANSFER.getStatus());// 交易类型
        orderinfo.setTradeState(TradeStateEnum.NOTPAY.getStatus());//交易状态
        orderinfo.setOpenid(transferDTO.getOpenid());
        orderinfo.setTotalAmount(BigDecimal.valueOf(transferDTO.getAmount()).subtract(BigDecimal.valueOf(100)));//订单总金额,单位为元
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
    public void updateOrderinfo(RiderUserOrder riderUserOrder, RiderPayOrder payOrderinfo, CallbackDecryptData consumeData) {
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
        int update = this.baseMapper.update(payOrderinfo, payOrderWrapper);
        //支付订单更新成功
        if(update > 0){
            // 2.更新用户订单
            Date userOrderUpdateTime = riderUserOrder.getUpdateTime();
            riderUserOrder.setActualAmount(payAmount);//实际支付金额
            riderUserOrder.setSuccessTime(consumeData.getSuccessTime());//支付完成日期
            riderUserOrder.setPaymentMethod(PayMethodEnum.WECHAT.getCode());//微信支付
            if(Objects.equals(tradeStateEnum,TradeStateEnum.SUCCESS)){
                riderUserOrder.setOrderState(OrderStateEnum.SUCCESS.getCode());//支付成功
            } else if(Objects.equals(tradeStateEnum,TradeStateEnum.NOTPAY)){
                riderUserOrder.setOrderState(OrderStateEnum.SUCCESS.getCode());//未支付
            } else {
                riderUserOrder.setOrderState(OrderStateEnum.PAYERROR.getCode());//支付失败
            }
            riderUserOrder.setUpdateTime(new Date());
            QueryWrapper<RiderUserOrder> userOrderWrapper = new QueryWrapper<>();
            userOrderWrapper.lambda().eq(RiderUserOrder::getId, riderUserOrder.getId());
            if(userOrderUpdateTime != null){
                userOrderWrapper.lambda().eq(RiderUserOrder::getUpdateTime, userOrderUpdateTime);
            }
            riderUserOrderService.getBaseMapper().update(riderUserOrder, userOrderWrapper);
            //3.更新用户为合伙人
            RiderCustomer riderCustomer = new RiderCustomer();
            riderCustomer.setIdentity(CustomerIdentityEnum.PARTNER.getCode());
            riderCustomer.setId(riderUserOrder.getCustomerId());
            riderCustomerService.updateById(riderCustomer);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderinfo(RiderUserOrder riderUserOrder, RiderPayOrder payOrderinfo, TransferCallbackDecryptData consumeData) {
        // 1.更新转账订单
        Date payOrderUpdateTime = payOrderinfo.getUpdateTime();
        TransferStateEnum transferStateEnum = TransferStateEnum.getEnum(consumeData.getState());
        //转账金额单位为分
        payOrderinfo.setPayAmount(BigDecimal.valueOf(consumeData.getTransferAmount()));//用户提现金额
        payOrderinfo.setTradeState(transferStateEnum.getStatus());
        if(Objects.equals(transferStateEnum,TransferStateEnum.CANCELLED)){
            //订单已关闭
            payOrderinfo.setCloseState(WechatPayContants.PayCloseStatus.CLOSE);
        }
        if(Objects.equals(transferStateEnum,TransferStateEnum.SUCCESS)){
            payOrderinfo.setSuccessTime(consumeData.getUpdateTime());//提现成功时间
        }
        payOrderinfo.setUpdateTime(new Date());
        QueryWrapper<RiderPayOrder> payOrderWrapper = new QueryWrapper<>();
        payOrderWrapper.lambda().eq(RiderPayOrder::getId, payOrderinfo.getId())
                .eq(RiderPayOrder::getUpdateTime, payOrderUpdateTime);
        int update = this.baseMapper.update(payOrderinfo, payOrderWrapper);
        //转账订单更新成功
        if(update > 0){
            // 2.更新用户订单
            Date userOrderUpdateTime = riderUserOrder.getUpdateTime();
            riderUserOrder.setActualAmount(BigDecimal.valueOf(consumeData.getTransferAmount()));//实际支付金额
            riderUserOrder.setPaymentMethod(PayMethodEnum.WECHAT.getCode());//微信支付
            if(Objects.equals(transferStateEnum,TransferStateEnum.SUCCESS)){
                riderUserOrder.setSuccessTime(consumeData.getUpdateTime());//转账完成日期
                riderUserOrder.setOrderState(OrderStateEnum.SUCCESS.getCode());//支付成功
            } else {
                riderUserOrder.setOrderState(transferStateEnum.getStatus());//转账失败
            }
            riderUserOrder.setUpdateTime(new Date());
            QueryWrapper<RiderUserOrder> userOrderWrapper = new QueryWrapper<>();
            userOrderWrapper.lambda().eq(RiderUserOrder::getId, riderUserOrder.getId());
            if(userOrderUpdateTime != null){
                userOrderWrapper.lambda().eq(RiderUserOrder::getUpdateTime, userOrderUpdateTime);
            }
            riderUserOrderService.getBaseMapper().update(riderUserOrder, userOrderWrapper);
            // 3.添加提现佣金
            riderCustomerService.subtractCommission(riderUserOrder.getCustomerId(),BigDecimal.ZERO, BigDecimal.valueOf(consumeData.getTransferAmount()).subtract(BigDecimal.valueOf(100)));
        }
    }
}
