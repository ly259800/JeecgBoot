package org.jeecg.modules.rider.pay.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
* 租户订单信息表
*
* @author admin 
* @since 3.0 2022-02-08
*/
@Data
@ApiModel(value = "租户订单信息表")
public class TenantOrderDTO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "租户编码")
    private Long tenantCode;
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;
    @ApiModelProperty(value = "订单状态(0-未支付,1-支付成功,2-支付失败,3-取消支付)")
    private Integer orderState;
    @ApiModelProperty(value = "订单类型(1-扩容,2-续费)")
    private Integer orderType;
    @ApiModelProperty(value = "当前用户数量")
    private Integer currentUserNum;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "到期时间")
    private Date expirationDate;
    @ApiModelProperty(value = "扩容数量")
    private Integer expandNum;
    @ApiModelProperty(value = "扩容单价,单位为元")
    private BigDecimal expandUnitPrice;
    @ApiModelProperty(value = "续费年限")
    private Integer renewYear;
    @ApiModelProperty(value = "续费单价,单位为元")
    private BigDecimal renewUnitPrice;
    @ApiModelProperty(value = "续费时间起")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date renewDateStart;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "续费时间止")
    private Date renewDateEnd;
    @ApiModelProperty(value = "订单总金额,单位为元")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "商品描述")
    private String description;
    @ApiModelProperty(value = "支付人ID")
    private Long payId;
    @ApiModelProperty(value = "支付人名称")
    private String payer;
    @ApiModelProperty(value = "支付完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date successTime;
    @ApiModelProperty(value = "支付方式(1-微信 2-支付宝)")
    private Integer paymentMethod;
    @ApiModelProperty(value = "创建人ID")
    private Long userId;
    @ApiModelProperty(value = "创建人名称")
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
