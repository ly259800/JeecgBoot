package org.jeecg.modules.order.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.order.service.IRiderUserOrderService;
import org.jeecg.modules.pay.enums.OrderStateEnum;
import org.jeecg.modules.order.entity.RiderUserOrder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.pay.dto.TenantOrderCannelDTO;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 用户个人订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Api(tags="用户个人订单")
@RestController
@RequestMapping("/order/riderUserOrder")
@Slf4j
public class RiderUserOrderController extends JeecgController<RiderUserOrder, IRiderUserOrderService> {
	@Autowired
	private IRiderUserOrderService riderUserOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param riderUserOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户个人订单-分页列表查询")
	@ApiOperation(value="用户个人订单-分页列表查询", notes="用户个人订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderUserOrder>> queryPageList(RiderUserOrder riderUserOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<RiderUserOrder> queryWrapper = QueryGenerator.initQueryWrapper(riderUserOrder, req.getParameterMap());
		Page<RiderUserOrder> page = new Page<RiderUserOrder>(pageNo, pageSize);
		IPage<RiderUserOrder> pageList = riderUserOrderService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param riderUserOrder
	 * @return
	 */
	@AutoLog(value = "用户个人订单-添加")
	@ApiOperation(value="用户个人订单-添加", notes="用户个人订单-添加")
	@RequiresPermissions("order:rider_user_order:add")
	@PostMapping(value = "/add")
	public org.jeecg.modules.pay.util.Result add(@RequestBody RiderUserOrder riderUserOrder) {
		return riderUserOrderService.saveUserOrder(riderUserOrder);
	}

	 @PostMapping("cannel")
	 @ApiOperation("取消支付")
	 @AutoLog("取消支付")
	 public Result cannelPay(@RequestBody @Valid TenantOrderCannelDTO cannelDTO){
		 RiderUserOrder sysTenantOrder = riderUserOrderService.getById(cannelDTO.getOrderId());
		 if(Objects.isNull(sysTenantOrder)){
			 throw new JeecgBootException("订单不存在或已删除!");
		 }
		 OrderStateEnum orderStateEnum = OrderStateEnum.getEnum(sysTenantOrder.getOrderState());
		 if(Objects.isNull(orderStateEnum)){
			 throw new JeecgBootException("订单状态未知!");
		 }
		 switch (orderStateEnum){
			 case NOTPAY:
			 case PAYERROR:
				 //获取当前用户
				 LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
				 sysTenantOrder.setOrderState(OrderStateEnum.CANNEL.getCode());
				 sysTenantOrder.setCanceler(user.getRealname());
				 sysTenantOrder.setCancelId(user.getId());
				 sysTenantOrder.setCancelTime(new Date());
				 break;
			 case SUCCESS:
				 throw new JeecgBootException("订单已支付!");
			 case CANNEL:
				 throw new JeecgBootException("订单已取消!");
			 default:
		 }
		 riderUserOrderService.updateById(sysTenantOrder);
		 return new Result();
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户个人订单-通过id删除")
	@ApiOperation(value="用户个人订单-通过id删除", notes="用户个人订单-通过id删除")
	@RequiresPermissions("order:rider_user_order:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderUserOrderService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户个人订单-批量删除")
	@ApiOperation(value="用户个人订单-批量删除", notes="用户个人订单-批量删除")
	@RequiresPermissions("order:rider_user_order:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.riderUserOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户个人订单-通过id查询")
	@ApiOperation(value="用户个人订单-通过id查询", notes="用户个人订单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderUserOrder> queryById(@RequestParam(name="id",required=true) String id) {
		RiderUserOrder riderUserOrder = riderUserOrderService.getById(id);
		if(riderUserOrder==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(riderUserOrder);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param riderUserOrder
    */
    @RequiresPermissions("order:rider_user_order:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderUserOrder riderUserOrder) {
        return super.exportXls(request, riderUserOrder, RiderUserOrder.class, "用户个人订单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("order:rider_user_order:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderUserOrder.class);
    }

}
