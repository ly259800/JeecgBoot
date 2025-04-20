package org.jeecg.modules.params.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.params.entity.RiderParams;
import org.jeecg.modules.params.service.IRiderParamsService;

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
 * @Description: 参数配置
 * @Author: jeecg-boot
 * @Date:   2025-04-04
 * @Version: V1.0
 */
@Api(tags="参数配置")
@RestController
@RequestMapping("/params/riderParams")
@Slf4j
public class RiderParamsController extends JeecgController<RiderParams, IRiderParamsService> {
	@Autowired
	private IRiderParamsService riderParamsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderParams
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "参数配置-分页列表查询")
	@ApiOperation(value="参数配置-分页列表查询", notes="参数配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderParams>> queryPageList(RiderParams riderParams,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderParams> queryWrapper = QueryGenerator.initQueryWrapper(riderParams, req.getParameterMap());
		Page<RiderParams> page = new Page<RiderParams>(pageNo, pageSize);
		IPage<RiderParams> pageList = riderParamsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param riderParams
	 * @return
	 */
	@AutoLog(value = "参数配置-添加")
	@ApiOperation(value="参数配置-添加", notes="参数配置-添加")
	@RequiresPermissions("params:rider_params:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderParams riderParams) {
		riderParamsService.save(riderParams);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderParams
	 * @return
	 */
	@AutoLog(value = "参数配置-编辑")
	@ApiOperation(value="参数配置-编辑", notes="参数配置-编辑")
	@RequiresPermissions("params:rider_params:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderParams riderParams) {
		riderParamsService.updateById(riderParams);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "参数配置-通过id删除")
	@ApiOperation(value="参数配置-通过id删除", notes="参数配置-通过id删除")
	@RequiresPermissions("params:rider_params:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderParamsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "参数配置-批量删除")
	@ApiOperation(value="参数配置-批量删除", notes="参数配置-批量删除")
	@RequiresPermissions("params:rider_params:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderParamsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "参数配置-通过id查询")
	@ApiOperation(value="参数配置-通过id查询", notes="参数配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderParams> queryById(@RequestParam(name="id",required=true) String id) {
		RiderParams riderParams = riderParamsService.getById(id);
		if(riderParams==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderParams);
	}

	 /**
	  * 通过编码查询
	  * @param code
	  * @return
	  */
	 @ApiOperation(value="参数配置-通过编码查询", notes="参数配置-通过编码查询")
	 @GetMapping(value = "/queryByCode")
	 public Result<RiderParams> queryByCode(@RequestParam(name="code",required=true) String code) {
		 RiderParams riderParams = riderParamsService.getByCode(code);
		 if(riderParams==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(riderParams);
	 }


    /**
    * 导出excel
    *
    * @param request
    * @param riderParams
    */
    @RequiresPermissions("params:rider_params:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderParams riderParams) {
        return super.exportXls(request, riderParams, RiderParams.class, "参数配置");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("params:rider_params:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderParams.class);
    }

}
