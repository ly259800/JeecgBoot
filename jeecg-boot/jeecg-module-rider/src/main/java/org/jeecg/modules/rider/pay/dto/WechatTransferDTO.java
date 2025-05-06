package org.jeecg.modules.rider.pay.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信商户转账请求入参
 * @author leiyong
 * @date 2022-01-21
 */
@Data
public class WechatTransferDTO {
    @ApiModelProperty(value = "用户openid", required = true)
    @NotBlank
    private String openid;          // 用户openid
    @ApiModelProperty(value = "商户转账单号", required = true)
    @NotBlank
    private String outBillNo;      // 商户转账单号
    @ApiModelProperty(value = "转账金额（分）", required = true)
    @NotNull
    private Integer amount;        // 转账金额（分）
    @ApiModelProperty(value = "转账备注", required = true)
    @NotBlank
    private String remark;         // 转账备注
    @ApiModelProperty(value = "用户真实姓名")
    private String userName;       // 用户真实姓名

    //提现订单ID
    private String payOrderId;
}
