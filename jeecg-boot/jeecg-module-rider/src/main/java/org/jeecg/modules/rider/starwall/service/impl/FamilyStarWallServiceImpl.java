package org.jeecg.modules.rider.starwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.rider.starwall.dto.FamilyStarWallDTO;
import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import org.jeecg.modules.rider.starwall.mapper.FamilyStarWallMapper;
import org.jeecg.modules.rider.starwall.service.IFamilyStarWallService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
@Service
public class FamilyStarWallServiceImpl extends ServiceImpl<FamilyStarWallMapper, FamilyStarWall> implements IFamilyStarWallService {

    @Override
    public void addLikeCnt(String starWallId, Integer cnt) {
        baseMapper.addLikeCnt(starWallId, cnt);
    }

    @Override
    public List<FamilyStarWallDTO> getStarWallList(IPage<FamilyStarWall> page, FamilyStarWallDTO familyStarWall) {
        Integer totalDate = familyStarWall.getTotalDate();
        QueryWrapper<FamilyStarWall> queryWrapper = new QueryWrapper<>();
        LocalDate localDate = LocalDate.now();
        //获取当月一号的日期
        if(Objects.equals(totalDate,1)){
            //查询本月
            localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
        } else if(Objects.equals(totalDate,2)){
            //查询本周
            localDate = localDate.with(DayOfWeek.MONDAY);
        }
        if(StringUtils.isNotEmpty(familyStarWall.getName())){
            queryWrapper.like("fsw.name",familyStarWall.getName());
        }
        if(Objects.nonNull(familyStarWall.getIdentity())){
            queryWrapper.eq("fsw.identity",familyStarWall.getIdentity());
        }
        queryWrapper.groupBy("fsw.id");
        if(Objects.equals(familyStarWall.getOrderType(),1)){
            queryWrapper.orderByDesc("promoterCount,fsw.id");
        } else {
            queryWrapper.orderByDesc("likeCnt,fsw.id");
        }
        return baseMapper.getStarWallList(page,queryWrapper,localDate);
    }

    @Override
    public List<FamilyStarWallDTO> queryList(IPage<FamilyStarWall> page,FamilyStarWallDTO familyStarWall) {
        QueryWrapper<FamilyStarWall> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(familyStarWall.getName())){
            queryWrapper.like("fsw.name",familyStarWall.getName());
        }
        if(Objects.nonNull(familyStarWall.getIdentity())){
            queryWrapper.eq("fsw.identity",familyStarWall.getIdentity());
        }
        if(Objects.equals(familyStarWall.getOrderType(),1)){
            queryWrapper.orderByDesc("promoterCount,fsw.id");
        } else {
            queryWrapper.orderByDesc("fsw.like_cnt,fsw.id");
        }
        return baseMapper.queryList(page,queryWrapper);
    }
}
