package org.jeecg.modules.rider.order.service;

import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.rider.pay.util.Result;

/**
 * @Description: 用户个人订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
public interface IRiderUserOrderService extends IService<RiderUserOrder> {

    RiderUserOrder getByOutTradeNo(String outTradeNo);

    Long getMaxOutTradeNo();

    Result saveUserOrder(RiderUserOrder dto);

}
