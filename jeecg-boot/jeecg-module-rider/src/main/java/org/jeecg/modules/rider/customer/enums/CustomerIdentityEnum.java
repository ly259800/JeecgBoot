package org.jeecg.modules.rider.customer.enums;

import java.util.Objects;

/**
 * 客户身份
 */
public enum CustomerIdentityEnum {

    /**
     * 会员
     */
    TOURIST(1,"会员"),

    /**
     * 娘家人
     */
    RIDER(2,"娘家人"),


    /**
     * 主理人
     */
    PARTNER(3,"主理人");

    private Integer code;

    private String msg;

    CustomerIdentityEnum(Integer code, String msg) {
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
    public static CustomerIdentityEnum getEnum(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (CustomerIdentityEnum item : CustomerIdentityEnum.values()) {
            if (Objects.equals(item.getCode(),code)) {
                return item;
            }
        }
        return null;
    }
}
