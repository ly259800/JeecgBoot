package org.jeecg.modules.rider.pay.service;


import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.entity.CallbackDecryptData;
import org.jeecg.modules.rider.pay.entity.PayOrderinfoEntity;
import org.jeecg.modules.rider.pay.entity.SysTenantOrderEntity;

public interface PayOrderinfoService {
    PayOrderinfoEntity saveOrderinfo(WechatPayDTO paydto, TradeTypeEnum tradeTypeEnum);

    PayOrderinfoEntity getByOutTradeNo(String outTradeNo);

    void updateOrderinfo(SysTenantOrderEntity tenantOrder, PayOrderinfoEntity payOrderinfo , CallbackDecryptData consumeData);

    void updateById(PayOrderinfoEntity orderinfoEntity);

}
