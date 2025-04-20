package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 二维码
 */
@Data
@ApiModel(value = "二维码")
public class WxQrcodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**二维码链接*/
    @Excel(name = "二维码链接", width = 15)
    @ApiModelProperty(value = "二维码链接")
    private java.lang.String url;
    /**二维码传参*/
    @Excel(name = "二维码传参", width = 15)
    @ApiModelProperty(value = "二维码传参")
    private java.lang.String scene;

    @Excel(name = "二维码跳转页面", width = 15)
    @ApiModelProperty(value = "二维码跳转页面")
    private java.lang.String page;

    @Excel(name = "小程序版本", width = 15)
    @ApiModelProperty(value = "小程序版本")
    private java.lang.String envVersion;
    /**手机号*/
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phone;
}
