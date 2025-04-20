package org.jeecg.modules.params.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.params.service.IRiderParamsService;
import org.jeecg.modules.params.entity.RiderParams;
import org.jeecg.modules.params.mapper.RiderParamsMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 参数配置
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
@Service
public class RiderParamsServiceImpl extends ServiceImpl<RiderParamsMapper, RiderParams> implements IRiderParamsService {


    @Override
    public RiderParams getByCode(String code) {
        QueryWrapper<RiderParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderParams::getParamCode,code).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
