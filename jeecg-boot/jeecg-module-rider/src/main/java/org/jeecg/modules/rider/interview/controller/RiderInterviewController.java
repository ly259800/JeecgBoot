package org.jeecg.modules.rider.interview.controller;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
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
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.enums.InterviewEntranceEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.rider.site.entity.RiderSite;
import org.jeecg.modules.rider.site.service.IRiderSiteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

	 @Autowired
	 private IRiderSiteService riderSiteService;
	
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
	  * 面试管理-我的招聘
	  *
	  * @param riderInterview
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "面试管理-分页列表查询")
	 @ApiOperation(value="面试管理-我的招聘", notes="面试管理-我的招聘")
	 @GetMapping(value = "/listForSelf")
	 public Result<List<RiderInterviewDTO>> listForSelf(RiderInterview riderInterview,
														@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														HttpServletRequest req) {
		 // 直接获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
		 if (oConvertUtils.isEmpty(riderCustomer)) {
			 return Result.error("请注册用户！");
		 }
		 // 自定义查询规则
		 QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(RiderInterview::getReference,riderCustomer.getId());
		 if(Objects.nonNull(riderInterview.getPassStatus())){
			 queryWrapper.lambda().eq(RiderInterview::getPassStatus,riderInterview.getPassStatus());
			 //查询未结算的数据
			 queryWrapper.lambda().eq(RiderInterview::getSettleStatus,0);
		 }
		 if(Objects.nonNull(riderInterview.getSettleStatus())){
			 queryWrapper.lambda().eq(RiderInterview::getSettleStatus,riderInterview.getSettleStatus());
		 }
		 queryWrapper.lambda().orderByDesc(RiderInterview::getCreateTime);
		 List<RiderInterview> pageList = riderInterviewService.list(queryWrapper);
		 if(CollectionUtils.isEmpty(pageList)){
			 return Result.OK(new ArrayList<>());
		 }
		 List<String> siteIdList = pageList.stream().filter(s -> Objects.nonNull(s.getSiteId())).map(x -> x.getSiteId()).collect(Collectors.toList());
		 if(CollectionUtils.isEmpty(siteIdList)){
			 //若站点不存在，则佣金设置为null
			 List<RiderInterviewDTO> dtoList = pageList.stream().map(x -> {
				 RiderInterviewDTO interviewDTO = new RiderInterviewDTO();
				 BeanUtils.copyProperties(x, interviewDTO);
				 return interviewDTO;
			 }).collect(Collectors.toList());
			 return Result.OK(dtoList);
		 }
		 List<RiderSite> riderSiteList = riderSiteService.listByIds(siteIdList);
		 Map<String,  RiderSite> riderSiteMap = riderSiteList.stream().collect(Collectors.toMap(RiderSite::getId, Function.identity(), (a, b) -> b));
		 List<RiderInterviewDTO> dtoList = pageList.stream().map(x -> {
			 RiderInterviewDTO interviewDTO = new RiderInterviewDTO();
			 BeanUtils.copyProperties(x, interviewDTO);
			 if(Objects.nonNull(x.getSiteId()) && riderSiteMap.containsKey(x.getSiteId())){
				 RiderSite riderSite = riderSiteMap.get(x.getSiteId());
				 interviewDTO.setSiteCommission(riderSite.getCommission() - riderSite.getProfit());
			 }
			 return interviewDTO;
		 }).collect(Collectors.toList());
		 return Result.OK(dtoList);
	 }

	
	/**
	 *   骑手报名
	 */
	@AutoLog(value = "骑手报名")
	@ApiOperation(value="骑手报名", notes="骑手报名")
	@RequiresPermissions("interview:rider_interview:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderInterview riderInterview) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if(Objects.isNull(sysUser)){
			throw new JeecgBootException("请先登录");
		}
		RiderCustomer riderCustomer = riderCustomerService.getByPhone(sysUser.getPhone());
		if(riderCustomer == null){
			return Result.error("用户未注册");
		}

		RiderInterview one = riderInterviewService.getOne(new QueryWrapper<RiderInterview>().eq("phone", riderInterview.getPhone()).eq("entrance", 1));
		if(one != null){
			return Result.error("该手机号已报名");
		}
		//小程序入口，1-骑手
		riderInterview.setEntrance(InterviewEntranceEnum.RIDER.getCode());
		riderInterview.setSource("骑手报名");
		//若未传推广码
		if(StringUtils.isNotEmpty(riderInterview.getReference())){
			RiderCustomer byId = riderCustomerService.getById(riderInterview.getReference());
			if(Objects.nonNull(byId)){
				riderInterview.setReferencePhone(byId.getPhone());
			}
		} else {
			//则取当前用户的推广码
			riderInterview.setReference(riderCustomer.getReference());
			riderInterview.setReferencePhone(riderCustomer.getReferencePhone());
		}
		riderInterviewService.save(riderInterview);
		//若用户身份为游客，则更新为骑手
		if(Objects.nonNull(riderCustomer.getIdentity()) && Objects.equals(riderCustomer.getIdentity(),CustomerIdentityEnum.TOURIST.getCode())){
			riderCustomer.setIdentity(CustomerIdentityEnum.RIDER.getCode());
			riderCustomerService.updateById(riderCustomer);
		}
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
		 riderInterview.setEntrance(InterviewEntranceEnum.PARTNER.getCode());
		 riderInterview.setSource("站点申请");
		 riderInterview.setReference(riderCustomer.getId());
		 riderInterview.setReferencePhone(riderCustomer.getPhone());
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
		if(StringUtils.isNotEmpty(riderInterview.getMemo())){
			riderInterviewService.handle(riderInterview);
		}
		if(StringUtils.isNotEmpty(riderInterview.getSiteId())){
			riderInterviewService.updateSite(riderInterview);
		}
		return Result.OK("编辑成功!");
	}

	 /**
	  *  跟踪维护
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "面试管理-跟踪维护")
	 @ApiOperation(value="面试管理-跟踪维护", notes="面试管理-跟踪维护")
	 @RequiresPermissions("interview:rider_interview:handle")
	 @RequestMapping(value = "/handle", method = {RequestMethod.POST})
	 public Result<String> handle(@RequestBody RiderInterview riderInterview) {
		 riderInterviewService.handle(riderInterview);
		 return Result.OK("维护成功!");
	 }

	 /**
	  *  站点维护
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "面试管理-站点维护")
	 @ApiOperation(value="面试管理-站点维护", notes="面试管理-站点维护")
	 @RequiresPermissions("interview:rider_interview:site")
	 @RequestMapping(value = "/site", method = {RequestMethod.POST})
	 public Result<String> site(@RequestBody RiderInterview riderInterview) {
		 riderInterviewService.updateSite(riderInterview);
		 return Result.OK("维护成功!");
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
	  *  批量入职
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "面试管理-批量入职")
	 @ApiOperation(value="面试管理-批量入职", notes="面试管理-批量入职")
	 @RequiresPermissions("interview:rider_interview:passBatch")
	 @PostMapping(value = "/passBatch")
	 public Result<String> passBatch(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.riderInterviewService.passBatch(ids);
		 return Result.OK("批量入职成功!");
	 }

	 /**
	  *  批量结算
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "面试管理-批量结算")
	 @ApiOperation(value="面试管理-批量结算", notes="面试管理-批量结算")
	 @RequiresPermissions("interview:rider_interview:settleBatch")
	 @PostMapping(value = "/settleBatch")
	 public Result<String> settleBatch(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.riderInterviewService.settleBatch(ids);
		 return Result.OK("批量结算成功!");
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
