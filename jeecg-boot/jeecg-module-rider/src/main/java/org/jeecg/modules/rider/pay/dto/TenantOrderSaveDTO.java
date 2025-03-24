package org.jeecg.modules.rider.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 租户订单请求入参
 * @author leiyong
 * @date 2022-02-08
 */
@Data
public class TenantOrderSaveDTO {
    /**
     * 租户订单号
     */
    @ApiModelProperty(value = "租户订单号", required = true)
    private String outTradeNo;

    /** 到期时间 */
    @ApiModelProperty(value = "到期时间", required = true)
    @NotBlank
    private String expirationDate;

    /** 当前用户数量 */
    @ApiModelProperty(value = "当前用户数量", required = true)
    @NotNull
    private Integer currentUserNum;

    /** 扩容数量 */
    @ApiModelProperty(value = "扩容数量", required = true)
    @NotNull
    private Integer expandNum;

    /** 扩容单价 */
    @ApiModelProperty(value = "扩容单价,单位为元")
    private BigDecimal expandUnitPrice;

    /** 续费年限 */
    @ApiModelProperty(value = "续费年限", required = true)
    @NotNull
    private Integer renewYear;

    @ApiModelProperty(value = "续费单价,单位为元")
    private BigDecimal renewUnitPrice;

    /** 订单总金额，单位为元 */
    @ApiModelProperty(value = "订单总金额，单位为元", required = true)
    @NotNull
    private BigDecimal totalAmount;

    /** 订单类型 */
    @ApiModelProperty(value = "订单类型，1-扩容 2-续费", required = true)
    @NotNull
    private Integer orderType;

    /** 商品描述 */
    @ApiModelProperty(value = "商品描述", required = true)
    @NotBlank
    private String description;

}
