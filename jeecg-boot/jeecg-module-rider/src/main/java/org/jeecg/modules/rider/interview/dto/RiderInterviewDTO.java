package org.jeecg.modules.rider.interview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.interview.entity.RiderInterview;

/**
 * @Description: 我的招聘
 */
@Data
public class RiderInterviewDTO extends RiderInterview {

    @ApiModelProperty(value = "站长佣金")
    private java.lang.Integer siteCommission;

}
