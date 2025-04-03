package org.jeecg.modules.rider.customer.enums;

import java.util.Objects;

/**
 * 客户身份
 */
public enum CustomerIdentityEnum {

    /**
     * 游客
     */
    TOURIST(1,"游客"),

    /**
     * 骑手
     */
    RIDER(2,"骑手"),


    /**
     * 合伙人
     */
    PARTNER(3,"合伙人");

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
