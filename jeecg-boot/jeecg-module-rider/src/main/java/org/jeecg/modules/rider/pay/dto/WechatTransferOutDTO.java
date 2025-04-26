package org.jeecg.modules.rider.pay.dto;

import lombok.Data;

/**
 * 微信商户转账响应
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class WechatTransferOutDTO {

    private String code;           // 返回码
    private String message;        // 返回信息
    private String outBillNo;      // 商户转账单号
    private String wxBillNo;       // 微信转账单号
    private Integer amount;        // 转账金额
    private String status;         // 转账状态

}
