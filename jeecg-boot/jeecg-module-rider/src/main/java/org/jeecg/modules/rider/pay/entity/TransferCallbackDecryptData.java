package org.jeecg.modules.rider.pay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.rider.pay.constants.TransferStateEnum;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String mchId;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后一次状态变更时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
