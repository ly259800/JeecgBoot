package org.jeecg.modules.rider.customer.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface RiderCustomerMapper extends BaseMapper<RiderCustomer> {

    void addCommission(@Param("id") String id, @Param("commission") BigDecimal commission);

    void subtractCommission(@Param("id") String id, @Param("commission") BigDecimal commission, @Param("settleCommission") BigDecimal settleCommission);

}
