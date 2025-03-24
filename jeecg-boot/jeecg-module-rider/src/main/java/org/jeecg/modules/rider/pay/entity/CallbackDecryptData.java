package org.jeecg.modules.rider.pay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.rider.pay.constants.TradeStateEnum;
import org.jeecg.modules.rider.pay.constants.TradeTypeEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 微信回调解密数据
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class CallbackDecryptData {

    /**
     * 直连模式应用ID，服务商模式请解析spAppid
     */
    private String appid;
    /**
     * 直连模式商户号，服务商模式请解析spMchid
     */
    private String mchid;
    /**
     * 服务商模式-服务商APPID
     */
    private String spAppid;
    /**
     * 服务商模式-服务商户号
     */
    private String spMchid;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 交易类型
     * @see TradeTypeEnum
     */
    private String tradeType;
    /**
     * 交易状态
     * @see TradeStateEnum
     */
    private String tradeState;
    /**
     * 交易状态描述
     */
    private String tradeStateDesc;
    /**
     * 银行类型，采用字符串类型的银行标识。银行标识请参考 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/terms_definition/chapter1_1_3.shtml#part-6">《银行类型对照表》</a>
     */
    private String bankType;

    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;
    /**
     * 支付完成时间 YYYY-MM-DDTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date successTime;
    /**
     * 支付者信息
     */
    private Payer payer;
    /**
     * 订单金额
     */
    private Amount amount;
    /**
     * 支付场景信息描述
     */
    private SceneInfo sceneInfo;
    /**
     * 优惠功能，享受优惠时返回该字段。
     */
    private List<PromotionDetail> promotionDetail;
    /**
     * 支付者信息
     * @author leiyong
     * @date 2022-01-21
     */
    @Data
    public static class Payer {
        /**
         * 用户在直连商户appid下的唯一标识。
         */
        private String openid;
    }

    /**
     * 支付场景信息描述
     * @author leiyong
     * @date 2022-01-21
     */
    @Data
    public static class SceneInfo {
        /**
         * 商户端设备号（门店号或收银设备ID）。
         */
        private String deviceId;
    }

    /**
     * 订单金额
     * @author leiyong
     * @date 2022-01-21
     */
    @Data
    public static class Amount {
        /**
         * 订单总金额，单位为分。
         */
        private Integer total;
        /**
         * 用户支付金额，单位为分。
         */
        private Integer payerTotal;
        /**
         * CNY：人民币，境内商户号仅支持人民币。
         */
        private String currency;
        /**
         * 用户支付币种
         */
        private String payerCurrency;
    }


}
