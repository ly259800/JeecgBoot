package org.jeecg.modules.pay.exception;

import org.jeecg.modules.pay.constants.BaseErrorCodeEnum;

public class WechatPayException extends RuntimeException {

    /**
     * 具体状态码
     */
    protected Integer status;

    /**
     * 具体异常码
     */
    protected String code;

    /**
     * 异常信息
     */
    protected String msg;

    public WechatPayException(Integer status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }

    public WechatPayException(BaseErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public WechatPayException(BaseErrorCodeEnum errorCode, Object... args) {
        super(String.format(errorCode.getMsg(), args));
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.msg = String.format(errorCode.getMsg(), args);
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
}
