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

    /**薪资范围*/
    @Excel(name = "薪资范围", width = 15)
    @ApiModelProperty(value = "薪资范围")
    private java.lang.String salaryRange;

    /**联系人*/
    @Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private java.lang.String contacts;

    /**是否显示岗位培训*/
    @Excel(name = "是否显示岗位培训", width = 15)
    @ApiModelProperty(value = "是否显示岗位培训")
    private java.lang.Integer showTrainStatus;


    /**岗位类型*/
    @Excel(name = "岗位类型", width = 15)
    @ApiModelProperty(value = "岗位类型")
    private java.lang.String categoryName;


    @ApiModelProperty(value = "申请人名称")
    private java.lang.String applyUserName;

    @ApiModelProperty(value = "主理人名称")
    private java.lang.String promoterName;

}
