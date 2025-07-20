package org.jeecg.modules.rider.interview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 我的招聘
 */
@Data
public class RiderInterviewDTO extends RiderInterview {

    @ApiModelProperty(value = "站长佣金")
    private java.lang.Integer siteCommission;

    /**付费类型*/
    @Excel(name = "付费类型", width = 15, dicCode = "pay_type")
    @Dict(dicCode = "pay_type")
    @ApiModelProperty(value = "付费类型")
    private java.lang.Integer payType;

    /**付费价格*/
    @Excel(name = "付费价格", width = 15)
    @ApiModelProperty(value = "付费价格")
    private java.math.BigDecimal price;

    /**薪资范围*/
    @Excel(name = "薪资范围", width = 15)
    @ApiModelProperty(value = "薪资范围")
    private java.lang.String salaryRange;

}
