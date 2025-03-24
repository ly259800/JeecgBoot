package org.jeecg.modules.rider.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@ApiModel(value = "登录表单")
public class WxLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "code", required = true)
    private String code;

    @ApiModelProperty(value = "sessionKey", required = true)
    private String sessionKey;

    @ApiModelProperty(value = "是否退出登录(0-否,1-是)", required = true)
    private Integer isLoginOut;
}
