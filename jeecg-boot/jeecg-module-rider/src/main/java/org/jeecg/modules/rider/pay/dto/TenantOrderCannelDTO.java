package org.jeecg.modules.rider.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户订单取消支付入参
 * @author leiyong
 * @date 2022-04-24
 */
@Data
public class TenantOrderCannelDTO {
    /**
     * 用户订单号
     */
    @ApiModelProperty(value = "订单id", required = true)
    @NotNull
    private String orderId;

}
