package org.jeecg.modules.rider.pay.enums;

/**
 * 支付方式.
 *
 * @author leiyong
 * @date 2022-02-10
 */
public enum PayMethodEnum {

    /**
     * 微信
     */
    WECHAT(1,"微信"),

    /**
     * 支付宝
     */
    ALIPAY(2,"支付宝");

    private Integer code;

    private String msg;

    PayMethodEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
