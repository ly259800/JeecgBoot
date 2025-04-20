package org.jeecg.modules.pay.dto;

import lombok.Data;

/**
 * 微信扫码登录接口返回值
 */
@Data
public class WxSacnLoginResDTO {
    /**
     * 接口调用凭证
     */
    private String access_token;
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private Long expires_in;
    /**
     * 用户刷新access_token
     */
    private String refresh_token;
    /**
     * 授权用户唯一标识
     */
    private String openid;
    /**
     *  用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
     */
    private String unionid;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    /**
     * 错误码
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;
}
