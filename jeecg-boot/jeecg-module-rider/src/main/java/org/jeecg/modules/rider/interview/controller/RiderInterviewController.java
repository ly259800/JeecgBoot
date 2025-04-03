package org.jeecg.modules.rider.interview.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 面试管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Api(tags="面试管理")
@RestController
@RequestMapping("/interview/riderInterview")
@Slf4j
public class RiderInterviewController extends JeecgController<RiderInterview, IRiderInterviewService> {
	@Autowired
	private IRiderInterviewService riderInterviewService;

	 @Autowired
	 private IRiderCustomerService riderCustomerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderInterview
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "面试管理-分页列表查询")
	@ApiOperation(value="面试管理-分页列表查询", notes="面试管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderInterview>> queryPageList(RiderInterview riderInterview,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("status", QueryRuleEnum.LIKE_WITH_OR);
        customeRuleMap.put("passStatus", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<RiderInterview> queryWrapper = QueryGenerator.initQueryWrapper(riderInterview, req.getParameterMap(),customeRuleMap);
		Page<RiderInterview> page = new Page<RiderInterview>(pageNo, pageSize);
		IPage<RiderInterview> pageList = riderInterviewService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   骑手报名
	 */
	@AutoLog(value = "骑手报名")
	@ApiOperation(value="骑手报名", notes="骑手报名")
	@RequiresPermissions("interview:rider_interview:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderInterview riderInterview) {
		RiderInterview one = riderInterviewService.getOne(new QueryWrapper<RiderInterview>().eq("phone", riderInterview.getPhone()).eq("entrance", 1));
		if(one != null){
			return Result.error("该手机号已报名");
		}
		//小程序入口，1-骑手
		riderInterview.setEntrance(1);
		riderInterviewService.save(riderInterview);
		return Result.OK("报名成功！");
	}

	 /**
	  *   站点申请
	  */
	 @AutoLog(value = "站点申请")
	 @ApiOperation(value="站点申请", notes="站点申请")
	 @RequiresPermissions("interview:rider_interview:add")
	 @PostMapping(value = "/siteAdd")
	 public Result<String> siteAdd(@RequestBody RiderInterview riderInterview) {
		 if(StringUtils.isEmpty(riderInterview.getSiteId()) || StringUtils.isEmpty(riderInterview.getSiteName())){
			 return Result.error("站点不能为空");
		 }
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if(Objects.isNull(sysUser)){
			 throw new JeecgBootException("请先登录");
		 }
		 RiderCustomer riderCustomer = riderCustomerService.getByPhone(sysUser.getPhone());
		 if(riderCustomer == null){
			 return Result.error("用户未注册");
		 }
		 if(riderCustomer.getIdentity() != CustomerIdentityEnum.PARTNER.getCode() ){
			 return Result.error("您当前不是合伙人，不能申请");
		 }
		 RiderInterview one = riderInterviewService.getOne(new QueryWrapper<RiderInterview>().eq("phone", riderInterview.getPhone()).eq("entrance", 2));
		 if(one != null){
			 riderInterview.setId(one.getId());
			 riderInterviewService.updateById(riderInterview);
			 return Result.OK("登记信息修改成功！");
		 }
		 //小程序入口，2-合伙人
		 riderInterview.setEntrance(2);
		 riderInterview.setReference(riderCustomer.getId());
		 riderInterviewService.save(riderInterview);
		 return Result.OK("登记成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param riderInterview
	 * @return
	 */
	@AutoLog(value = "面试管理-编辑")
	@ApiOperation(value="面试管理-编辑", notes="面试管理-编辑")
	@RequiresPermissions("interview:rider_interview:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderInterview riderInterview) {
		riderInterviewService.updateById(riderInterview);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "面试管理-通过id删除")
	@ApiOperation(value="面试管理-通过id删除", notes="面试管理-通过id删除")
	@RequiresPermissions("interview:rider_interview:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderInterviewService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "面试管理-批量删除")
	@ApiOperation(value="面试管理-批量删除", notes="面试管理-批量删除")
	@RequiresPermissions("interview:rider_interview:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderInterviewService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "面试管理-通过id查询")
	@ApiOperation(value="面试管理-通过id查询", notes="面试管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderInterview> queryById(@RequestParam(name="id",required=true) String id) {
		RiderInterview riderInterview = riderInterviewService.getById(id);
		if(riderInterview==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderInterview);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderInterview
    */
    @RequiresPermissions("interview:rider_interview:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderInterview riderInterview) {
        return super.exportXls(request, riderInterview, RiderInterview.class, "面试管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("interview:rider_interview:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderInterview.class);
    }

}
