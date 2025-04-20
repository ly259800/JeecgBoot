package org.jeecg.modules.pay.constants;


/**
 * 微信支付服务器地址
 *
 * @author leiyong
 * @date 2022-01-21
 */
public enum WeChatServerEnum {
    /**
     * 中国
     */
    CHINA("https://api.mch.weixin.qq.com"),
    /**
     * 中国国内(备用域名)
     */
    CHINA2("https://api2.mch.weixin.qq.com"),
    /**
     * 香港
     */
    HK("https://apihk.mch.weixin.qq.com"),
    /**
     * 美国
     */
    US("https://apius.mch.weixin.qq.com");

    /**
     * 域名
     */
    private final String domain;

    WeChatServerEnum(String domain) {
        this.domain = domain;
    }

    public String domain() {
        return domain;
    }

    @Override
    public String toString() {
        return domain;
    }
}
