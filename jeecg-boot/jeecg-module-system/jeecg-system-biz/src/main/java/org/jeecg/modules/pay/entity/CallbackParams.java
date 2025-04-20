package org.jeecg.modules.pay.entity;

import lombok.Data;

/**
 * @desc 微信支付回调请求参数.
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class CallbackParams {
    /**
     * 通知Id
     */
    private String id;
    /**
     * 通知创建时间
     */
    private String createTime;
    /**
     * 通知类型
     *
     */
    private String eventType;
    /**
     * 通知数据类型
     */
    private String resourceType;
    /**
     * 回调摘要
     */
    private String summary;
    /**
     * 通知数据
     */
    private Resource resource;

    /**
     * 通知数据
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Resource {
        /**
         * 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM。
         */
        private String algorithm;
        /**
         * Base64编码后的开启/停用结果数据密文。
         */
        private String ciphertext;
        /**
         * 附加数据。
         */
        private String associatedData;
        /**
         * 加密使用的随机串。
         */
        private String nonce;
        /**
         * 原始回调类型。
         */
        private String originalType;
    }

}
