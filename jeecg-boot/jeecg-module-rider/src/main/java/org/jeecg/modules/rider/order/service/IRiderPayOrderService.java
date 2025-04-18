package org.jeecg.modules.rider.order.service;

import org.jeecg.modules.rider.order.entity.RiderPayOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.entity.CallbackDecryptData;

/**
 * @Description: 支付订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
public interface IRiderPayOrderService extends IService<RiderPayOrder> {


    RiderPayOrder saveOrderinfo(WechatPayDTO paydto, TradeTypeEnum tradeTypeEnum);

    RiderPayOrder getByOutTradeNo(String outTradeNo);

    void updateOrderinfo(RiderUserOrder tenantOrder, RiderPayOrder payOrderinfo , CallbackDecryptData consumeData);

}
