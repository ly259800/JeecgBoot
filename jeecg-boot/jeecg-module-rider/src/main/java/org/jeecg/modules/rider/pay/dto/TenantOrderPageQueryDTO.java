package org.jeecg.modules.rider.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 租户订单分页查询入参
 * @author leiyong
 * @date 2022-02-08
 */
@Data
public class TenantOrderPageQueryDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "页数" ,required = true)
    private Integer page;
    @ApiModelProperty(value = "每页条数",required = true)
    private Integer limit;
}
