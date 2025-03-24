package org.jeecg.modules.rider.pay.constants;

/**
 * 公共错误码
 * @author leiyong
 * @date 2022-01-21
 */
public enum BaseErrorCodeEnum {

    /**
     * 请求处理成功
     **/
    REQ_SUCCESS(200,"SUCCESS", "请求处理成功"),

    /**
     * 关闭订单成功
     **/
    CLOSE_SUCCESS(204,"SUCCESS", "请求处理成功"),

    /**
     * 请求处理失败
     **/
    REQ_FAIL(400, "FAIL","请求处理失败"),
    /**
     * 参数异常码
     */
    USERPAYING(202, "USERPAYING","用户支付中，需要输入密码"),

    INVALID_REQUEST(400, "INVALID_REQUEST","无效请求"),
    APPID_MCHID_NOT_MATCH(400, "APPID_MCHID_NOT_MATCH","appid和mch_id不匹配"),
    /**请求参数异常 */
    PARAM_ERROR(400, "PARAM_ERROR" ,"参数错误"),
    REQ_PARAM_ERROR(400, "REQ_PARAM_ERROR","参数【%s】格式错误"),
    /**
     * 订单异常
     */
    ORDER_CLOSED(400, "ORDER_CLOSED","订单已关闭"),
    ORDER_PAID(400, "ORDER_PAID","订单已支付"),
    MCH_NOT_EXISTS(400, "MCH_NOT_EXISTS","商户号不存在"),
    /**
     * 支付异常
     */
    TRADE_TYPE_NOT_EXSIT(400,"TRADE_TYPE_NOT_EXSIT","交易类型【%s】不存在"),
    TRADE_STATE_NOT_EXSIT(400,"TRADE_STATE_NOT_EXSIT","交易状态【%s】不存在"),
    /** 支付回调出错 */
    CALLBACK_HANDLED(400, "CALLBACK_HANDLED" ,"订单支付回调【%s】已处理"),

    /*** 幂等出错**/
    IDEMPOTENT_ERROR(401,"IDEMPOTENT_ERROR", "幂等性校验异常!"),

    SIGN_ERROR(401, "SIGN_ERROR" ,"签名错误"),

    ACCOUNT_ERROR(403, "ACCOUNT_ERROR","账号异常"),
    OUT_TRADE_NO_USED(403, "OUT_TRADE_NO_USED" ,"商户订单号重复"),
    TRADE_ERROR(403,"TRADE_ERROR", "交易错误"),
    RULE_LIMIT(403,"RULE_LIMIT" ,"业务规则限制"),
    NOT_ENOUGH(403, "NOT_ENOUGH","余额不足"),
    NO_AUTH(403, "NO_AUTH","商户无权限"),

    ORDER_NO_TEXIST(404,"ORDER_NO_TEXIST" ,"订单不存在"),
    NOT_FOUND(404,"NOT_FOUND" ,"请求的资源不存在"),

    FREQUENCY_LIMITED(429,"FREQUENCY_LIMITED", "频率超限"),

    SYSTEM_ERROR(500,"SYSTEM_ERROR" ,"系统错误"),
    OPENID_MISMATCH(500, "OPENID_MISMATCH","openid和appid不匹配"),
    INVALID_TRANSACTIONID(500, "INVALID_TRANSACTIONID","订单号非法"),
    BANK_ERROR(500, "BANK_ERROR","银行系统异常"),
    PAY_UNKNOW_ERROR(500,"PAY_UNKNOW_ERROR", "系统错误，未知返回码【%s】")
	;

    BaseErrorCodeEnum(Integer status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 状态码
     */
    private final Integer status;

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误描述
     */
    private final String msg;

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
    public static BaseErrorCodeEnum getEnum(String code) {
        for (BaseErrorCodeEnum item : BaseErrorCodeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
