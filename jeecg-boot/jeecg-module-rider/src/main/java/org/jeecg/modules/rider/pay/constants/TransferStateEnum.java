package org.jeecg.modules.rider.pay.constants;

/**
 * 微信侧返回商户转账状态
 * @author leiyong
 * @date 2022-01-21
 */
public enum TransferStateEnum {
    /**
     * 单据已受理
     */
    ACCEPTED(0, "ACCEPTED", "单据已受理"),

    /**
     * 单据处理中
     */
    PROCESSING(1, "PROCESSING", "单据处理中"),
    /**
     * 待收款用户确认
     */
    WAIT_USER_CONFIRM(2, "WAIT_USER_CONFIRM", "待收款用户确认"),
    /**
     * 转账中
     */
    TRANSFERING(3, "TRANSFERING", "转账中"),
    /**
     * 支付成功
     */
    SUCCESS(4, "SUCCESS", "支付成功"),

    /**
     * 转账失败
     */
    FAIL(5, "FAIL", "转账失败"),

    /**
     * 撤销中
     */
    CANCELING(6, "CANCELING", "撤销中"),
    /**
     * 已撤销
     */
    CANCELLED(7, "CANCELLED", "已撤销");

    private final Integer status;
    private final String code;
    private final String msg;

    TransferStateEnum(Integer status, String code, String msg) {
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
    public static TransferStateEnum getEnum(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (TransferStateEnum item : TransferStateEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
