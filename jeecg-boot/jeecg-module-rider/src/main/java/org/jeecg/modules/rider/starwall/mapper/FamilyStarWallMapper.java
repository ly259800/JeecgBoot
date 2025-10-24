package org.jeecg.modules.rider.starwall.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
public interface FamilyStarWallMapper extends BaseMapper<FamilyStarWall> {

    void addLikeCnt(@Param("id") String id, @Param("cnt") Integer cnt);

}
