package org.jeecg.modules.rider.commission.service;

import org.jeecg.modules.rider.commission.entity.RiderCommission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 佣金管理
 * @Author: jeecg-boot
 * @Date:   2025-04-25
 * @Version: V1.0
 */
public interface IRiderCommissionService extends IService<RiderCommission> {


    void auditBatch(String ids);

}
