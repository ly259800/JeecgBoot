package org.jeecg.modules.rider.tourism.controller;

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
import org.jeecg.modules.rider.post.entity.PostDetail;
import org.jeecg.modules.rider.tourism.entity.FamilyTourismDetail;
import org.jeecg.modules.rider.tourism.service.IFamilyTourismDetailService;

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
 * @Description: 旅游信息详情表
 * @Author: jeecg-boot
 * @Date:   2025-11-23
 * @Version: V1.0
 */
@Api(tags="旅游信息详情表")
@RestController
@RequestMapping("/tourism/familyTourismDetail")
@Slf4j
public class FamilyTourismDetailController extends JeecgController<FamilyTourismDetail, IFamilyTourismDetailService> {
	@Autowired
	private IFamilyTourismDetailService familyTourismDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param familyTourismDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "旅游信息详情表-分页列表查询")
	@ApiOperation(value="旅游信息详情表-分页列表查询", notes="旅游信息详情表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FamilyTourismDetail>> queryPageList(FamilyTourismDetail familyTourismDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<FamilyTourismDetail> queryWrapper = QueryGenerator.initQueryWrapper(familyTourismDetail, req.getParameterMap());
		Page<FamilyTourismDetail> page = new Page<FamilyTourismDetail>(pageNo, pageSize);
		IPage<FamilyTourismDetail> pageList = familyTourismDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param familyTourismDetail
	 * @return
	 */
	@AutoLog(value = "旅游信息详情表-添加")
	@ApiOperation(value="旅游信息详情表-添加", notes="旅游信息详情表-添加")
	@RequiresPermissions("tourism:family_tourism_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FamilyTourismDetail familyTourismDetail) {
		familyTourismDetailService.save(familyTourismDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param familyTourismDetail
	 * @return
	 */
	@AutoLog(value = "旅游信息详情表-编辑")
	@ApiOperation(value="旅游信息详情表-编辑", notes="旅游信息详情表-编辑")
	@RequiresPermissions("tourism:family_tourism:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FamilyTourismDetail familyTourismDetail) {
		FamilyTourismDetail oldDetail = familyTourismDetailService.getByTourismId(familyTourismDetail.getTourismId());
		if(oldDetail != null){
			oldDetail.setImage(familyTourismDetail.getImage());
			oldDetail.setVideo(familyTourismDetail.getVideo());
			oldDetail.setRoutePic(familyTourismDetail.getRoutePic());
			oldDetail.setMemo(familyTourismDetail.getMemo());
			familyTourismDetailService.updateById(oldDetail);
		} else {
			familyTourismDetailService.save(familyTourismDetail);
		}
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "旅游信息详情表-通过id删除")
	@ApiOperation(value="旅游信息详情表-通过id删除", notes="旅游信息详情表-通过id删除")
	@RequiresPermissions("tourism:family_tourism_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		familyTourismDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "旅游信息详情表-批量删除")
	@ApiOperation(value="旅游信息详情表-批量删除", notes="旅游信息详情表-批量删除")
	@RequiresPermissions("tourism:family_tourism_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.familyTourismDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "旅游信息详情表-通过id查询")
	@ApiOperation(value="旅游信息详情表-通过id查询", notes="旅游信息详情表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FamilyTourismDetail> queryById(@RequestParam(name="id",required=true) String id) {
		FamilyTourismDetail familyTourismDetail = familyTourismDetailService.getById(id);
		if(familyTourismDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(familyTourismDetail);
	}

	 /**
	  * 通过TourismId查询
	  *
	  * @param tourismId
	  * @return
	  */
	 //@AutoLog(value = "旅游信息详情表-通过id查询")
	 @ApiOperation(value="旅游信息详情表-通过tourismId查询", notes="旅游信息详情表-通过tourismId查询")
	 @GetMapping(value = "/queryByTourismId")
	 public Result<FamilyTourismDetail> queryByTourismId(@RequestParam(name="tourismId",required=true) String tourismId) {
		 FamilyTourismDetail familyTourismDetail = familyTourismDetailService.getByTourismId(tourismId);
		 return Result.OK(familyTourismDetail);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param familyTourismDetail
    */
    @RequiresPermissions("tourism:family_tourism_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FamilyTourismDetail familyTourismDetail) {
        return super.exportXls(request, familyTourismDetail, FamilyTourismDetail.class, "旅游信息详情表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("tourism:family_tourism_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FamilyTourismDetail.class);
    }

}
