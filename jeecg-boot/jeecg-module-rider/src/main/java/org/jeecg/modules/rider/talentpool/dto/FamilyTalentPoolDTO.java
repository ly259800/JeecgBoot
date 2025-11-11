package org.jeecg.modules.rider.talentpool.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;

@Data
public class FamilyTalentPoolDTO extends FamilyTalentPool {


    @ApiModelProperty(value = "推广人名称")
    private java.lang.String promoterName;

    @ApiModelProperty(value = "领取人名称")
    private java.lang.String receiverName;

    @ApiModelProperty(value = "自动移除剩余时间")
    private java.lang.String remainTime;




}
