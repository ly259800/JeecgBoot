package org.jeecg.modules.rider.starwall.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.starwall.dto.FamilyStarWallDTO;
import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
public interface FamilyStarWallMapper extends BaseMapper<FamilyStarWall> {

    void addLikeCnt(@Param("id") String id, @Param("cnt") Integer cnt);

    List<FamilyStarWall> getStarWallList(@Param("ew") Wrapper<FamilyStarWall> queryWrapper, @Param("totalDate") LocalDate totalDate);

}
