package org.jeecg.modules.rider.pay.dto;


import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 微信支付请求入参
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class WechatPayDTO {
    
    /** 商品描述 */
    @ApiModelProperty(value = "商品描述", required = true)
    @NotBlank
    private String description;

    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号", required = true)
    @NotBlank
    private String outTradeNo;

    /** 附加数据 */
    @ApiModelProperty(value = "附加数据")
    private String attach;

    /** 交易类型
     * @see TradeTypeEnum
     * */
    @ApiModelProperty(value = "交易类型", required = true)
    @NotNull
	private Integer tradeType;

    /** 订单总金额，单位为元 */
    @ApiModelProperty(value = "订单总金额，单位为元", required = true)
    @NotNull
    private BigDecimal totalAmount;

    /** 用户id */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Long userId;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 支付订单id
     */
    private Long payOrderId;
    
}
