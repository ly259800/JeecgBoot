package org.jeecg.modules.rider.pay.constants;

import java.util.Objects;

/**
 * 微信侧返回交易类型
 * @author leiyong
 * @date 2022-01-21
 */
public enum TradeTypeEnum {
    /**
     * 公众号/小程序支付
     */
    JSAPI(1, "JSAPI", "公众号/小程序支付"),
    /**
     * 扫码支付
     */
    NATIVE(2, "NATIVE", "扫码支付"),
    /**
     * APP支付
     */
    APP(3, "APP", "APP支付"),
    /**
     * 付款码支付
     */
    MICROPAY(4, "MICROPAY", "付款码支付"),
    /**
     * H5支付
     */
    MWEB(5, "MWEB", "H5支付"),
    /**
     * 刷脸支付
     */
    FACEPAY(6, "FACEPAY", "刷脸支付");

    private final Integer status;
    private final String code;
    private final String msg;

    TradeTypeEnum(Integer status, String code, String msg) {
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
    public static TradeTypeEnum getEnumByCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (TradeTypeEnum item : TradeTypeEnum.values()) {
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
    public static TradeTypeEnum getEnumByStatus(Integer status) {
        if (Objects.isNull(status)) {
            return null;
        }
        for (TradeTypeEnum item : TradeTypeEnum.values()) {
            if (Objects.equals(item.getStatus(),status)) {
                return item;
            }
        }
        return null;
    }


}
