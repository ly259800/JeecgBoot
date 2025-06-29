package org.jeecg.modules.rider.course.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.course.entity.VideoCourse;
import org.jeecg.modules.rider.course.entity.VideoUnlockRecord;
import org.jeecg.modules.rider.course.service.IVideoCourseService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.rider.course.service.IVideoUnlockRecordService;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.system.entity.SysCategory;
import org.jeecg.modules.system.service.ISysCategoryService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 课程管理
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Api(tags="课程管理")
@RestController
@RequestMapping("/course/videoCourse")
@Slf4j
public class VideoCourseController extends JeecgController<VideoCourse, IVideoCourseService> {
	@Autowired
	private IVideoCourseService videoCourseService;

	 @Autowired
	 private ISysCategoryService sysCategoryService;

	 @Autowired
	 private IRiderCustomerService riderCustomerService;

	 @Autowired
	 private IVideoUnlockRecordService videoUnlockRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param videoCourse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "课程管理-分页列表查询")
	@ApiOperation(value="课程管理-分页列表查询", notes="课程管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VideoCourse>> queryPageList(VideoCourse videoCourse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        QueryWrapper<VideoCourse> queryWrapper = QueryGenerator.initQueryWrapper(videoCourse, req.getParameterMap(),customeRuleMap);
		Page<VideoCourse> page = new Page<VideoCourse>(pageNo, pageSize);
		IPage<VideoCourse> pageList = videoCourseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}


	 /**
	  * 列表查询
	  *
	  * @param videoCourse
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="课程管理-列表查询", notes="课程管理-列表查询")
	 @GetMapping(value = "/listForApp")
	 public Result<List<VideoCourse>> listForApp(VideoCourse videoCourse,
													 HttpServletRequest req) {
		 // 自定义查询规则
		 Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
		 QueryWrapper<VideoCourse> queryWrapper = QueryGenerator.initQueryWrapper(videoCourse, req.getParameterMap(),customeRuleMap);
		 List<VideoCourse> courseList = videoCourseService.list(queryWrapper);
		 //视频链接不开放，需要单独查询
		 for (VideoCourse course : courseList) {
			 course.setUrl(null);
		 }
		 return Result.OK(courseList);
	 }
	
	/**
	 *   添加
	 *
	 * @param videoCourse
	 * @return
	 */
	@AutoLog(value = "课程管理-添加")
	@ApiOperation(value="课程管理-添加", notes="课程管理-添加")
	@RequiresPermissions("course:video_course:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody VideoCourse videoCourse) {
		if(videoCourse.getPayType() == 1 && videoCourse.getPrice() == null){
			return Result.error("付费课程必须设置付费价格");
		}
		if(videoCourse.getClassification()!=null){
			SysCategory category = sysCategoryService.getById(videoCourse.getClassification());
			videoCourse.setClassificationName(category.getName());
		}
		videoCourseService.save(videoCourse);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param videoCourse
	 * @return
	 */
	@AutoLog(value = "课程管理-编辑")
	@ApiOperation(value="课程管理-编辑", notes="课程管理-编辑")
	@RequiresPermissions("course:video_course:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VideoCourse videoCourse) {
		if(videoCourse.getPayType() == 1 && videoCourse.getPrice() == null){
			return Result.error("付费课程必须设置付费价格");
		}
		if(videoCourse.getClassification()!=null){
			SysCategory category = sysCategoryService.getById(videoCourse.getClassification());
			videoCourse.setClassificationName(category.getName());
		}
		videoCourseService.updateById(videoCourse);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "课程管理-通过id删除")
	@ApiOperation(value="课程管理-通过id删除", notes="课程管理-通过id删除")
	@RequiresPermissions("course:video_course:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		videoCourseService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "课程管理-批量删除")
	@ApiOperation(value="课程管理-批量删除", notes="课程管理-批量删除")
	@RequiresPermissions("course:video_course:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.videoCourseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "课程管理-通过id查询")
	@ApiOperation(value="课程管理-通过id查询", notes="课程管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VideoCourse> queryById(@RequestParam(name="id",required=true) String id) {
		VideoCourse videoCourse = videoCourseService.getById(id);
		if(videoCourse==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(videoCourse);
	}

	 /**
	  * 获取视频链接
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "课程管理-通过id查询")
	 @ApiOperation(value="课程管理-获取视频链接", notes="课程管理-获取视频链接")
	 @GetMapping(value = "/queryUrlById")
	 public Result<VideoCourse> queryUrlById(@RequestParam(name="id",required=true) String id) {
		 //获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 VideoCourse videoCourse = videoCourseService.getById(id);
		 if(videoCourse==null) {
			 return Result.error("未找到对应数据");
		 }
		 //免费
		 if(videoCourse.getPayType() == 0) {
			 return Result.OK(videoCourse);
		 }
		 //判断是否是合伙人
		 RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
		if(Objects.equals(riderCustomer.getIdentity() , CustomerIdentityEnum.PARTNER.getCode())){
			return Result.OK(videoCourse);
		}
		 //不是合伙人，则判断视频是否已经解锁
		 VideoUnlockRecord videoUnlockRecord = videoUnlockRecordService.queryByUserIdAndCourseId(riderCustomer.getId(), id);
		 if(Objects.nonNull(videoUnlockRecord)){
			 return Result.OK(videoCourse);
		 } else {
			 return Result.error("请先解锁该课程");
		 }
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param videoCourse
    */
    @RequiresPermissions("course:video_course:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VideoCourse videoCourse) {
        return super.exportXls(request, videoCourse, VideoCourse.class, "课程管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("course:video_course:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VideoCourse.class);
    }

}
