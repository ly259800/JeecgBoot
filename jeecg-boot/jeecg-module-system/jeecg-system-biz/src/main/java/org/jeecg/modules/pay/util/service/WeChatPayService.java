package org.jeecg.modules.pay.util.service;


import org.jeecg.modules.pay.dto.OrderCloseDTO;
import org.jeecg.modules.pay.dto.OrderQueryDTO;
import org.jeecg.modules.pay.util.Result;
import org.jeecg.modules.pay.dto.WechatPayDTO;

public interface WeChatPayService {
    Result preOrder(WechatPayDTO payDto) throws Exception;

    Result queryOrder(OrderQueryDTO queryDto);

    Result closeOrder(OrderCloseDTO closeDto);
}
