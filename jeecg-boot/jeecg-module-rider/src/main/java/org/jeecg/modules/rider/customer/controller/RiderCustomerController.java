package org.jeecg.modules.rider.customer.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Api(tags="客户管理")
@RestController
@RequestMapping("/customer/riderCustomer")
@Slf4j
public class RiderCustomerController extends JeecgController<RiderCustomer, IRiderCustomerService> {
	@Autowired
	private IRiderCustomerService riderCustomerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "客户管理-分页列表查询")
	@ApiOperation(value="客户管理-分页列表查询", notes="客户管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderCustomer>> queryPageList(RiderCustomer riderCustomer,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("identity", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<RiderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(riderCustomer, req.getParameterMap(),customeRuleMap);
		Page<RiderCustomer> page = new Page<RiderCustomer>(pageNo, pageSize);
		IPage<RiderCustomer> pageList = riderCustomerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 查询客户信息列表
	  * @return
	  */
	 @RequestMapping(value = "/queryList", method = RequestMethod.GET)
	 public Result<List<RiderCustomer>> queryList(@RequestParam(name="ids",required=false) String ids) {
		 Result<List<RiderCustomer>> result = new Result<List<RiderCustomer>>();
		 LambdaQueryWrapper<RiderCustomer> query = new LambdaQueryWrapper<>();
		 if(oConvertUtils.isNotEmpty(ids)){
			 query.in(RiderCustomer::getId, ids.split(","));
		 }
		 //此处查询忽略时间条件
		 List<RiderCustomer> ls = riderCustomerService.list(query);
		 result.setSuccess(true);
		 result.setResult(ls);
		 return result;
	 }
	
	/**
	 *   添加
	 *
	 * @param riderCustomer
	 * @return
	 */
	@AutoLog(value = "客户管理-添加")
	@ApiOperation(value="客户管理-添加", notes="客户管理-添加")
	@RequiresPermissions("customer:rider_customer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderCustomer riderCustomer) {
		riderCustomerService.save(riderCustomer);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderCustomer
	 * @return
	 */
	@AutoLog(value = "客户管理-编辑")
	@ApiOperation(value="客户管理-编辑", notes="客户管理-编辑")
	@RequiresPermissions("customer:rider_customer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderCustomer riderCustomer) {
		riderCustomerService.updateById(riderCustomer);
		return Result.OK("编辑成功!");
	}

	 /**
	  *  更新站点城市
	  *
	  * @param riderCustomer
	  * @return
	  */
	 @AutoLog(value = "客户管理-更新站点城市")
	 @ApiOperation(value="客户管理-更新站点城市", notes="客户管理-更新站点城市")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/updateCity", method = {RequestMethod.POST})
	 public Result<String> updateCity(@RequestBody RiderCustomer riderCustomer) {
		 if(StringUtils.isEmpty(riderCustomer.getId())){
			 //	获取当前用户
			 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			 if (oConvertUtils.isEmpty(loginUser)) {
				 return Result.error("请登录系统！");
			 }
			 RiderCustomer r = riderCustomerService.getByPhone(loginUser.getPhone());
			 if (oConvertUtils.isEmpty(r)) {
				 return Result.error("请注册用户！");
			 }
			 riderCustomer.setId(r.getId());
		 }
		 if(StringUtils.isEmpty(riderCustomer.getSiteCity())){
			 throw new JeecgBootException("站点城市不能为空");
		 }
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("更新成功!");
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户管理-通过id删除")
	@ApiOperation(value="客户管理-通过id删除", notes="客户管理-通过id删除")
	@RequiresPermissions("customer:rider_customer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderCustomerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户管理-批量删除")
	@ApiOperation(value="客户管理-批量删除", notes="客户管理-批量删除")
	@RequiresPermissions("customer:rider_customer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderCustomerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	 /**
	  *  升级为合伙人
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "客户管理-升级为合伙人")
	 @ApiOperation(value="客户管理-升级为合伙人", notes="客户管理-升级为合伙人")
	 @RequiresPermissions("customer:rider_customer:upgradePartner")
	 @PostMapping(value = "/upgradePartner")
	 public Result<String> upgradePartner(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.riderCustomerService.upgradePartner(ids);
		 return Result.OK("批量更新成功!");
	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "客户管理-通过id查询")
	@ApiOperation(value="客户管理-通过id查询", notes="客户管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderCustomer> queryById(@RequestParam(name="id",required=true) String id) {
		RiderCustomer riderCustomer = riderCustomerService.getById(id);
		if(riderCustomer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderCustomer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderCustomer
    */
    @RequiresPermissions("customer:rider_customer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderCustomer riderCustomer) {
        return super.exportXls(request, riderCustomer, RiderCustomer.class, "客户管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("customer:rider_customer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderCustomer.class);
    }

}
