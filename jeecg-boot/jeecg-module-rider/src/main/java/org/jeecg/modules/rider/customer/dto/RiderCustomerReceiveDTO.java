package org.jeecg.modules.rider.customer.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RiderCustomerReceiveDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;

    /**
     * 常用标签
     */
    private java.lang.String tag;

    /**
     * 意向阶段
     */
    private java.lang.String intention;

    /**
     * 岗位需求
     */
    private java.lang.String postRequirement;


}
