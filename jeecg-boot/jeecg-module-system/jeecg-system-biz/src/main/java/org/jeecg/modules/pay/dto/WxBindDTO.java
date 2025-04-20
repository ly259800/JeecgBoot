package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 微信绑定
 * @author leiyong
 * @date 2022-03-07
 */
@Data
@ApiModel(value = "微信绑定")
public class WxBindDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否绑定微信自动登录(0-否,1-是)")
    @NotNull
    private Integer bindFlag;

    @ApiModelProperty(value = "登录方式(1-微信授权登录,2-账号密码登录)")
    @NotNull
    private Integer loginType;

    @ApiModelProperty(value = "头像", required = true)
    private String headUrl;

    private String sessionKey;

    private String encryptedData;

    private String iv;
}
