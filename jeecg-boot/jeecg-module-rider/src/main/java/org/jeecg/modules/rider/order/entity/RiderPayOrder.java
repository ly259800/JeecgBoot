package org.jeecg.modules.rider.order.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 支付订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Data
@TableName("rider_pay_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rider_pay_order对象", description="支付订单")
public class RiderPayOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**应用ID*/
	@Excel(name = "应用ID", width = 15)
    @ApiModelProperty(value = "应用ID")
    private java.lang.String appid;
	/**商户号*/
	@Excel(name = "商户号", width = 15)
    @ApiModelProperty(value = "商户号")
    private java.lang.String mchid;
	/**商户订单号*/
	@Excel(name = "商户订单号", width = 15)
    @ApiModelProperty(value = "商户订单号")
    private java.lang.String outTradeNo;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
    private java.lang.String description;
	/**预支付交易会话标识*/
	@Excel(name = "预支付交易会话标识", width = 15)
    @ApiModelProperty(value = "预支付交易会话标识")
    private java.lang.String prePayId;
	/**微信支付订单号*/
	@Excel(name = "微信支付订单号", width = 15)
    @ApiModelProperty(value = "微信支付订单号")
    private java.lang.String transactionId;
	/**交易类型*/
	@Excel(name = "交易类型", width = 15, dicCode = "trade_type")
	@Dict(dicCode = "trade_type")
    @ApiModelProperty(value = "交易类型")
    private java.lang.Integer tradeType;
	/**交易状态*/
	@Excel(name = "交易状态", width = 15, dicCode = "trade_state")
	@Dict(dicCode = "trade_state")
    @ApiModelProperty(value = "交易状态")
    private java.lang.Integer tradeState;
	/**用户唯一标识*/
	@Excel(name = "用户唯一标识", width = 15)
    @ApiModelProperty(value = "用户唯一标识")
    private java.lang.String openid;
	/**订单总金额*/
	@Excel(name = "订单总金额", width = 15)
    @ApiModelProperty(value = "订单总金额")
    private java.math.BigDecimal totalAmount;
	/**货币类型*/
	@Excel(name = "货币类型", width = 15)
    @ApiModelProperty(value = "货币类型")
    private java.lang.String currency;
	/**用户支付金额*/
	@Excel(name = "用户支付金额", width = 15)
    @ApiModelProperty(value = "用户支付金额")
    private java.math.BigDecimal payAmount;
	/**用户支付币种类型*/
	@Excel(name = "用户支付币种类型", width = 15)
    @ApiModelProperty(value = "用户支付币种类型")
    private java.lang.String payCurrency;
	/**银行类型*/
	@Excel(name = "银行类型", width = 15)
    @ApiModelProperty(value = "银行类型")
    private java.lang.String bankType;
	/**支付完成时间*/
	@Excel(name = "支付完成时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "支付完成时间")
    private java.util.Date successTime;
	/**关闭状态*/
	@Excel(name = "关闭状态", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "关闭状态")
    private java.lang.Integer closeState;
}
