package org.jeecg.modules.rider.interview.controller;

import java.math.BigDecimal;
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
import org.jeecg.common.util.IdCardUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.interview.dto.InterviewOrderDTO;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.params.service.IRiderParamsService;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.service.IPostService;
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
	 private IPostService postService;

	 @Autowired
	 private IRiderParamsService riderParamsService;
	
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
		 List<Post> riderSiteList = postService.listByIds(siteIdList);
		 Map<String,  Post> riderSiteMap = riderSiteList.stream().collect(Collectors.toMap(Post::getId, Function.identity(), (a, b) -> b));
		 List<RiderInterviewDTO> dtoList = pageList.stream().map(x -> {
			 RiderInterviewDTO interviewDTO = new RiderInterviewDTO();
			 BeanUtils.copyProperties(x, interviewDTO);
			 if(Objects.nonNull(x.getSiteId()) && riderSiteMap.containsKey(x.getSiteId())){
				 Post riderSite = riderSiteMap.get(x.getSiteId());
				 //佣金为价格的一半
				 interviewDTO.setSiteCommission(riderSite.getCommission().intValue());
			 }
			 return interviewDTO;
		 }).collect(Collectors.toList());
		 return Result.OK(dtoList);
	 }

	 /**
	  * 面试管理-我的报名
	  *
	  * @param riderInterview
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="面试管理-我的报名", notes="面试管理-我的报名")
	 @GetMapping(value = "/listForCustomer")
	 public Result<List<RiderInterviewDTO>> listForCustomer(RiderInterview riderInterview,
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
		 queryWrapper.lambda().eq(RiderInterview::getPhone,riderCustomer.getPhone());
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
		 List<Post> riderSiteList = postService.listByIds(siteIdList);
		 Map<String,  Post> riderSiteMap = riderSiteList.stream().collect(Collectors.toMap(Post::getId, Function.identity(), (a, b) -> b));
		 List<RiderInterviewDTO> dtoList = pageList.stream().map(x -> {
			 RiderInterviewDTO interviewDTO = new RiderInterviewDTO();
			 BeanUtils.copyProperties(x, interviewDTO);
			 if(Objects.nonNull(x.getSiteId()) && riderSiteMap.containsKey(x.getSiteId())){
				 Post riderSite = riderSiteMap.get(x.getSiteId());
				 interviewDTO.setSalaryRange(riderSite.getSalaryRange());
				 interviewDTO.setPayType(riderSite.getPayType());
				 interviewDTO.setPrice(riderSite.getPrice());
				 //若岗位免费，则价格为0
				 if(!Objects.equals(riderSite.getPayType(), 1)){
					 interviewDTO.setPrice(BigDecimal.ZERO);
				 }
			 }
			 return interviewDTO;
		 }).collect(Collectors.toList());
		 return Result.OK(dtoList);
	 }


	 /**
	  *   岗位报名申请
	  */
	 @AutoLog(value = "岗位报名申请")
	 @ApiOperation(value="岗位报名申请", notes="岗位报名申请")
	 @RequiresPermissions("interview:rider_interview:add")
	 @PostMapping(value = "/postAdd")
	 public Result<String> postAdd(@RequestBody RiderInterview riderInterview) {
		 if(StringUtils.isEmpty(riderInterview.getSiteId())){
			 return Result.error("岗位不能为空");
		 }
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if(Objects.isNull(sysUser)){
			 throw new JeecgBootException("请先登录");
		 }
		 RiderCustomer riderCustomer = riderCustomerService.getByPhone(sysUser.getPhone());
		 if(riderCustomer == null){
			 return Result.error("用户未注册");
		 }

		 Post post = postService.getById(riderInterview.getSiteId());
		 if(post == null){
			 return Result.error("岗位不存在");
		 }
		 if(StringUtils.isEmpty(riderCustomer.getIdCard())){
			 Result result = new Result();
			 result.setCode(10080);
			 result.setMessage("用户未实名,请先实名认证!");
			 return result;
		 }
		 RiderInterview one = riderInterviewService.getOne(new QueryWrapper<RiderInterview>().eq("phone", riderCustomer.getPhone()).eq("site_id", riderInterview.getSiteId()));
		 if(one != null) {
			 return Result.error("您已经报名过该岗位，不能重复报名！");
		 }
		 riderInterview.setName(riderCustomer.getName());
		 riderInterview.setPhone(riderCustomer.getPhone());
		 riderInterview.setSex(IdCardUtils.getGenderFromIdCard(riderCustomer.getIdCard()));
		 riderInterview.setAge(IdCardUtils.getAgeFromIdCard(riderCustomer.getIdCard()));
		 riderInterview.setSource("报名申请");
		 riderInterview.setReference(riderCustomer.getReference());
		 riderInterview.setReferencePhone(riderCustomer.getReferencePhone());
		 riderInterviewService.save(riderInterview);
		 //若用户身份为会员，则更新为娘家人
		 if(Objects.nonNull(riderCustomer.getIdentity()) && Objects.equals(riderCustomer.getIdentity(), CustomerIdentityEnum.TOURIST.getCode())){
			 riderCustomer.setIdentity(CustomerIdentityEnum.RIDER.getCode());
			 riderCustomerService.updateById(riderCustomer);
		 }
		 Result<String> ok = Result.OK("报名成功！");
		 ok.setResult(riderInterview.getId());
		 return ok;
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
	  *  岗位培训单
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "岗位培训单")
	 @ApiOperation(value="岗位培训单", notes="岗位培训单")
	 @RequestMapping(value = "/getTrainOrder", method = {RequestMethod.GET})
	 public Result<InterviewOrderDTO> training(RiderInterview riderInterview) {
		 if(Objects.isNull(riderInterview.getId())){
			 return Result.error("请选择报名记录!");
		 }
		 RiderParams train_order_template = riderParamsService.getByCode("train_order_template");
		 InterviewOrderDTO dto = new InterviewOrderDTO();
		 dto.setContent(train_order_template.getParamValue());
		 return Result.ok(dto);
	 }

	 /**
	  *  岗位培训单
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "岗位培训确认")
	 @ApiOperation(value="岗位培训确认", notes="岗位培训确认")
	 @RequestMapping(value = "/sumbitTrainOrder", method = {RequestMethod.POST})
	 public Result sumbitTrainOrder(@RequestBody RiderInterview riderInterview) {
		 if(Objects.isNull(riderInterview.getId())){
			 return Result.error("请选择报名记录!");
		 }
		 RiderInterview update = new RiderInterview();
		 update.setId(riderInterview.getId());
		 update.setTrainingStatus(1);
		 riderInterviewService.updateById(update);
		 return Result.ok();
	 }



	 /**
	  *  岗位确认单
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "岗位确认单")
	 @ApiOperation(value="岗位确认单", notes="岗位确认单")
	 @RequestMapping(value = "/getConfirmOrder", method = {RequestMethod.GET})
	 public Result<InterviewOrderDTO> getConfirmOrder(RiderInterview riderInterview) {
		 if(Objects.isNull(riderInterview.getId())){
			 return Result.error("请选择报名记录!");
		 }
		 RiderParams confirm_order_template = riderParamsService.getByCode("confirm_order_template");
		 InterviewOrderDTO dto = new InterviewOrderDTO();
		 dto.setContent(confirm_order_template.getParamValue());
		 return Result.ok(dto);
	 }

	 /**
	  *  岗位确认单
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "岗位确认单确认")
	 @ApiOperation(value="岗位确认单确认", notes="岗位确认单确认")
	 @RequestMapping(value = "/sumbitConfirmOrder", method = {RequestMethod.POST})
	 public Result sumbitConfirmOrder(@RequestBody RiderInterview riderInterview) {
		 if(Objects.isNull(riderInterview.getId())){
			 return Result.error("请选择报名记录!");
		 }
		 RiderInterview update = new RiderInterview();
		 update.setId(riderInterview.getId());
		 update.setConfirmStatus(1);
		 riderInterviewService.updateById(update);
		 return Result.ok();
	 }

}
