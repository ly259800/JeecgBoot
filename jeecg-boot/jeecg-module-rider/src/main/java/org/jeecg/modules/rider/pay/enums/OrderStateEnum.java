package org.jeecg.modules.rider.pay.enums;

import java.util.Objects;

/**
 * 订单状态.
 *
 * @author leiyong
 * @date 2022-02-08
 */
public enum OrderStateEnum {

    /**
     * 未支付
     */
    NOTPAY(0,"未支付"),

    /**
     * 支付成功
     */
    SUCCESS(1,"支付成功"),

    /**
     * 支付失败
     */
    PAYERROR(2,"支付失败"),
    /**
     * 取消支付
     */
    CANNEL(3,"取消支付");

    private Integer code;

    private String msg;

    OrderStateEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
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
    public static OrderStateEnum getEnum(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (OrderStateEnum item : OrderStateEnum.values()) {
            if (Objects.equals(item.getCode(),code)) {
                return item;
            }
        }
        return null;
    }
}
