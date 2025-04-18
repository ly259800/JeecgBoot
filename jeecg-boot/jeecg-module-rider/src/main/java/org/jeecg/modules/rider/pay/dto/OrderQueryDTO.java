package org.jeecg.modules.rider.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 订单查询请求实体
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class OrderQueryDTO {
	
	/** 微信支付订单号 */
    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;
    
    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号", required = true)
    @NotBlank
    private String outTradeNo;

	/** 直连商户号 */
    @ApiModelProperty(value = "商户号")
	private String mchid;

}
