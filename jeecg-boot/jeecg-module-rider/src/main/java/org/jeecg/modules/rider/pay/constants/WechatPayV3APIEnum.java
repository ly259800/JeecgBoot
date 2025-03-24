package org.jeecg.modules.rider.pay.constants;

import org.springframework.http.HttpMethod;

/**
 * 微信支付接口api.
 * @author leiyong
 * @date 2022-01-21
 */
public enum WechatPayV3APIEnum {

    /**
     * 微信公众号支付或者小程序支付.
     *
     */
    JSAPI(HttpMethod.POST, "%s/v3/pay/transactions/jsapi"),

    /**
     * 微信扫码支付.
     *
     */
    NATIVE(HttpMethod.POST, "%s/v3/pay/transactions/native"),

    /**
     * 微信APP支付.
     *
     */
    APP(HttpMethod.POST, "%s/v3/pay/transactions/app"),

    /**
     * H5支付.
     *
     */
    MWEB(HttpMethod.POST, "%s/v3/pay/transactions/h5"),
    /**
     * 关闭订单.
     *
     */
    CLOSE(HttpMethod.POST, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}/close"),
    /**
     * 微信支付订单号查询API.
     *
     */
    QUERY_TRANSACTION_ID(HttpMethod.GET, "%s/v3/pay/transactions/id/{transaction_id}"),
    /**
     * 商户订单号查询API.
     *
     */
    QUERY_OUT_TRADE_NO(HttpMethod.GET, "%s/v3/pay/transactions/out-trade-no/{out_trade_no}"),
    /**
     * 申请退款API.
     *
     */
    REFUND(HttpMethod.POST, "%s/v3/refund/domestic/refunds"),
    /**
     * 查询退款API.
     *
     */
    QUERY_REFUND(HttpMethod.GET, "%s/v3/refund/domestic/refunds/{out_refund_no}"),


    //----------------------------服务商模式--------------------------------------
    /**
     * 服务商APP下单API.
     *
     */
    APP_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/app"),

    /**
     * 微信公众号支付或者小程序支付.
     *
     */
    JSAPI_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/jsapi"),

    /**
     * 微信扫码支付.
     *
     */
    NATIVE_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/native"),

    /**
     * H5支付.
     *
     */
    MWEB_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/h5"),

    /**
     * 关闭订单.
     *
     */
    CLOSE_PARTNER(HttpMethod.POST, "%s/v3/pay/partner/transactions/out-trade-no/{out_trade_no}/close"),
    /**
     * 微信支付订单号查询API.
     *
     */
    TRANSACTION_TRANSACTION_ID_PARTNER(HttpMethod.GET, "%s/v3/pay/partner/transactions/id/{transaction_id}"),
    /**
     * 商户订单号查询API.
     *
     */
    TRANSACTION_OUT_TRADE_NO_PARTNER(HttpMethod.GET, "%s/v3/pay/partner/transactions/out-trade-no/{out_trade_no}");


    /**
     * 方法类型.
     *
     */
    private final HttpMethod method;

    /**
     * 请求地址.
     */
    private final String pattern;

    /**
     * 构造方法.
     */
    WechatPayV3APIEnum(HttpMethod method, String pattern) {
        this.method = method;
        this.pattern = pattern;
    }

    /**
     * 获取方法类型.
     */
    public HttpMethod method() {
        return this.method;
    }

    /**
     * 获取接口路径.
     */
    public String pattern() {
        return this.pattern;
    }

    /**
     * 获取微信支付URI.
     */
    public String uri(WeChatServerEnum weChatServerEnum) {
        return String.format(this.pattern, weChatServerEnum.domain());
    }
}
