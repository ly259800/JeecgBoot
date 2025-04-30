package org.jeecg.modules.rider.site.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.mapper.RiderSiteMapper;
import org.jeecg.modules.rider.site.service.IRiderSiteService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<RiderSite> riderSiteList = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)){
            riderSiteList = this.baseMapper.selectBatchIds(Arrays.asList(ids.split(",")));
        } else {
            QueryWrapper<RiderSite> queryWrapper = new QueryWrapper<>();
            riderSiteList = this.baseMapper.selectList(queryWrapper);
        }
        if(!CollectionUtils.isEmpty(riderSiteList)){
            riderSiteList.forEach(x->{
                x.setProfit(x.getCommission() * profit / 100);
                this.baseMapper.updateById(x);
            });
        }
    }
}
