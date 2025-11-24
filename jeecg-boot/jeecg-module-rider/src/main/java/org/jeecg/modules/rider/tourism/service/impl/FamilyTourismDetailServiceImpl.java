package org.jeecg.modules.rider.tourism.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.rider.post.entity.PostDetail;
import org.jeecg.modules.rider.tourism.entity.FamilyTourismDetail;
import org.jeecg.modules.rider.tourism.mapper.FamilyTourismDetailMapper;
import org.jeecg.modules.rider.tourism.service.IFamilyTourismDetailService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 旅游信息详情表
 * @Author: jeecg-boot
 * @Date:   2025-11-23
 * @Version: V1.0
 */
@Service
public class FamilyTourismDetailServiceImpl extends ServiceImpl<FamilyTourismDetailMapper, FamilyTourismDetail> implements IFamilyTourismDetailService {

    @Override
    public FamilyTourismDetail getByTourismId(String tourismId) {
        QueryWrapper<FamilyTourismDetail> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(FamilyTourismDetail::getTourismId,tourismId).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
