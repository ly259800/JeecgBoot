package org.jeecg.modules.rider.qrcode.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.qrcode.entity.RiderQrcode;
import org.jeecg.modules.rider.qrcode.service.IRiderQrcodeService;

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
 * @Description: 小程序二维码
 * @Author: jeecg-boot
 * @Date:   2025-04-06
 * @Version: V1.0
 */
@Api(tags="小程序二维码")
@RestController
@RequestMapping("/qrcode/riderQrcode")
@Slf4j
public class RiderQrcodeController extends JeecgController<RiderQrcode, IRiderQrcodeService> {
	@Autowired
	private IRiderQrcodeService riderQrcodeService;

	 @Autowired
	 private IRiderCustomerService riderCustomerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderQrcode
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "小程序二维码-分页列表查询")
	@ApiOperation(value="小程序二维码-分页列表查询", notes="小程序二维码-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderQrcode>> queryPageList(RiderQrcode riderQrcode,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderQrcode> queryWrapper = QueryGenerator.initQueryWrapper(riderQrcode, req.getParameterMap());
		Page<RiderQrcode> page = new Page<RiderQrcode>(pageNo, pageSize);
		IPage<RiderQrcode> pageList = riderQrcodeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 * @param riderQrcode
	 * @return
	 */
	@AutoLog(value = "小程序二维码-添加")
	@ApiOperation(value="小程序二维码-添加", notes="小程序二维码-添加")
	@RequiresPermissions("qrcode:rider_qrcode:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderQrcode riderQrcode) {
		riderQrcodeService.saveRiderQrcode(riderQrcode);
		return Result.OK("添加成功！");
	}

	 @AutoLog(value = "小程序二维码-绑定用户")
	 @ApiOperation(value="小程序二维码-绑定用户", notes="小程序二维码-绑定用户")
	 @RequiresPermissions("qrcode:rider_qrcode:bind")
	 @PostMapping(value = "/bind")
	 public Result<String> bind(@RequestBody RiderQrcode riderQrcode) {
		if(StringUtils.isEmpty(riderQrcode.getCustomerId())){
			throw new JeecgBootException("请选择绑定用户!");
		}
		 RiderCustomer riderCustomer = riderCustomerService.getById(riderQrcode.getCustomerId());
		 if(Objects.isNull(riderCustomer)){
			 throw new JeecgBootException("用户不存在!");
		 }
		 riderQrcode.setPhone(riderCustomer.getPhone());
		 riderQrcodeService.updateById(riderQrcode);
		 return Result.OK("添加成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param riderQrcode
	 * @return
	 */
	@AutoLog(value = "小程序二维码-编辑")
	@ApiOperation(value="小程序二维码-编辑", notes="小程序二维码-编辑")
	@RequiresPermissions("qrcode:rider_qrcode:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderQrcode riderQrcode) {
		riderQrcodeService.updateById(riderQrcode);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "小程序二维码-通过id删除")
	@ApiOperation(value="小程序二维码-通过id删除", notes="小程序二维码-通过id删除")
	@RequiresPermissions("qrcode:rider_qrcode:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderQrcodeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "小程序二维码-批量删除")
	@ApiOperation(value="小程序二维码-批量删除", notes="小程序二维码-批量删除")
	@RequiresPermissions("qrcode:rider_qrcode:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderQrcodeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "小程序二维码-通过id查询")
	@ApiOperation(value="小程序二维码-通过id查询", notes="小程序二维码-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderQrcode> queryById(@RequestParam(name="id",required=true) String id) {
		RiderQrcode riderQrcode = riderQrcodeService.getById(id);
		if(riderQrcode==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderQrcode);
	}

	 /**
	  * 通过scene查询
	  * @param scene
	  * @return
	  */
	 //@AutoLog(value = "小程序二维码-通过id查询")
	 @ApiOperation(value="小程序二维码-通过scene查询", notes="小程序二维码-通过scene查询")
	 @GetMapping(value = "/queryByScene")
	 public Result<RiderQrcode> queryByScene(@RequestParam(name="scene",required=true) String scene) {
		 RiderQrcode riderQrcode = riderQrcodeService.getByScene(scene);
		 if(riderQrcode==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(riderQrcode);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param riderQrcode
    */
    @RequiresPermissions("qrcode:rider_qrcode:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderQrcode riderQrcode) {
        return super.exportXls(request, riderQrcode, RiderQrcode.class, "小程序二维码");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("qrcode:rider_qrcode:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderQrcode.class);
    }

}
