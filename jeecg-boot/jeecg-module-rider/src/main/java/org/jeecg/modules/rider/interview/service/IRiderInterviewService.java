package org.jeecg.modules.rider.interview.service;

import org.jeecg.modules.rider.interview.entity.RiderInterview;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface IRiderInterviewService extends IService<RiderInterview> {

    void passBatch(String ids);

    void settleBatch(String ids);

    void handle(RiderInterview riderInterview);

    void updateSite(RiderInterview riderInterview);

}
