package org.jeecg.modules.order.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.order.service.IRiderPayOrderService;
import org.jeecg.modules.order.entity.RiderPayOrder;

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
 * @Description: 支付订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Api(tags="支付订单")
@RestController
@RequestMapping("/order/riderPayOrder")
@Slf4j
public class RiderPayOrderController extends JeecgController<RiderPayOrder, IRiderPayOrderService> {
	@Autowired
	private IRiderPayOrderService riderPayOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderPayOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "支付订单-分页列表查询")
	@ApiOperation(value="支付订单-分页列表查询", notes="支付订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderPayOrder>> queryPageList(RiderPayOrder riderPayOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderPayOrder> queryWrapper = QueryGenerator.initQueryWrapper(riderPayOrder, req.getParameterMap());
		Page<RiderPayOrder> page = new Page<RiderPayOrder>(pageNo, pageSize);
		IPage<RiderPayOrder> pageList = riderPayOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param riderPayOrder
	 * @return
	 */
	@AutoLog(value = "支付订单-添加")
	@ApiOperation(value="支付订单-添加", notes="支付订单-添加")
	@RequiresPermissions("order:rider_pay_order:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderPayOrder riderPayOrder) {
		riderPayOrderService.save(riderPayOrder);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param riderPayOrder
	 * @return
	 */
	@AutoLog(value = "支付订单-编辑")
	@ApiOperation(value="支付订单-编辑", notes="支付订单-编辑")
	@RequiresPermissions("order:rider_pay_order:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderPayOrder riderPayOrder) {
		riderPayOrderService.updateById(riderPayOrder);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "支付订单-通过id删除")
	@ApiOperation(value="支付订单-通过id删除", notes="支付订单-通过id删除")
	@RequiresPermissions("order:rider_pay_order:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderPayOrderService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "支付订单-批量删除")
	@ApiOperation(value="支付订单-批量删除", notes="支付订单-批量删除")
	@RequiresPermissions("order:rider_pay_order:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderPayOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "支付订单-通过id查询")
	@ApiOperation(value="支付订单-通过id查询", notes="支付订单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderPayOrder> queryById(@RequestParam(name="id",required=true) String id) {
		RiderPayOrder riderPayOrder = riderPayOrderService.getById(id);
		if(riderPayOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderPayOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderPayOrder
    */
    @RequiresPermissions("order:rider_pay_order:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderPayOrder riderPayOrder) {
        return super.exportXls(request, riderPayOrder, RiderPayOrder.class, "支付订单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("order:rider_pay_order:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderPayOrder.class);
    }

}
