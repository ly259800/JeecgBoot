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
public class InviteJoinDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "短信验证码")
    private String  smsCode;

    @ApiModelProperty(value = "邀请码")
    private String invitedCode;

    @ApiModelProperty(value = "sessionKey", required = true)
    private String sessionKey;

}
