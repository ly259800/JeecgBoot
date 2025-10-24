package org.jeecg.modules.rider.starwall.service;

import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
public interface IFamilyStarWallService extends IService<FamilyStarWall> {

    void addLikeCnt(String starWallId , Integer cnt);

}
