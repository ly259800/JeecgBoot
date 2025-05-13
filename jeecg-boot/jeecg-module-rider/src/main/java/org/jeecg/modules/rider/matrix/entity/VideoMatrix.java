package org.jeecg.modules.rider.matrix.entity;

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
 * @Description: 矩阵管理
 * @Author: jeecg-boot
 * @Date:   2025-05-13
 * @Version: V1.0
 */
@Data
@TableName("video_matrix")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="video_matrix对象", description="矩阵管理")
public class VideoMatrix implements Serializable {
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
	/**我的视频*/
	@Excel(name = "我的视频", width = 15)
    @ApiModelProperty(value = "我的视频")
    private java.lang.String videoUrl;
	/**视频描述*/
	@Excel(name = "视频描述", width = 15)
    @ApiModelProperty(value = "视频描述")
    private java.lang.String content;
	/**发布抖音*/
	@Excel(name = "发布抖音", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "发布抖音")
    private java.lang.Integer releaseTiktok;
	/**发布快手*/
	@Excel(name = "发布快手", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "发布快手")
    private java.lang.Integer releaseKwai;
	/**发布视频号*/
	@Excel(name = "发布视频号", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "发布视频号")
    private java.lang.Integer releaseWechat;
	/**发布小红书*/
	@Excel(name = "发布小红书", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "发布小红书")
    private java.lang.Integer releaseRednote;
}
