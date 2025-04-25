package org.jeecg.modules.rider.commission.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.commission.entity.RiderCommission;
import org.jeecg.modules.rider.commission.service.IRiderCommissionService;

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
 * @Description: 佣金管理
 * @Author: jeecg-boot
 * @Date:   2025-04-25
 * @Version: V1.0
 */
@Api(tags="佣金管理")
@RestController
@RequestMapping("/commission/riderCommission")
@Slf4j
public class RiderCommissionController extends JeecgController<RiderCommission, IRiderCommissionService> {
	@Autowired
	private IRiderCommissionService riderCommissionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderCommission
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "佣金管理-分页列表查询")
	@ApiOperation(value="佣金管理-分页列表查询", notes="佣金管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderCommission>> queryPageList(RiderCommission riderCommission,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderCommission> queryWrapper = QueryGenerator.initQueryWrapper(riderCommission, req.getParameterMap());
		Page<RiderCommission> page = new Page<RiderCommission>(pageNo, pageSize);
		IPage<RiderCommission> pageList = riderCommissionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param riderCommission
	 * @return
	 */
	@AutoLog(value = "佣金管理-添加")
	@ApiOperation(value="佣金管理-添加", notes="佣金管理-添加")
	@RequiresPermissions("commission:rider_commission:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderCommission riderCommission) {
		riderCommissionService.save(riderCommission);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderCommission
	 * @return
	 */
	@AutoLog(value = "佣金管理-编辑")
	@ApiOperation(value="佣金管理-编辑", notes="佣金管理-编辑")
	@RequiresPermissions("commission:rider_commission:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderCommission riderCommission) {
		riderCommissionService.updateById(riderCommission);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "佣金管理-通过id删除")
	@ApiOperation(value="佣金管理-通过id删除", notes="佣金管理-通过id删除")
	@RequiresPermissions("commission:rider_commission:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderCommissionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "佣金管理-批量删除")
	@ApiOperation(value="佣金管理-批量删除", notes="佣金管理-批量删除")
	@RequiresPermissions("commission:rider_commission:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderCommissionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	 /**
	  *  审核通过
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "佣金管理-审核通过")
	 @ApiOperation(value="佣金管理-审核通过", notes="佣金管理-审核通过")
	 @RequiresPermissions("commission:rider_commission:auditBatch")
	 @PostMapping(value = "/auditBatch")
	 public Result<String> auditBatch(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.riderCommissionService.auditBatch(ids);
		 return Result.OK("批量审核成功!");
	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "佣金管理-通过id查询")
	@ApiOperation(value="佣金管理-通过id查询", notes="佣金管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderCommission> queryById(@RequestParam(name="id",required=true) String id) {
		RiderCommission riderCommission = riderCommissionService.getById(id);
		if(riderCommission==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderCommission);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderCommission
    */
    @RequiresPermissions("commission:rider_commission:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderCommission riderCommission) {
        return super.exportXls(request, riderCommission, RiderCommission.class, "佣金管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("commission:rider_commission:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderCommission.class);
    }

}
