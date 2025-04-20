package org.jeecg.modules.pay.constants;

import java.util.Objects;

/**
 * 微信支付api类型
 * @author leiyong
 * @date 2022-04-27
 */
public enum WechatApiTypeEnum {
    /**
     * 公众号/小程序支付
     */
    JSAPI(1, "JSAPI", "公众号/小程序支付"),
    /**
     * 支付订单号查询
     */
    PAYORDER(2, "PAYORDERQUERY", "支付订单号查询"),
    /**
     * 商户订单号查询
     */
    OUTTRADENO(3, "OUTTRADENOQUERY", "商户订单号查询"),
    /**
     * 关闭订单
     */
    COLSE(4, "COLSE", "关闭订单");

    private final Integer status;
    private final String code;
    private final String msg;

    WechatApiTypeEnum(Integer status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 根据code获得枚举,未事先定义的异常码返回为空
     * @param code
     * @return
     */
    public static WechatApiTypeEnum getEnumByCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (WechatApiTypeEnum item : WechatApiTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 根据status获得枚举,未事先定义的异常码返回为空
     * @param status
     * @return
     */
    public static WechatApiTypeEnum getEnumByStatus(Integer status) {
        if (Objects.isNull(status)) {
            return null;
        }
        for (WechatApiTypeEnum item : WechatApiTypeEnum.values()) {
            if (Objects.equals(item.getStatus(),status)) {
                return item;
            }
        }
        return null;
    }


}
