package org.jeecg.modules.rider.site.service;

import org.jeecg.modules.rider.site.entity.RiderSite;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 站点管理
 * @Author: jeecg-boot
 * @Date:   2025-03-23
 * @Version: V1.0
 */
public interface IRiderSiteService extends IService<RiderSite> {

    void updateProfit(String ids,Integer profit);

}
