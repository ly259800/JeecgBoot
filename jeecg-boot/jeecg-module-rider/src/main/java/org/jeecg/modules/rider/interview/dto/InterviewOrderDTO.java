package org.jeecg.modules.rider.interview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 单据信息
 */
@Data
public class InterviewOrderDTO {
    /**单据内容*/
    @Excel(name = "单据内容", width = 15)
    @ApiModelProperty(value = "单据内容")
    private String content;

}
