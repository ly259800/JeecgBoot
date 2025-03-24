package org.jeecg.modules.rider.pay.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.rider.pay.dto.TenantOrderDTO;
import org.jeecg.modules.rider.pay.dto.TenantOrderPageQueryDTO;
import org.jeecg.modules.rider.pay.dto.TenantOrderSaveDTO;
import org.jeecg.modules.rider.pay.entity.SysTenantOrderEntity;
import org.jeecg.modules.rider.pay.util.Result;

/**
 * 租户订单信息表
 */
public interface TenantOrderService extends IService<SysTenantOrderEntity> {
    Result saveTenantOrder(TenantOrderSaveDTO dto);


    SysTenantOrderEntity getByOutTradeNo(String outTradeNo);

    Long getMaxOutTradeNo();
}
