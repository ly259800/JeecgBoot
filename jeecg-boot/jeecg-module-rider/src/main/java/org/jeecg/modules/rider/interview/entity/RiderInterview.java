package org.jeecg.modules.rider.interview.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Data
@TableName("rider_interview")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rider_interview对象", description="面试管理")
public class RiderInterview implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private java.lang.String name;
	/**性别*/
	@Excel(name = "性别", width = 15, dicCode = "sex")
	@Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别")
    private java.lang.Integer sex;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private java.lang.Integer age;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    @ApiModelProperty(value = "手机号码")
    private java.lang.String phone;
	/**报道城市*/
	@Excel(name = "报道城市", width = 15)
    @ApiModelProperty(value = "报道城市")
    private java.lang.String city;
	/**是否全职*/
	@Excel(name = "是否全职", width = 15)
    @ApiModelProperty(value = "是否全职")
    private java.lang.Integer jobType;
	/**是否需要住宿*/
	@Excel(name = "是否需要住宿", width = 15)
    @ApiModelProperty(value = "是否需要住宿")
    private java.lang.Integer accommodation;
	/**是否需要购买社保*/
	@Excel(name = "是否需要购买社保", width = 15)
    @ApiModelProperty(value = "是否需要购买社保")
    private java.lang.Integer socialSecurity;
	/**报道站点*/
	@Excel(name = "报道站点", width = 15)
    @ApiModelProperty(value = "报道站点")
    private java.lang.String siteName;
	/**期望区域地址*/
    @Excel(name = "期望区域地址", width = 15,exportConvert=true,importConvert = true )
    @ApiModelProperty(value = "期望区域地址")
    private java.lang.String expectRegion;

    public String convertisExpectRegion() {
        return SpringContextUtils.getBean(ProvinceCityArea.class).getText(expectRegion);
    }

    public void convertsetExpectRegion(String text) {
        this.expectRegion = SpringContextUtils.getBean(ProvinceCityArea.class).getCode(text);
    }
	/**推广人*/
	@Excel(name = "推广人", width = 15)
    @ApiModelProperty(value = "推广人")
    private java.lang.String reference;
	/**数据来源*/
	@Excel(name = "数据来源", width = 15)
    @ApiModelProperty(value = "数据来源")
    private java.lang.String source;
	/**面试状态*/
	@Excel(name = "面试状态", width = 15)
    @ApiModelProperty(value = "面试状态")
    private java.lang.Integer status;
	/**通过状态*/
	@Excel(name = "通过状态", width = 15)
    @ApiModelProperty(value = "通过状态")
    private java.lang.Integer passStatus;
}
