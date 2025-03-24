package org.jeecg.modules.rider.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单信息表
 *
 * @author leiyong
 * @since 3.0 2022-01-27
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("tb_pay_orderinfo")
public class PayOrderinfoEntity {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 直连商户号
	 */
	private String mchid;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 商品描述
	 */
	private String description;
	/**
	 * 预支付交易会话标识(有效期为2小时)
	 */
	private String prePayId;
	/**
	 * 微信支付订单号
	 */
	private String transactionId;
	/**
	 * 交易类型(1-公众号/小程序支付,2-扫码支付,3-APP支付,4-付款码支付,5-H5支付,6-刷脸支付)
	 */
	private Integer tradeType;
	/**
	 * 交易状态(0-未支付,1-支付成功,2-转入退款,3-已关闭,4-已撤销,5-用户支付中,6-支付失败)
	 */
	private Integer tradeState;
	/**
	 * 用户在直连商户appid下的唯一标识
	 */
	private String openid;
	/**
	 * 订单总金额,单位为元
	 */
	private BigDecimal totalAmount;
	/**
	 * 货币类型(CNY-人民币)
	 */
	private String currency;
	/**
	 * 用户支付金额,单位为元
	 */
	private BigDecimal payAmount;
	/**
	 * 用户支付币种类型(CNY-人民币)
	 */
	private String payCurrency;
	/**
	 * 银行类型
	 */
	private String bankType;
	/**
	 * 支付完成时间
	 */
	private Date successTime;
	/**
	 * 关闭状态(0-未关闭,1-已关闭)
	 */
	private Integer closeState;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
}