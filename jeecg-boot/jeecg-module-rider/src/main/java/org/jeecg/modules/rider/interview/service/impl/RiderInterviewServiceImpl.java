package org.jeecg.modules.rider.interview.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.commission.entity.RiderCommission;
import org.jeecg.modules.rider.commission.service.IRiderCommissionService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.mapper.RiderInterviewMapper;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.service.IRiderSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Service
public class RiderInterviewServiceImpl extends ServiceImpl<RiderInterviewMapper, RiderInterview> implements IRiderInterviewService {

    @Autowired
    private IRiderSiteService riderSiteService;

    @Autowired
    private IRiderCommissionService riderCommissionService;

    @Override
    public void passBatch(String ids) {
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .in(RiderInterview::getId, Arrays.asList(ids.split(",")))
                .set(RiderInterview::getPassStatus,1);
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void settleBatch(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<RiderInterview> riderInterviews = this.listByIds(idList);
        if(riderInterviews.stream().anyMatch(s-> StringUtils.isEmpty(s.getReference()))){
            throw new JeecgBootException("推广人为空，不能结算！");
        }
        if(riderInterviews.stream().anyMatch(s-> StringUtils.isEmpty(s.getSiteId()))){
            throw new JeecgBootException("站点为空，不能结算！");
        }
        List<String> siteIdList = riderInterviews.stream().map(x -> x.getSiteId()).collect(Collectors.toList());

        List<RiderSite> riderSiteList = riderSiteService.listByIds(siteIdList);
        Map<String,  RiderSite> riderSiteMap = riderSiteList.stream().collect(Collectors.toMap(RiderSite::getId, Function.identity(), (a, b) -> b));
        //更新为已结算
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .in(RiderInterview::getId, idList)
                .set(RiderInterview::getSettleStatus,"1");
        this.update(updateWrapper);
        //新增佣金审核
        List<RiderCommission> commissionList = riderInterviews.stream().map(x -> {
            RiderCommission riderCommission = new RiderCommission();
            riderCommission.setCustomerId(x.getReference());
            riderCommission.setCustomerPhone(x.getReferencePhone());
            riderCommission.setInterviewId(x.getSiteId());
            riderCommission.setInterviewName(x.getName());
            riderCommission.setInterviewPhone(x.getPhone());
            riderCommission.setAuditStatus(0);
            if(riderSiteMap.containsKey(x.getSiteId())){
                RiderSite riderSite = riderSiteMap.get(x.getSiteId());
                riderCommission.setCommission(riderSite.getCommission() - riderSite.getProfit());
            }
            return riderCommission;
        }).collect(Collectors.toList());
        riderCommissionService.saveBatch(commissionList);
    }

    @Override
    public void handle(RiderInterview riderInterview) {
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId, riderInterview.getId())
                .set(RiderInterview::getMemo,riderInterview.getMemo())
                .set(RiderInterview::getStatus,"1");
        this.update(updateWrapper);
    }

    @Override
    public void updateSite(RiderInterview riderInterview) {
        RiderSite site = riderSiteService.getById(riderInterview.getSiteId());
        if(Objects.isNull(site)){
            throw new JeecgBootException("站点不存在!");
        }
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .eq(RiderInterview::getId,riderInterview.getId())
                .set(RiderInterview::getSiteId,riderInterview.getSiteId())
                .set(RiderInterview::getSiteName,site.getName());
        this.update(updateWrapper);
    }
}
