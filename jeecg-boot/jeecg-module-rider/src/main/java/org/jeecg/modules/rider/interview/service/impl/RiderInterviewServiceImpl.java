package org.jeecg.modules.rider.interview.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.mapper.RiderInterviewMapper;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.service.IRiderSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public void passBatch(String ids) {
        LambdaUpdateWrapper<RiderInterview> updateWrapper = new UpdateWrapper<RiderInterview>()
                .lambda()
                .in(RiderInterview::getId, Arrays.asList(ids.split(",")))
                .set(RiderInterview::getPassStatus,"1");
        this.update(updateWrapper);
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
