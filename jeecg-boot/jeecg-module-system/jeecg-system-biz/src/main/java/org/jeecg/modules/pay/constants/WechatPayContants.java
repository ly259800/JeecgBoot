package org.jeecg.modules.pay.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WechatPayContants {

	/**
	 * json转换对象
	 */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 回调类型
	 * @author leiyong
	 *
	 */
	public static class NotifyType {
		//支付回调
		public static final Integer PAY_CALLBACK = 1;
		//退款回调
		public static final Integer REFUND_CALLBACK = 2;
	}
	
	/**
	 * 幂等业务类型
	 * @author leiyong
	 *
	 */
	public static class IdepmentType{
		// 支付
		public static final Integer PAY = 1;
		// 退款
		public static final Integer REFUND = 2;
	}

	/**
	 * 支付订单关闭状态
	 * @author leiyong
	 */
	public static class PayCloseStatus{
		// 开启
		public static final int OPEN = 0;
		// 关闭
		public static final int CLOSE = 1;
	}
	
	/**
	 * 日志记录类型
	 * @author leiyong
	 *
	 */
	public static class LogRecordType {
		// 小程序调用
		public static final String FROM_APPLET = "1";
		// 接口调用微信API
		public static final String TO_WXPAY = "2";
	}

}
