package org.jeecg.modules.rider.talentpool.entity;

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
 * @Description: 人才库
 * @Author: jeecg-boot
 * @Date:   2025-10-19
 * @Version: V1.0
 */
@Data
@TableName("family_talent_pool")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="family_talent_pool对象", description="人才库")
public class FamilyTalentPool implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phone;

    @Excel(name = "性别", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别")
    private java.lang.Integer sex;

    /**年龄*/
    @Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private java.lang.Integer age;

	/**常用标签*/
	//@Excel(name = "常用标签", width = 15)
    @ApiModelProperty(value = "常用标签")
    private java.lang.String tag;
	/**意向阶段*/
	//@Excel(name = "意向阶段", width = 15)
    @ApiModelProperty(value = "意向阶段")
    private java.lang.String intention;
	/**岗位需求*/
	//@Excel(name = "岗位需求", width = 15)
    @ApiModelProperty(value = "岗位需求")
    private java.lang.String postRequirement;
	/**领取人*/
	//@Excel(name = "领取人", width = 15)
    @ApiModelProperty(value = "领取人")
    private java.lang.String receiver;
	/**领取状态*/
	//@Excel(name = "领取状态", width = 15)
    @ApiModelProperty(value = "领取状态")
    private java.lang.Integer receiveStatus;
	/**申请状态*/
	//@Excel(name = "申请状态", width = 15)
    @ApiModelProperty(value = "申请状态")
    private java.lang.Integer applyStatus;
	/**领取日期*/
	//@Excel(name = "领取日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领取日期")
    private java.util.Date receiveTime;

    //客户id
    private java.lang.String customerId;
}
