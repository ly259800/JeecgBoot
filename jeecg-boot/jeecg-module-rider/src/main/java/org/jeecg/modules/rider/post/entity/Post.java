package org.jeecg.modules.rider.post.entity;

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
 * @Description: 岗位管理
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Data
@TableName("family_post")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="family_post对象", description="岗位管理")
public class Post implements Serializable {
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
	/**岗位类型ID*/
    @Excel(name = "岗位类型ID", width = 15, dictTable = "sys_category", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "岗位类型ID")
    private java.lang.String categoryId;
	/**岗位类型名称*/
	@Excel(name = "岗位类型名称", width = 15)
    @ApiModelProperty(value = "岗位类型名称")
    private java.lang.String categoryName;
	/**标签*/
	@Excel(name = "标签", width = 15)
    @ApiModelProperty(value = "标签")
    private java.lang.String tag;
	/**薪资范围*/
	@Excel(name = "薪资范围", width = 15)
    @ApiModelProperty(value = "薪资范围")
    private java.lang.String salaryRange;
	/**岗位名称*/
	@Excel(name = "岗位名称", width = 15)
    @ApiModelProperty(value = "岗位名称")
    private java.lang.String postName;
	/**时薪*/
	@Excel(name = "时薪", width = 15)
    @ApiModelProperty(value = "时薪")
    private java.lang.String hourlyWage;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private java.lang.String address;
	/**经度*/
	@Excel(name = "经度", width = 15)
    @ApiModelProperty(value = "经度")
    private java.lang.String longitude;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
    @ApiModelProperty(value = "纬度")
    private java.lang.String latitude;
	/**佣金*/
	@Excel(name = "佣金", width = 15)
    @ApiModelProperty(value = "佣金")
    private java.math.BigDecimal commission;
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**福利*/
	@Excel(name = "福利", width = 15)
    @ApiModelProperty(value = "福利")
    private java.lang.String benefit;
	/**发布状态*/
	@Excel(name = "发布状态", width = 15)
    @ApiModelProperty(value = "发布状态")
    @Dict(dicCode = "publish_status")
    private java.lang.Integer publishStatus;
}
