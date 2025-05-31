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
import org.jeecg.modules.rider.interview.mapper.RiderInterviewMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Service
public class RiderCustomerServiceImpl extends ServiceImpl<RiderCustomerMapper, RiderCustomer> implements IRiderCustomerService {

    @Autowired
    private RiderInterviewMapper riderInterviewMapper;


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
    public List<RiderCustomer> getByReferencePhone(String referencePhone) {
        QueryWrapper<RiderCustomer> wrapper = new QueryWrapper<>();
        wrapper.lambda().likeLeft(RiderCustomer::getPhone,referencePhone);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void upgradePartner(String ids) {
        LambdaUpdateWrapper<RiderCustomer> updateWrapper = new UpdateWrapper<RiderCustomer>()
                .lambda()
                .in(RiderCustomer::getId, Arrays.asList(ids.split(",")))
                .set(RiderCustomer::getIdentity, CustomerIdentityEnum.PARTNER.getCode());
        this.update(updateWrapper);
    }

    @Override
    public void updateQrcode(String id) {
        LambdaUpdateWrapper<RiderCustomer> updateWrapper = new UpdateWrapper<RiderCustomer>()
                .lambda()
                .eq(RiderCustomer::getId, id)
                .set(RiderCustomer::getQrcode, "");
        this.update(updateWrapper);
    }

    @Override
    public void addCommission(String id, BigDecimal commission) {
        baseMapper.addCommission(id,commission);
    }

    @Override
    public void subtractCommission(String id, BigDecimal commission , BigDecimal settleCommission) {
        baseMapper.subtractCommission(id,commission,settleCommission);
    }

    @Override
    public RiderCustomerDTO convertTotal(RiderCustomer riderCustomer) {
        RiderCustomerDTO riderCustomerDTO = new RiderCustomerDTO();
        BeanUtils.copyProperties(riderCustomer, riderCustomerDTO);
        //统计未入职、已入职、已结算的数据
        QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RiderInterview::getReference,riderCustomer.getId());
        List<RiderInterview> interviewList = riderInterviewMapper.selectList(queryWrapper);
        Integer failCount = 0;
        Integer passCount = 0;
        Integer settleCount = 0;
        for (RiderInterview riderInterview : interviewList) {
            if(Objects.equals(riderInterview.getPassStatus() , 1)){
                passCount++;
            } else {
                failCount++;
            }
            if(Objects.equals(riderInterview.getSettleStatus() , 1)){
                settleCount++;
            }
        }
        riderCustomerDTO.setFailCount(failCount);
        riderCustomerDTO.setPassCount(passCount);
        riderCustomerDTO.setSettleCount(settleCount);
        return riderCustomerDTO;
    }
}
