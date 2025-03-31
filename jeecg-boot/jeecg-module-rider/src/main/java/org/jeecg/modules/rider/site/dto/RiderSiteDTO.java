package org.jeecg.modules.rider.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class RiderSiteDTO extends RiderSite {
    @Excel(name = "站长佣金", width = 15)
    @ApiModelProperty(value = "站长佣金")
    private java.lang.Integer site_commission;
}
