package org.jeecg.modules.rider.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录表单
 */
@Data
@ApiModel(value = "PC注册")
public class RegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message="{sysuser.realName.require}")
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message="{sysuser.mobile.require}")
    private String mobile;

    @ApiModelProperty(value = "性别")
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
    private Integer type;

}
