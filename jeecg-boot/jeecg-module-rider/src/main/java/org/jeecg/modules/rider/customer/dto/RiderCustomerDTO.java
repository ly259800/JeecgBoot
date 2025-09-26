package org.jeecg.modules.rider.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class RiderCustomerDTO extends RiderCustomer {

    @ApiModelProperty(value = "sessionKey", required = true)
    private String sessionKey;

    @ApiModelProperty(value = "性别 1-男 2-女")
    private Integer sex;

    @ApiModelProperty(value = "未入职")
    private Integer failCount;

    @ApiModelProperty(value = "已入职")
    private Integer passCount;

    @ApiModelProperty(value = "已结算")
    private Integer settleCount;

    @ApiModelProperty(value = "推广人名称")
    private java.lang.String promoterName;

    @ApiModelProperty(value = "自动移除剩余时间")
    private java.lang.String remainTime;

    //分享人用户id
    private java.lang.String shareUserId;

    //分享人用户手机号
    private java.lang.String shareUserPhone;

}
