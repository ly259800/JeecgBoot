package org.jeecg.modules.pay.constants;

/**
 * 微信侧返回交易状态
 * @author leiyong
 * @date 2022-01-21
 */
public enum TradeStateEnum {
    /**
     * 未支付
     */
    NOTPAY(0, "NOTPAY", "未支付"),
    /**
     * 支付成功
     */
    SUCCESS(1, "SUCCESS", "支付成功"),
    /**
     * 转入退款
     */
    REFUND(2, "REFUND", "转入退款"),
    /**
     * 已关闭
     */
    CLOSED(3, "CLOSED", "已关闭"),
    /**
     * 已撤销（付款码支付）
     */
    REVOKED(4, "REVOKED", "已撤销"),
    /**
     * 用户支付中（付款码支付）
     */
    USERPAYING(5, "USERPAYING", "用户支付中"),
    /**
     * 支付失败(其他原因，如银行返回失败)
     */
    PAYERROR(6, "PAYERROR", "支付失败");

    private final Integer status;
    private final String code;
    private final String msg;

    TradeStateEnum(Integer status, String code, String msg) {
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
    public static TradeStateEnum getEnum(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (TradeStateEnum item : TradeStateEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
