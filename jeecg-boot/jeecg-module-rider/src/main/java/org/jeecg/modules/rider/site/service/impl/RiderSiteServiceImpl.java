package org.jeecg.modules.rider.site.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.mapper.RiderSiteMapper;
import org.jeecg.modules.rider.site.service.IRiderSiteService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 * @Description: 站点管理
 * @Author: jeecg-boot
 * @Date:   2025-03-23
 * @Version: V1.0
 */
@Service
public class RiderSiteServiceImpl extends ServiceImpl<RiderSiteMapper, RiderSite> implements IRiderSiteService {
    @Override
    public void updateProfit(String ids, Integer profit) {
        LambdaUpdateWrapper<RiderSite> updateWrapper = new UpdateWrapper<RiderSite>()
                .lambda()
                .set(RiderSite::getProfit,profit);
        if(StringUtils.isNotEmpty(ids)){
            updateWrapper.in(RiderSite::getId, Arrays.asList(ids.split(",")));
        }
        this.update(updateWrapper);
    }
}
