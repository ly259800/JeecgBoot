package org.jeecg.modules.video.course.entity;

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
 * @Description: 课程管理
 * @Author: jeecg-boot
 * @Date:   2025-04-22
 * @Version: V1.0
 */
@Data
@TableName("video_course")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="video_course对象", description="课程管理")
public class VideoCourse implements Serializable {
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
	/**分类*/
    @Excel(name = "分类", width = 15, dictTable = "sys_category", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "分类")
    private java.lang.String classification;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**付费类型*/
	@Excel(name = "付费类型", width = 15, dicCode = "pay_type")
	@Dict(dicCode = "pay_type")
    @ApiModelProperty(value = "付费类型")
    private java.lang.Integer payType;
	/**付费价格*/
	@Excel(name = "付费价格", width = 15)
    @ApiModelProperty(value = "付费价格")
    private java.math.BigDecimal price;
	/**视频链接*/
	@Excel(name = "视频链接", width = 15)
    @ApiModelProperty(value = "视频链接")
    private java.lang.String url;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sort;
}
