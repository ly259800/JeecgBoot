package org.jeecg.modules.rider.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.mapper.RiderCustomerMapper;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Service
public class RiderCustomerServiceImpl extends ServiceImpl<RiderCustomerMapper, RiderCustomer> implements IRiderCustomerService {

    @Override
    public RiderCustomer getByOpenId(String openid) {
        QueryWrapper<RiderCustomer> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderCustomer::getWxOpenId,openid).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public RiderCustomer getByPhone(String phone) {
        QueryWrapper<RiderCustomer> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderCustomer::getPhone,phone).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
