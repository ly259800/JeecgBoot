package org.jeecg.modules.rider.pay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.rider.pay.constants.TransferStateEnum;

import java.util.Date;

/**
 * 商家转账微信回调解密数据
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class TransferCallbackDecryptData {
    /**
     * 商户单号
     */
    private String outBillNo;
    /**
     * 商家转账订单号
     */
    private String transferBillNo;
    /**
     * 转账状态
     * @see TransferStateEnum
     */
    private String state;
    /**
     * 商户号
     */
    private String mch_id;

    /**
     * 商户号
     */
    private String mchid;

    /**
     * 转账总金额，单位为分。
     */
    private Integer transferAmount;

    /**
     * 用户在直连商户appid下的唯一标识。
     */
    private String openid;

    /**
     * 失败原因
     */
    private String failReason;
    /**
     * 单据创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后一次状态变更时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private Date updateTime;


}
