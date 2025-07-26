package org.jeecg.modules.rider.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RiderReferenceDTO{

    @ApiModelProperty(value = "全部推广人总数")
    private Integer listCount;


    @ApiModelProperty(value = "会员推广人总数")
    private Integer oneListCount;

    @ApiModelProperty(value = "娘家人推广人总数")
    private Integer twoListCount;

    @ApiModelProperty(value = "主理人推广人总数")
    private Integer threeListCount;

    //推广人列表
    List<RiderCustomer> list;

}
