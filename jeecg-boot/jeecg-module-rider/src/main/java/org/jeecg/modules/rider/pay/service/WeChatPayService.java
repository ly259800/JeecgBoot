package org.jeecg.modules.rider.pay.service;


import org.jeecg.modules.rider.pay.dto.*;
import org.jeecg.modules.rider.pay.util.Result;

public interface WeChatPayService {
    Result preOrder(WechatPayDTO payDto) throws Exception;

    Result queryOrder(OrderQueryDTO queryDto);

    Result closeOrder(OrderCloseDTO closeDto);

    Result transfer(WechatTransferDTO transferDTO) throws Exception;



    Result cannelTransfer(TransferCannelDTO cannelDTO);

}
