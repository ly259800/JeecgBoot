package org.jeecg.modules.rider.interview.enums;

import java.util.Objects;

/**
 * 面试登记入口
 */
public enum InterviewEntranceEnum {

    /**
     * 骑手
     */
    RIDER(1,"骑手"),

    /**
     * 合伙人
     */
    PARTNER(2,"合伙人");

    private Integer code;

    private String msg;

    InterviewEntranceEnum(Integer code, String msg) {
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
    public static InterviewEntranceEnum getEnum(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (InterviewEntranceEnum item : InterviewEntranceEnum.values()) {
            if (Objects.equals(item.getCode(),code)) {
                return item;
            }
        }
        return null;
    }
}
