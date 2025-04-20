package org.jeecg.modules.pay.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信api接口返回对象
 * @author leiyong
 *
 */
@Data
@ToString
@Accessors(chain = true)
public class WechatPayApiOutDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	//接口响应状态
	private Integer status;

	//接口响应码
	private String code;
	
	//接口返回提示信息
	private String message;
	
	//接口返回数据主体
	private Object data;

}
