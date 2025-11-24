package org.jeecg.modules.rider.tourism.service;

import org.jeecg.modules.rider.tourism.entity.FamilyTourismDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 旅游信息详情表
 * @Author: jeecg-boot
 * @Date:   2025-11-23
 * @Version: V1.0
 */
public interface IFamilyTourismDetailService extends IService<FamilyTourismDetail> {

    FamilyTourismDetail getByTourismId(String tourismId);

}
