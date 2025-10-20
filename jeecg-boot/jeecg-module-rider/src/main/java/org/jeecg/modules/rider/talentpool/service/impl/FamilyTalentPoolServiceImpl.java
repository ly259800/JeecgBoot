package org.jeecg.modules.rider.talentpool.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.rider.talentpool.dto.FamilyTalentPoolDTO;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import org.jeecg.modules.rider.talentpool.mapper.FamilyTalentPoolMapper;
import org.jeecg.modules.rider.talentpool.service.IFamilyTalentPoolService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 人才库
 * @Author: jeecg-boot
 * @Date:   2025-10-19
 * @Version: V1.0
 */
@Service
public class FamilyTalentPoolServiceImpl extends ServiceImpl<FamilyTalentPoolMapper, FamilyTalentPool> implements IFamilyTalentPoolService {

    @Override
    public IPage<FamilyTalentPoolDTO> pageList(IPage<FamilyTalentPool> page, Wrapper<FamilyTalentPool> queryWrapper) {
        return baseMapper.pageList(page, queryWrapper);
    }

    @Override
    public List<FamilyTalentPoolDTO> queryList(Wrapper<FamilyTalentPool> queryWrapper) {
        return baseMapper.queryList(queryWrapper);
    }
}
