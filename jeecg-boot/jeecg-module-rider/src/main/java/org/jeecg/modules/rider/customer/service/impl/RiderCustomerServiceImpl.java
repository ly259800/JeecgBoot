package org.jeecg.modules.rider.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.modules.rider.customer.dto.RiderCustomerDTO;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.mapper.RiderCustomerMapper;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    @Override
    public void upgradePartner(String ids) {
        LambdaUpdateWrapper<RiderCustomer> updateWrapper = new UpdateWrapper<RiderCustomer>()
                .lambda()
                .in(RiderCustomer::getId, Arrays.asList(ids.split(",")))
                .set(RiderCustomer::getIdentity, CustomerIdentityEnum.PARTNER.getCode());
        this.update(updateWrapper);
    }
}
