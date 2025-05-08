package org.jeecg.modules.rider.commission.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.commission.entity.RiderCommission;
import org.jeecg.modules.rider.commission.mapper.RiderCommissionMapper;
import org.jeecg.modules.rider.commission.service.IRiderCommissionService;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 佣金管理
 * @Author: jeecg-boot
 * @Date:   2025-04-25
 * @Version: V1.0
 */
@Service
public class RiderCommissionServiceImpl extends ServiceImpl<RiderCommissionMapper, RiderCommission> implements IRiderCommissionService {

    @Autowired
    private IRiderCustomerService riderCustomerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditBatch(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        //更新用户佣金
        List<RiderCommission> riderCommissions = this.listByIds(idList);
        if(riderCommissions.stream().anyMatch(s-> Objects.equals(s.getAuditStatus() , 1))){
            throw new JeecgBootException("不能选择已审核的记录！");
        }
        //更新审核状态
        LambdaUpdateWrapper<RiderCommission> updateWrapper = new UpdateWrapper<RiderCommission>()
                .lambda()
                .in(RiderCommission::getId, idList)
                .set(RiderCommission::getAuditStatus,1);
        this.update(updateWrapper);
        riderCommissions.forEach(riderCommission -> {
            //添加用户佣金
            riderCustomerService.addCommission(riderCommission.getCustomerId(), BigDecimal.valueOf(riderCommission.getCommission()));
        });

    }
}
