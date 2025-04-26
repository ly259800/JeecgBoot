package org.jeecg.modules.rider.pay.dto;


import lombok.Data;

/**
 * 微信商户转账请求入参
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class WechatTransferDTO {
    private String openid;          // 用户openid
    private String outBillNo;      // 商户转账单号
    private Integer amount;        // 转账金额（分）
    private String remark;         // 转账备注
    private String userName;       // 用户真实姓名
}
