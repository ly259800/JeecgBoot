package org.jeecg.modules.rider.pay.constants;

/**
 * 事件类型用于处理回调.
 *
 * @author leiyong
 * @date 2022-01-21
 */
public enum EventTypeEnum {

    /**
     * 支付成功事件.
     */
    TRANSACTION("TRANSACTION.SUCCESS"),

    /**
     * 退款成功事件.
     */
    REFUND_SUCCESS("REFUND.SUCCESS"),

    /**
     * 退款异常事件.
     */
    REFUND_ABNORMAL("REFUND.ABNORMAL"),

    /**
     * 退款关闭事件.
     */
    REFUND_CLOSED("REFUND.CLOSED");

    /**
     * The Event.
     */
    private final String event;

    /**
     * Instantiates a new Event type.
     * @param event the event
     */
    EventTypeEnum(String event) {
        this.event = event;
    }

    public String getEnvent(){
        return this.event;
    }

}
