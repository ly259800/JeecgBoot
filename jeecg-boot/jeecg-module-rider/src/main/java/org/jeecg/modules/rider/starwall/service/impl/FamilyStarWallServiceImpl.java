package org.jeecg.modules.rider.starwall.service.impl;

import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import org.jeecg.modules.rider.starwall.mapper.FamilyStarWallMapper;
import org.jeecg.modules.rider.starwall.service.IFamilyStarWallService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
}
