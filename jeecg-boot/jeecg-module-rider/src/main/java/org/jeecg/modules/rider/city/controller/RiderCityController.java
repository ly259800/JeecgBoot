package org.jeecg.modules.rider.city.controller;

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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.city.entity.RiderCity;
import org.jeecg.modules.rider.city.service.IRiderCityService;

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
 * @Description: 城市管理
 * @Author: jeecg-boot
 * @Date:   2025-03-31
 * @Version: V1.0
 */
@Api(tags="城市管理")
@RestController
@RequestMapping("/city/riderCity")
@Slf4j
public class RiderCityController extends JeecgController<RiderCity, IRiderCityService> {
	@Autowired
	private IRiderCityService riderCityService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderCity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "城市管理-分页列表查询")
	@ApiOperation(value="城市管理-分页列表查询", notes="城市管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderCity>> queryPageList(RiderCity riderCity,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderCity> queryWrapper = QueryGenerator.initQueryWrapper(riderCity, req.getParameterMap());
		Page<RiderCity> page = new Page<RiderCity>(pageNo, pageSize);
		IPage<RiderCity> pageList = riderCityService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param riderCity
	 * @return
	 */
	@AutoLog(value = "城市管理-添加")
	@ApiOperation(value="城市管理-添加", notes="城市管理-添加")
	@RequiresPermissions("city:rider_city:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderCity riderCity) {
		riderCityService.save(riderCity);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderCity
	 * @return
	 */
	@AutoLog(value = "城市管理-编辑")
	@ApiOperation(value="城市管理-编辑", notes="城市管理-编辑")
	@RequiresPermissions("city:rider_city:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderCity riderCity) {
		riderCityService.updateById(riderCity);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "城市管理-通过id删除")
	@ApiOperation(value="城市管理-通过id删除", notes="城市管理-通过id删除")
	@RequiresPermissions("city:rider_city:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderCityService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "城市管理-批量删除")
	@ApiOperation(value="城市管理-批量删除", notes="城市管理-批量删除")
	@RequiresPermissions("city:rider_city:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderCityService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "城市管理-通过id查询")
	@ApiOperation(value="城市管理-通过id查询", notes="城市管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderCity> queryById(@RequestParam(name="id",required=true) String id) {
		RiderCity riderCity = riderCityService.getById(id);
		if(riderCity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderCity);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderCity
    */
    @RequiresPermissions("city:rider_city:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderCity riderCity) {
        return super.exportXls(request, riderCity, RiderCity.class, "城市管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("city:rider_city:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderCity.class);
    }

}
