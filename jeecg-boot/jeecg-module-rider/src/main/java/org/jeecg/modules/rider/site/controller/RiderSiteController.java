package org.jeecg.modules.rider.site.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.site.dto.RiderSiteDTO;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.service.IRiderSiteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 站点管理
 * @Author: jeecg-boot
 * @Date:   2025-03-23
 * @Version: V1.0
 */
@Api(tags="站点管理")
@RestController
@RequestMapping("/site/riderSite")
@Slf4j
public class RiderSiteController extends JeecgController<RiderSite, IRiderSiteService> {
	@Autowired
	private IRiderSiteService riderSiteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderSite
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "站点管理-分页列表查询")
	@ApiOperation(value="站点管理-分页列表查询", notes="站点管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderSiteDTO>> queryPageList(RiderSite riderSite,
													 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													 HttpServletRequest req) {
        QueryWrapper<RiderSite> queryWrapper = QueryGenerator.initQueryWrapper(riderSite, req.getParameterMap());
		Page<RiderSite> page = new Page<RiderSite>(pageNo, pageSize);
		IPage<RiderSite> pageList = riderSiteService.page(page, queryWrapper);
		IPage<RiderSiteDTO> siteDTOIPage = pageList.convert(x -> {
			RiderSiteDTO siteDTO = new RiderSiteDTO();
			BeanUtils.copyProperties(x, siteDTO);
			//设置站长佣金
			siteDTO.setSiteCommission(x.getCommission() - x.getProfit());
			return siteDTO;
		});
		return Result.OK(siteDTOIPage);
	}

	 /**
	  * 查询站点信息列表
	  * @return
	  */
	 @RequestMapping(value = "/queryList", method = RequestMethod.GET)
	 public Result<List<RiderSite>> queryList(@RequestParam(name="ids",required=false) String ids) {
		 Result<List<RiderSite>> result = new Result<List<RiderSite>>();
		 LambdaQueryWrapper<RiderSite> query = new LambdaQueryWrapper<>();
		 if(oConvertUtils.isNotEmpty(ids)){
			 query.in(RiderSite::getId, ids.split(","));
		 }
		 //此处查询忽略时间条件
		 List<RiderSite> ls = riderSiteService.list(query);
		 result.setSuccess(true);
		 result.setResult(ls);
		 return result;
	 }
	
	/**
	 *   添加
	 *
	 * @param riderSite
	 * @return
	 */
	@AutoLog(value = "站点管理-添加")
	@ApiOperation(value="站点管理-添加", notes="站点管理-添加")
	@RequiresPermissions("site:rider_site:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderSite riderSite) {
		riderSiteService.save(riderSite);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderSite
	 * @return
	 */
	@AutoLog(value = "站点管理-编辑")
	@ApiOperation(value="站点管理-编辑", notes="站点管理-编辑")
	@RequiresPermissions("site:rider_site:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderSite riderSite) {
		riderSiteService.updateById(riderSite);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "站点管理-通过id删除")
	@ApiOperation(value="站点管理-通过id删除", notes="站点管理-通过id删除")
	@RequiresPermissions("site:rider_site:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderSiteService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "站点管理-批量删除")
	@ApiOperation(value="站点管理-批量删除", notes="站点管理-批量删除")
	@RequiresPermissions("site:rider_site:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderSiteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "站点管理-通过id查询")
	@ApiOperation(value="站点管理-通过id查询", notes="站点管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderSite> queryById(@RequestParam(name="id",required=true) String id) {
		RiderSite riderSite = riderSiteService.getById(id);
		if(riderSite==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderSite);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderSite
    */
    @RequiresPermissions("site:rider_site:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderSite riderSite) {
        return super.exportXls(request, riderSite, RiderSite.class, "站点管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("site:rider_site:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderSite.class);
    }

}
