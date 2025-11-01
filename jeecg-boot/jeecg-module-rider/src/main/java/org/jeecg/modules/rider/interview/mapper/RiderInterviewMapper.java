package org.jeecg.modules.rider.interview.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface RiderInterviewMapper extends BaseMapper<RiderInterview> {

    List<RiderInterview> queryListByCategory(@Param("phone") String phone , @Param("categoryIds") List<String> categoryIds);

    IPage<RiderInterviewDTO> pageList(IPage<RiderInterview> page, @Param("ew") Wrapper<RiderInterview> queryWrapper);


}
