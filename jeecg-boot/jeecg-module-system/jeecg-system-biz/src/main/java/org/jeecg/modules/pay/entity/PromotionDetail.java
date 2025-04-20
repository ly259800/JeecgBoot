package org.jeecg.modules.pay.entity;

import lombok.Data;

import java.util.List;

/**
 * 微信回调解密数据中的优惠功能
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class PromotionDetail {

    /**
     * 优惠券面额，单位【分】
     */
    private Long amount;
    /**
     * 券ID
     */
    private String couponId;
    /**
     * 优惠币种，内商户号仅支持人民币 CNY
     */
    private String currency;
    /**
     * 单品列表信息
     */
    private List<GoodsDetail> goodsDetail;
    /**
     * 商户出资，单位为分
     */
    private Long merchantContribute;
    /**
     * 优惠名称
     */
    private String name;
    /**
     * 其他出资，单位为分
     */
    private Long otherContribute;
    /**
     * 优惠范围
     * <ul>
     *     <li>GLOBAL：全场代金券</li>
     *     <li>SINGLE：单品优惠</li>
     * </ul>
     */
    private String scope;
    /**
     * 活动ID
     */
    private String stockId;
    /**
     * 优惠类型
     * <ul>
     *     <li>CASH：充值</li>
     *     <li>NOCASH：预充值</li>
     * </ul>
     */
    private String type;
    /**
     * 微信出资，单位为分
     */
    private Long wechatpayContribute;

    /**
     * 单品列表信息
     * @author leiyong
     * @date 2022-01-21
     */
    @Data
    public static class GoodsDetail {

        /**
         * 商品编码
         */
        private String goodsId;
        /**
         * 商品数量
         */
        private Long quantity;
        /**
         * 商品单价
         */
        private Long unitPrice;
        /**
         * 商品优惠金额，单位【分】
         */
        private Long discountAmount;
        /**
         * 商品备注
         */
        private String goodsRemark;

    }
}
