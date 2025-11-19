package org.jeecg.modules.rider.starwall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;

/**
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
@Data
public class FamilyStarWallDTO extends FamilyStarWall{

	/**统计时间*/
    @ApiModelProperty(value = "统计时间(1-本月 2-本周 3-今日)")
    private Integer totalDate;


    @ApiModelProperty(value = "推广人数")
    private Long promoterCount;


    @ApiModelProperty(value = "1-按推广人数排序")
    private Integer orderType;
}
