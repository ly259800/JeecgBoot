package org.jeecg.modules.rider.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RiderReferenceDTO{

    @ApiModelProperty(value = "推广人总数")
    private Integer listCount;

    @ApiModelProperty(value = "佣金总数")
    private BigDecimal commissionCount;


    //推广人列表
    List<RiderCustomer> list;

}
