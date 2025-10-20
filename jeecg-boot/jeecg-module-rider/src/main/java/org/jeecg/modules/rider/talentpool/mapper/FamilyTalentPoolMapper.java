package org.jeecg.modules.rider.talentpool.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.rider.talentpool.dto.FamilyTalentPoolDTO;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 人才库
 * @Author: jeecg-boot
 * @Date:   2025-10-19
 * @Version: V1.0
 */
public interface FamilyTalentPoolMapper extends BaseMapper<FamilyTalentPool> {

    IPage<FamilyTalentPoolDTO> pageList(IPage<FamilyTalentPool> page, @Param("ew") Wrapper<FamilyTalentPool> queryWrapper);


    List<FamilyTalentPoolDTO> queryList(@Param("ew") Wrapper<FamilyTalentPool> queryWrapper);


}
