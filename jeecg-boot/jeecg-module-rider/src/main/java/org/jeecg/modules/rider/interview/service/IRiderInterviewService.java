package org.jeecg.modules.rider.interview.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface IRiderInterviewService extends IService<RiderInterview> {

    IPage<RiderInterviewDTO> pageList(IPage<RiderInterview> page, @Param("ew") Wrapper<RiderInterview> queryWrapper);


    void passBatch(String ids);

    void settleBatch(String ids);

    void marketCompleteBatch(String ids);


    void updatePriceBatch(String ids, BigDecimal price);

    void confirmTraining(String ids, String trainingTeacher);

    void handle(RiderInterview riderInterview);

    void updateSite(RiderInterview riderInterview);

    List<RiderInterview> queryListByCategory(String phone,List<String> categoryIds);

    void updatePayPrice(String ids, BigDecimal payPrice);

}
