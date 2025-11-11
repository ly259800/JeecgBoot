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
import org.jeecg.common.util.DateUtils;
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
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import org.jeecg.modules.rider.talentpool.service.IFamilyTalentPoolService;
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

	 @Autowired
	 private IFamilyTalentPoolService familyTalentPoolService;
	
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
	public Result<IPage<RiderInterviewDTO>> queryPageList(RiderInterview riderInterview,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
		Page<RiderInterview> page = new Page<RiderInterview>(pageNo, pageSize);
		if(StringUtils.isNotBlank(riderInterview.getName())){
			queryWrapper.like("ri.name", riderInterview.getName());
		}
		if(StringUtils.isNotBlank(riderInterview.getPhone())){
			queryWrapper.like("ri.phone", riderInterview.getPhone());
		}
		if(StringUtils.isNotBlank(riderInterview.getReferencePhone())){
			queryWrapper.like("ri.reference_phone", riderInterview.getReferencePhone());
		}
		if(Objects.nonNull(riderInterview.getPassStatus())){
			queryWrapper.eq("ri.pass_status", riderInterview.getPassStatus());
		}
		if(Objects.nonNull(riderInterview.getSettleStatus())){
			queryWrapper.eq("ri.settle_status", riderInterview.getSettleStatus());
		}
		if(Objects.nonNull(riderInterview.getStatus())){
			queryWrapper.eq("ri.status", riderInterview.getStatus());
		}
		queryWrapper.orderByDesc("ri.id");
		IPage<RiderInterviewDTO> pageList = riderInterviewService.pageList(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 安置信息查询
	  *
	  * @param riderInterview
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="面试管理-安置信息查询", notes="面试管理-安置信息查询")
	 @GetMapping(value = "/signList")
	 public Result<IPage<RiderInterviewDTO>> querySignList(RiderInterview riderInterview,
														@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														HttpServletRequest req) {
		 // 自定义查询规则
		 QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
		 Page<RiderInterview> page = new Page<RiderInterview>(pageNo, pageSize);
		 if(StringUtils.isNotBlank(riderInterview.getName())){
			 queryWrapper.like("ri.name", riderInterview.getName());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getPhone())){
			 queryWrapper.like("ri.phone", riderInterview.getPhone());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getReferencePhone())){
			 queryWrapper.like("ri.reference_phone", riderInterview.getReferencePhone());
		 }
		 if(Objects.nonNull(riderInterview.getPassStatus())){
			 queryWrapper.eq("ri.pass_status", riderInterview.getPassStatus());
		 }
		 if(Objects.nonNull(riderInterview.getSettleStatus())){
			 queryWrapper.eq("ri.settle_status", riderInterview.getSettleStatus());
		 }
		 if(Objects.nonNull(riderInterview.getStatus())){
			 queryWrapper.eq("ri.status", riderInterview.getStatus());
		 }
		 queryWrapper.and(wrapper1 -> wrapper1
				 .apply(" (fp.pay_type = 0 and fp.market_status = 0)")
				 .or()
				 .apply(" (fp.pay_type = 0 and fp.market_status = 1 and ri.market_complete_status = 1)")
				 .or()
				 .apply(" (fp.pay_type = 1 and fp.training_status = 0 and ri.sign_status = 1)")
				 .or()
				 .apply(" (fp.pay_type = 1 and fp.training_status = 1 and ri.training_status = 1)")
		 );
		 queryWrapper.orderByDesc("ri.id");
		 IPage<RiderInterviewDTO> pageList = riderInterviewService.pageList(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  * 培训信息查询
	  *
	  * @param riderInterview
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="面试管理-培训信息查询", notes="面试管理-培训信息查询")
	 @GetMapping(value = "/trainList")
	 public Result<IPage<RiderInterviewDTO>> queryTrainList(RiderInterview riderInterview,
														@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														HttpServletRequest req) {
		 // 自定义查询规则
		 QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
		 Page<RiderInterview> page = new Page<RiderInterview>(pageNo, pageSize);
		 if(StringUtils.isNotBlank(riderInterview.getName())){
			 queryWrapper.like("ri.name", riderInterview.getName());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getPhone())){
			 queryWrapper.like("ri.phone", riderInterview.getPhone());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getReferencePhone())){
			 queryWrapper.like("ri.reference_phone", riderInterview.getReferencePhone());
		 }
		 if(Objects.nonNull(riderInterview.getPassStatus())){
			 queryWrapper.eq("ri.pass_status", riderInterview.getPassStatus());
		 }
		 if(Objects.nonNull(riderInterview.getSettleStatus())){
			 queryWrapper.eq("ri.settle_status", riderInterview.getSettleStatus());
		 }
		 if(Objects.nonNull(riderInterview.getStatus())){
			 queryWrapper.eq("ri.status", riderInterview.getStatus());
		 }
		 queryWrapper.and(wrapper1 -> wrapper1
				 .apply(" (fp.pay_type = 1 and fp.training_status = 1 and ri.sign_status = 1 and ri.training_status = 0)")
		 );
		 queryWrapper.orderByDesc("ri.id");
		 IPage<RiderInterviewDTO> pageList = riderInterviewService.pageList(page, queryWrapper);
		 return Result.OK(pageList);
	 }

	 /**
	  * 市场部信息查询
	  * @param riderInterview
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="面试管理-市场部信息", notes="面试管理-市场部信息")
	 @GetMapping(value = "/marketList")
	 public Result<IPage<RiderInterviewDTO>> queryMarketList(RiderInterview riderInterview,
															@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															HttpServletRequest req) {
		 // 自定义查询规则
		 QueryWrapper<RiderInterview> queryWrapper = new QueryWrapper<>();
		 Page<RiderInterview> page = new Page<RiderInterview>(pageNo, pageSize);
		 if(StringUtils.isNotBlank(riderInterview.getName())){
			 queryWrapper.like("ri.name", riderInterview.getName());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getPhone())){
			 queryWrapper.like("ri.phone", riderInterview.getPhone());
		 }
		 if(StringUtils.isNotBlank(riderInterview.getReferencePhone())){
			 queryWrapper.like("ri.reference_phone", riderInterview.getReferencePhone());
		 }
		 if(Objects.nonNull(riderInterview.getPassStatus())){
			 queryWrapper.eq("ri.pass_status", riderInterview.getPassStatus());
		 }
		 if(Objects.nonNull(riderInterview.getSettleStatus())){
			 queryWrapper.eq("ri.settle_status", riderInterview.getSettleStatus());
		 }
		 if(Objects.nonNull(riderInterview.getStatus())){
			 queryWrapper.eq("ri.status", riderInterview.getStatus());
		 }
		 queryWrapper.and(wrapper1 -> wrapper1
				 .apply(" (fp.pay_type = 0 and fp.market_status = 1 and ri.market_complete_status = 0)")
		 );
		 queryWrapper.orderByDesc("ri.id");
		 IPage<RiderInterviewDTO> pageList = riderInterviewService.pageList(page, queryWrapper);
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
				 //佣金
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
				 //若价格小于0，则价格为岗位价格
				 if(interviewDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0){
					 interviewDTO.setPrice(riderSite.getPrice());
					 //若岗位免费，则价格为0
					 if(!Objects.equals(riderSite.getPayType(), 1)){
						 interviewDTO.setPrice(BigDecimal.ZERO);
					 }
				 }
				 interviewDTO.setShowTrainStatus(riderSite.getTrainingStatus());
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
		 //若存在人才库
		 FamilyTalentPool familyTalentPool = familyTalentPoolService.getByPhone(riderCustomer.getPhone());
		 if(Objects.nonNull(familyTalentPool)){
			 //设置报名记录的申请人为当前领取人
			 riderInterview.setApplyUserId(familyTalentPool.getReceiver());
		 }
		 riderInterview.setName(riderCustomer.getName());
		 riderInterview.setPhone(riderCustomer.getPhone());
		 riderInterview.setSex(IdCardUtils.getGenderFromIdCard(riderCustomer.getIdCard()));
		 riderInterview.setAge(IdCardUtils.getAgeFromIdCard(riderCustomer.getIdCard()));
		 riderInterview.setSource("报名申请");
		 riderInterview.setReference(riderCustomer.getReference());
		 riderInterview.setReferencePhone(riderCustomer.getReferencePhone());
		 riderInterview.setIdCard(riderCustomer.getIdCard());
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
		if(StringUtils.isNotEmpty(riderInterview.getExpectRegion()) && StringUtils.isNotEmpty(riderInterview.getJobPosition())){
			riderInterviewService.handle(riderInterview);
		} else {
			return Result.OK("请输入必填项!");
		}
		if(StringUtils.isNotEmpty(riderInterview.getSiteId())){
			riderInterviewService.updateSite(riderInterview);
		}
		return Result.OK("编辑成功!");
	}

	 /**
	  *  设置支付金额
	  * @return
	  */
	 @AutoLog(value = "面试管理-设置支付金额")
	 @ApiOperation(value="面试管理-设置支付金额", notes="面试管理-设置支付金额")
	 @RequiresPermissions("interview:rider_interview:updatePriceBatch")
	 @RequestMapping(value = "/updatePriceBatch", method = {RequestMethod.POST})
	 public Result<String> updatePriceBatch(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="price",required=true) BigDecimal price) {
		 riderInterviewService.updatePriceBatch(ids, price);
		 return Result.OK("设置成功!");
	 }


	 /**
	  *  确认培训
	  * @return
	  */
	 @AutoLog(value = "面试管理-确认培训")
	 @ApiOperation(value="面试管理-确认培训", notes="面试管理-确认培训")
	 @RequiresPermissions("interview:rider_interview:confirmTraining")
	 @RequestMapping(value = "/confirmTraining", method = {RequestMethod.POST})
	 public Result<String> confirmTraining(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="trainingTeacher",required=true) String trainingTeacher) {
		 if(StringUtils.isEmpty(trainingTeacher)){
			 throw new JeecgBootException("请输入招聘老师！");
		 }
		 riderInterviewService.confirmTraining(ids, trainingTeacher);
		 return Result.OK("确认培训成功!");
	 }

	 /**
	  *  转到安置信息
	  * @return
	  */
	 @AutoLog(value = "面试管理-转到安置信息")
	 @ApiOperation(value="面试管理-转到安置信息", notes="面试管理-转到安置信息")
	 @RequiresPermissions("interview:rider_interview:marketCompleteBatch")
	 @RequestMapping(value = "/marketCompleteBatch", method = {RequestMethod.POST})
	 public Result<String> marketComplete(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 throw new JeecgBootException("请选择要操作的记录！");
		 }
		 riderInterviewService.marketCompleteBatch(ids);
		 return Result.OK("确认转到安置成功!");
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
		 String template = train_order_template.getParamValue();
		 RiderInterview interview = riderInterviewService.getById(riderInterview.getId());
		 if(Objects.isNull(interview)){
			 return Result.error("报名记录不存在!");
		 }
		 if(StringUtils.isEmpty(interview.getTrainingTeacher())){
			 return Result.error("暂未进行培训，请联系客服!");
		 }
		 Map<String, Object> data = new HashMap<>();
		 data.put("name", interview.getName());
		 data.put("phone", interview.getPhone());
		 data.put("year", DateUtils.getYear());
		 data.put("month", DateUtils.getMonth());
		 data.put("day", DateUtils.getDay());
	 	 data.put("teacher", interview.getTrainingTeacher());
		 data.put("signName", interview.getName());
		 for (Map.Entry<String, Object> entry : data.entrySet()) {
			 String placeholder = "${" + entry.getKey() + "}";
			 template = template.replace(placeholder, Objects.nonNull(entry.getValue())?entry.getValue().toString():"");
		 }
		 InterviewOrderDTO dto = new InterviewOrderDTO();
		 dto.setContent(template);
		 return Result.ok(dto);
	 }

	 /**
	  *  岗位培训单
	  * @param riderInterview
	  * @return
	  */
	 @AutoLog(value = "岗位培训单确认")
	 @ApiOperation(value="岗位培训单确认", notes="岗位培训单确认")
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
		 String template = confirm_order_template.getParamValue();
		 RiderInterview interview = riderInterviewService.getById(riderInterview.getId());
		 if(Objects.isNull(interview)){
			 return Result.error("报名记录不存在!");
		 }
		 if(StringUtils.isEmpty(interview.getOperatorName())){
			 return Result.error("暂无分配岗位，请联系客服!");
		 }
		 Map<String, Object> data = new HashMap<>();
		 data.put("name", interview.getName());
		 data.put("phone", interview.getPhone());
		 data.put("year", DateUtils.getYear());
		 data.put("month", DateUtils.getMonth());
		 data.put("day", DateUtils.getDay());
		 data.put("area", interview.getExpectRegion());
		 data.put("hotel", interview.getJobPosition());
		 data.put("teacher", interview.getOperatorName());
		 data.put("signName", interview.getName());
		 for (Map.Entry<String, Object> entry : data.entrySet()) {
			 String placeholder = "${" + entry.getKey() + "}";
			 template = template.replace(placeholder, Objects.nonNull(entry.getValue())?entry.getValue().toString():"");
		 }
		 InterviewOrderDTO dto = new InterviewOrderDTO();
		 dto.setContent(template);
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
