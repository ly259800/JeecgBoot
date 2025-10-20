package org.jeecg.modules.rider.talentpool.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.rider.talentpool.dto.FamilyTalentPoolDTO;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 人才库
 * @Author: jeecg-boot
 * @Date:   2025-10-19
 * @Version: V1.0
 */
public interface IFamilyTalentPoolService extends IService<FamilyTalentPool> {

    IPage<FamilyTalentPoolDTO> pageList(IPage<FamilyTalentPool> page, Wrapper<FamilyTalentPool> queryWrapper);

    List<FamilyTalentPoolDTO> queryList(Wrapper<FamilyTalentPool> queryWrapper);


}
