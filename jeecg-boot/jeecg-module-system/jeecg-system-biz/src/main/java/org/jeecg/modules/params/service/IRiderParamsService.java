package org.jeecg.modules.params.service;

import org.jeecg.modules.params.entity.RiderParams;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 参数配置
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
public interface IRiderParamsService extends IService<RiderParams> {

    RiderParams getByCode(String code);

}
