package org.jeecg.modules.rider.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;

@Data
public class RiderCustomerDTO extends RiderCustomer {

    @ApiModelProperty(value = "sessionKey", required = true)
    private String sessionKey;

}
