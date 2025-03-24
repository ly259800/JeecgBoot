package org.jeecg.modules.rider.pay.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租户订单信息表
 *
 * @author jack
 * @since 3.0 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_tenant_order")
public class SysTenantOrderEntity {
	private static final long serialVersionUID = 1L;

	/**
	* id
	*/
	@TableId
	private Long id;
	/**
	* 租户编码
	*/
	@TableField(fill = FieldFill.INSERT)
	private Long tenantCode;
	/**
	* 商户订单号
	*/
	private String outTradeNo;
	/**
	* 订单状态(0-未支付,1-支付成功,2-支付失败)
	*/
	private Integer orderState;
	/**
	* 订单类型(1-扩容,2-续费)
	*/
	private Integer orderType;
	/**
	* 当前用户数量
	*/
	private Integer currentUserNum;
	/**
	* 到期时间
	*/
	private Date expirationDate;
	/**
	* 扩容数量
	*/
	private Integer expandNum;
	/**
	* 扩容单价,单位为元
	*/
	private BigDecimal expandUnitPrice;
	/**
	* 续费年限
	*/
	private Integer renewYear;
	/**
	* 续费单价,单位为元
	*/
	private BigDecimal renewUnitPrice;
	/**
	* 续费时间起
	*/
	private Date renewDateStart;
	/**
	* 续费时间止
	*/
	private Date renewDateEnd;
	/**
	* 订单总金额,单位为元
	*/
	private BigDecimal totalAmount;
	/**
	* 实际支付金额,单位为元
	*/
	private BigDecimal actualAmount;
	/**
	* 商品描述
	*/
	private String description;
	/**
	* 支付人ID
	*/
	private Long payId;
	/**
	* 支付人名称
	*/
	private String payer;
	/**
	* 支付完成时间
	*/
	private Date successTime;
	/**
	* 取消人ID
	*/
	private Long cancelId;
	/**
	* 取消人
	*/
	private String canceler;
	/**
	* 取消时间
	*/
	private Date cancelTime;
	/**
	* 支付方式(1-微信 2-支付宝)
	*/
	private Integer paymentMethod;
	/**
	* 创建人ID
	*/
	private Long userId;
	/**
	* 创建人名称
	*/
	private String username;
	/**
	* 创建时间
	*/
	private Date createTime;
	/**
	* 更新时间
	*/
	private Date updateTime;
}
