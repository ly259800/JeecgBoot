package org.jeecg.modules.rider.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@ApiModel(value = "手机注册")
public class GetInviteCodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

}
