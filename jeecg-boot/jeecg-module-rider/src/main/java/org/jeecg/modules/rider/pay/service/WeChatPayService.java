package org.jeecg.modules.rider.pay.service;


import org.jeecg.modules.rider.pay.dto.OrderCloseDTO;
import org.jeecg.modules.rider.pay.dto.OrderQueryDTO;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.util.Result;

public interface WeChatPayService {
    Result preOrder(WechatPayDTO payDto) throws Exception;

    Result queryOrder(OrderQueryDTO queryDto);

    Result closeOrder(OrderCloseDTO closeDto);
}
