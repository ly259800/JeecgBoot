package org.jeecg.modules.order.entity;

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
 * @Description: 用户个人订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Data
@TableName("rider_user_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rider_user_order对象", description="用户个人订单")
public class RiderUserOrder implements Serializable {
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
	/**客户ID*/
	@Excel(name = "客户ID", width = 15)
    @ApiModelProperty(value = "客户ID")
    private java.lang.String customerId;
	/**商户订单号*/
	@Excel(name = "商户订单号", width = 15)
    @ApiModelProperty(value = "商户订单号")
    private java.lang.String outTradeNo;
	/**订单状态*/
	@Excel(name = "订单状态", width = 15, dicCode = "order_state")
	@Dict(dicCode = "order_state")
    @ApiModelProperty(value = "订单状态")
    private java.lang.Integer orderState;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15, dicCode = "order_type")
	@Dict(dicCode = "order_type")
    @ApiModelProperty(value = "订单类型")
    private java.lang.Integer orderType;
	/**订单总金额*/
	@Excel(name = "订单总金额", width = 15)
    @ApiModelProperty(value = "订单总金额")
    private java.math.BigDecimal totalAmount;
	/**实际支付金额*/
	@Excel(name = "实际支付金额", width = 15)
    @ApiModelProperty(value = "实际支付金额")
    private java.math.BigDecimal actualAmount;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
    @ApiModelProperty(value = "商品描述")
    private java.lang.String description;
	/**支付人ID*/
	@Excel(name = "支付人ID", width = 15)
    @ApiModelProperty(value = "支付人ID")
    private java.lang.String payId;
	/**支付人名称*/
	@Excel(name = "支付人名称", width = 15)
    @ApiModelProperty(value = "支付人名称")
    private java.lang.String payer;
	/**支付完成时间*/
	@Excel(name = "支付完成时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "支付完成时间")
    private java.util.Date successTime;
	/**取消人ID*/
	@Excel(name = "取消人ID", width = 15)
    @ApiModelProperty(value = "取消人ID")
    private java.lang.String cancelId;
	/**取消人*/
	@Excel(name = "取消人", width = 15)
    @ApiModelProperty(value = "取消人")
    private java.lang.String canceler;
	/**取消时间*/
	@Excel(name = "取消时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "取消时间")
    private java.util.Date cancelTime;
	/**支付方式*/
	@Excel(name = "支付方式", width = 15)
    @ApiModelProperty(value = "支付方式")
    private java.lang.Integer paymentMethod;
}
