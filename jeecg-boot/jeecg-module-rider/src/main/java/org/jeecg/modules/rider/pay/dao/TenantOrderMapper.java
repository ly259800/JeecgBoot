package org.jeecg.modules.rider.pay.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.pay.entity.SysTenantOrderEntity;

/**
* 租户订单信息表
*
* @author admin
* @since 3.0 2022-02-08
*/
public interface TenantOrderMapper extends BaseMapper<SysTenantOrderEntity> {
    String getMaxOutTradeNo(@Param(Constants.WRAPPER) Wrapper<SysTenantOrderEntity> queryWrapper);
    /**
     * 查询订单列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<SysTenantOrderEntity> pageQuery(IPage<SysTenantOrderEntity> page, @Param("ew") QueryWrapper<SysTenantOrderEntity> wrapper);
}
