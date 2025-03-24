package org.jeecg.modules.rider.pay.enums;

import java.util.Objects;

/**
 * 订单类型.
 *
 * @author leiyong
 * @date 2022-02-08
 */
public enum OrderTypeEnum {

    /**
     * 扩容
     */
    EXPAND(1,"扩容"),

    /**
     * 续费
     */
    RENEW(2,"续费");

    private Integer code;

    private String msg;

    OrderTypeEnum(Integer code, String msg) {
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
    public static OrderTypeEnum getEnum(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (OrderTypeEnum item : OrderTypeEnum.values()) {
            if (Objects.equals(item.getCode(),code)) {
                return item;
            }
        }
        return null;
    }
}
