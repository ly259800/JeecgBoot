package org.jeecg.modules.rider.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@ApiModel(value = "手机注册")
public class WxRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    private String mobile;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(value = "性别")
    @NotNull
    private Integer gender;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

    @ApiModelProperty(value = "省-code")
    private String regionProvince;

    @ApiModelProperty(value = "省-名称")
    private String regionProvinceName;

    @ApiModelProperty(value = "市-code")
    private String regionCity;

    @ApiModelProperty(value = "市-名称")
    private String regionCityName;

    @ApiModelProperty(value = "区/县-code")
    private String regionCounty;

    @ApiModelProperty(value = "区/县-名称")
    private String regionCountyName;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "注册类型，1：个人注册  2：机构注册")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "sessionKey", required = true)
    private String sessionKey;

}
