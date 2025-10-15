package org.jeecg.modules.rider.customer.controller;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.dto.RiderCustomerDTO;
import org.jeecg.modules.rider.customer.dto.RiderCustomerReceiveDTO;
import org.jeecg.modules.rider.customer.dto.RiderReferenceDTO;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.params.service.IRiderParamsService;
import org.jeecg.modules.rider.params.service.impl.RiderParamsServiceImpl;
import org.jeecg.modules.rider.qrcode.entity.RiderQrcode;
import org.jeecg.modules.rider.qrcode.service.IRiderQrcodeService;
import org.jeecg.modules.system.service.ISysCategoryService;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
@Api(tags="客户管理")
@RestController
@RequestMapping("/customer/riderCustomer")
@Slf4j
public class RiderCustomerController extends JeecgController<RiderCustomer, IRiderCustomerService> {
	@Autowired
	private IRiderCustomerService riderCustomerService;

	@Autowired
	private IRiderQrcodeService riderQrcodeService;

	@Autowired
	private IRiderParamsService riderParamsService;

	 @Autowired
	 private IRiderInterviewService riderInterviewService;


	 @Autowired
	 private ISysCategoryService sysCategoryService;

	 @Value("${jeecg.path.prefix}")
	 private String upLoadPrefix;

	 private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);


	 static {
		 pool.scheduleWithFixedDelay(() -> {
			 try {
				log.info("定时移出主理人领取列表开始");
				 IRiderParamsService paramsService = (IRiderParamsService) ApplicationContextUtil.getContext().getBean("riderParamsServiceImpl");
				 RiderParams remove_resume_date = paramsService.getByCode("REMOVE_RESUME_DATE");
				 int remove_date = 7;
				 if(Objects.nonNull(remove_resume_date) && StringUtils.isNotBlank(remove_resume_date.getParamValue())){
					 remove_date = Integer.parseInt(remove_resume_date.getParamValue());
				 }
				 IRiderCustomerService customerService = (IRiderCustomerService) ApplicationContextUtil.getContext().getBean("riderCustomerServiceImpl");
				 QueryWrapper<RiderCustomer> queryWrapper = new QueryWrapper<>();
				 queryWrapper.lambda().eq(RiderCustomer::getApplyStatus,0)
						 .eq(RiderCustomer::getReceiveStatus,1)
						 .le(RiderCustomer::getReceiveTime, DateUtils.getAfterDate(DateUtils.date2Str(DateUtils.getDate(), DateUtils.yyyyMMdd.get()),0-remove_date))
						 .orderByDesc(RiderCustomer::getCreateTime);
				 List<RiderCustomer> list = customerService.list(queryWrapper);
				 if(!CollectionUtils.isEmpty(list)){
					 List<String> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());
					 UpdateWrapper<RiderCustomer> updateWrapper = new UpdateWrapper();
					 updateWrapper.lambda().set(RiderCustomer::getReceiveStatus,0)
							 .set(RiderCustomer::getReceiver,null)
							 .set(RiderCustomer::getReceiveTime,null)
							 .in(RiderCustomer::getId,idList);
					 customerService.update(updateWrapper);
				 }
				 log.info("定时移出主理人领取列表完成");
			 } catch (Exception e) {
				 log.error("定时移出主理人领取列表异常：",e);
			 }
		 }, 0, 1, TimeUnit.HOURS);
	 }

	/**
	 * 分页列表查询
	 *
	 * @param riderCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "客户管理-分页列表查询")
	@ApiOperation(value="客户管理-分页列表查询", notes="客户管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RiderCustomer>> queryPageList(RiderCustomer riderCustomer,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req) {
        // 自定义查询规则
        Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
        // 自定义多选的查询规则为：LIKE_WITH_OR
        customeRuleMap.put("identity", QueryRuleEnum.LIKE_WITH_OR);
        QueryWrapper<RiderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(riderCustomer, req.getParameterMap(),customeRuleMap);
		Page<RiderCustomer> page = new Page<RiderCustomer>(pageNo, pageSize);
		IPage<RiderCustomer> pageList = riderCustomerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 查询客户信息列表
	  * @return
	  */
	 @RequestMapping(value = "/queryList", method = RequestMethod.GET)
	 public Result<List<RiderCustomer>> queryList(@RequestParam(name="ids",required=false) String ids) {
		 Result<List<RiderCustomer>> result = new Result<List<RiderCustomer>>();
		 LambdaQueryWrapper<RiderCustomer> query = new LambdaQueryWrapper<>();
		 if(oConvertUtils.isNotEmpty(ids)){
			 query.in(RiderCustomer::getId, ids.split(","));
		 }
		 //此处查询忽略时间条件
		 List<RiderCustomer> ls = riderCustomerService.list(query);
		 result.setSuccess(true);
		 result.setResult(ls);
		 return result;
	 }

	 /**
	  * 通过推广人查询客户信息列表
	  * @return
	  */
	 @ApiOperation(value="我的推广人查询列表", notes="我的推广人查询列表")
	 @RequestMapping(value = "/listByReference", method = RequestMethod.GET)
	 public Result<RiderReferenceDTO> listByReference(@RequestParam(name="customerId",required=false) String customerId,@RequestParam(name="identity",required=false) Integer identity) {
		 RiderReferenceDTO referenceDTO = new RiderReferenceDTO();
		 referenceDTO.setListCount(0);
		 List<RiderCustomer> ls = new ArrayList<>();
		 if(StringUtils.isNotEmpty(customerId)){
			 //获取推广人信息
			 LambdaQueryWrapper<RiderCustomer> query = new LambdaQueryWrapper<>();
			 query.eq(RiderCustomer::getReference, customerId);
			 if(identity > 0){
				 query.eq(RiderCustomer::getIdentity, identity);
			 }
			 ls = riderCustomerService.list(query);
			 referenceDTO.setListCount(ls.size());
		 }
		 referenceDTO.setList(ls);
		 return Result.OK(referenceDTO);
	 }


	 /**
	  * 简历库查询列表
	  * @return
	  */
	 @ApiOperation(value="简历库查询列表", notes="简历库查询列表")
	 @RequestMapping(value = "/listByAllResume", method = RequestMethod.GET)
	 public Result<IPage<RiderCustomerDTO>> listByResume(RiderCustomerDTO riderCustomer,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		 RiderParams resume_before_date = riderParamsService.getByCode("RESUME_BEFORE_DATE");
		 int before_date = 7;
		 if(Objects.nonNull(resume_before_date) && StringUtils.isNotBlank(resume_before_date.getParamValue())){
			 before_date = Integer.parseInt(resume_before_date.getParamValue());
		 }
		 // 自定义查询规则
		 Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
		 String promoterName = riderCustomer.getPromoterName();
		 // 复制一份请求参数（避免修改原始参数）
		 Map<String, String[]> parameterMap = new HashMap<>(req.getParameterMap());
		 if(StringUtils.isNotBlank(riderCustomer.getPromoterName())){
			 riderCustomer.setPromoterName(null);
			 parameterMap.remove("promoterName");
		 }
		 QueryWrapper<RiderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(riderCustomer, parameterMap,customeRuleMap);
		 queryWrapper.lambda().eq(RiderCustomer::getIdentity,CustomerIdentityEnum.TOURIST.getCode())
				 .le(RiderCustomer::getCreateTime, DateUtils.getAfterDate(DateUtils.date2Str(DateUtils.getDate(), DateUtils.yyyyMMdd.get()),0-before_date))
				 .eq(RiderCustomer::getReceiveStatus,0)
				 .orderByDesc(RiderCustomer::getCreateTime);
		 Page<RiderCustomer> page = new Page<RiderCustomer>(pageNo, pageSize);
		 if(StringUtils.isNotBlank(promoterName)){
			 QueryWrapper<RiderCustomer> query = new QueryWrapper<>();
			 query.lambda().like(RiderCustomer::getName,promoterName);
			 List<RiderCustomer> list = riderCustomerService.list(query);
			 List<String> ids = list.stream().map(x -> x.getId()).collect(Collectors.toList());
			 queryWrapper.lambda().and(wrapper1 -> wrapper1
					 .like(RiderCustomer::getName, promoterName)
					 .or()
					 .like(RiderCustomer::getPhone,promoterName)
					 .or()
					 .in(ids.size() > 0, RiderCustomer::getReference,ids)
			 );
		 }
		 IPage<RiderCustomer> pageList = riderCustomerService.page(page, queryWrapper);
		 List<RiderCustomer> list = pageList.getRecords();
		 List<String> ids = list.stream().filter(s -> StringUtils.isNotBlank(s.getReference())).map(x -> x.getReference()).collect(Collectors.toList());
		 List<RiderCustomer> referenceList = ids.size()> 0 ? riderCustomerService.listByIds(ids) : new ArrayList<>();
		 Map<String, RiderCustomer> referenceMap = referenceList.stream().collect(Collectors.toMap(RiderCustomer::getId, Function.identity(), (a, b) -> b));
		 IPage<RiderCustomerDTO> dtoPage = pageList.convert(x -> {
			 RiderCustomerDTO customerDTO = new RiderCustomerDTO();
			 BeanUtils.copyProperties(x, customerDTO);
			 RiderCustomer r = referenceMap.get(x.getReference());
			 if (r != null) {
				 customerDTO.setPromoterName(r.getName());
			 }
			 if(StringUtils.isEmpty(x.getIdCard())){
				 customerDTO.setName("***");
			 }
			 return customerDTO;
		 });
		 return Result.OK(dtoPage);
	 }

	 /**
	  * 我的领取列表
	  * @return
	  */
	 @ApiOperation(value="我的领取列表", notes="我的领取列表")
	 @RequestMapping(value = "/listByMyResume", method = RequestMethod.GET)
	 public Result<List<RiderCustomerDTO>> listByMyResume(@RequestParam(name="customerId",required=false) String customerId) {
		 if(StringUtils.isNotEmpty(customerId)){
			 RiderParams remove_resume_date = riderParamsService.getByCode("REMOVE_RESUME_DATE");
			 long remove_date = 7L;
			 if(Objects.nonNull(remove_resume_date) && StringUtils.isNotBlank(remove_resume_date.getParamValue())){
				 remove_date = Long.parseLong(remove_resume_date.getParamValue());
			 }
			 QueryWrapper<RiderCustomer> queryWrapper = new QueryWrapper<>();
			 queryWrapper.lambda().eq(RiderCustomer::getReceiver,customerId)
					 .orderByDesc(RiderCustomer::getCreateTime);
			 List<RiderCustomer> list = riderCustomerService.list(queryWrapper);
			 List<String> ids = list.stream().filter(s -> StringUtils.isNotBlank(s.getReference())).map(x -> x.getReference()).collect(Collectors.toList());
			 List<RiderCustomer> referenceList = ids.size()> 0 ? riderCustomerService.listByIds(ids) : new ArrayList<>();
			 Map<String, RiderCustomer> referenceMap = referenceList.stream().collect(Collectors.toMap(RiderCustomer::getId, Function.identity(), (a, b) -> b));
			 final long final_remove_date = remove_date;
			 List<RiderCustomerDTO> customerDTOList = list.stream().map(x -> {
				 RiderCustomerDTO customerDTO = new RiderCustomerDTO();
				 BeanUtils.copyProperties(x, customerDTO);
				 RiderCustomer riderCustomer = referenceMap.get(x.getReference());
				 if(riderCustomer != null){
					 customerDTO.setPromoterName(riderCustomer.getName());
				 }
				 if(Objects.equals(0,customerDTO.getApplyStatus()) && Objects.nonNull(customerDTO.getReceiveTime())){
					 // 与当前时间差
					 long differenceInDays = DateUtils.calculateDaysDifference(customerDTO.getReceiveTime());
					 //剩余时间
					 customerDTO.setRemainTime((final_remove_date + differenceInDays)+"天");
				 }
				 return customerDTO;
			 }).collect(Collectors.toList());
			 return Result.OK(customerDTOList);
		 }
		 return Result.OK();
	 }

	 /**
	  *  领取客户
	  *
	  * @param receiveDTO
	  * @return
	  */
	 @AutoLog(value = "领取客户")
	 @ApiOperation(value="领取客户", notes="领取客户")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/receiveCustomer", method = {RequestMethod.POST})
	 public Result<String> receiveCustomer(@RequestBody RiderCustomerReceiveDTO receiveDTO) {
		 RiderCustomer riderCustomer = riderCustomerService.getById(receiveDTO.getId());
		 if(Objects.isNull(riderCustomer)){
			 throw new JeecgBootException("该用户不存在!");
		 }
		 //	获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 RiderCustomer r = riderCustomerService.getByPhone(loginUser.getPhone());
		 if (oConvertUtils.isEmpty(r)) {
			 return Result.error("请注册用户！");
		 }
		 int receive_num = 20;
		 RiderParams receive_customer_num = riderParamsService.getByCode("RECEIVE_CUSTOMER_NUM");
		 if(Objects.nonNull(receive_customer_num) && StringUtils.isNotBlank(receive_customer_num.getParamValue())){
			 receive_num = Integer.parseInt(receive_customer_num.getParamValue());
		 }
		 QueryWrapper<RiderCustomer> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(RiderCustomer::getReceiver,r.getId());
		 long count = riderCustomerService.count(queryWrapper);
		 if(count >= receive_num){
			 throw new JeecgBootException("已超过领取客户数量限制!");
		 }
		 //riderCustomer.setTag(receiveDTO.getTag());
		 //riderCustomer.setIntention(receiveDTO.getIntention());
		 //riderCustomer.setPostRequirement(receiveDTO.getPostRequirement());
		 riderCustomer.setReceiver(r.getId());
		 riderCustomer.setReceiveStatus(1);
		 riderCustomer.setReceiveTime(new Date());
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("领取客户成功!");
	 }

	 /**
	  *  打标签
	  *
	  * @param receiveDTO
	  * @return
	  */
	 @AutoLog(value = "打标签")
	 @ApiOperation(value="打标签", notes="打标签")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/setTag", method = {RequestMethod.POST})
	 public Result<String> setTag(@RequestBody RiderCustomerReceiveDTO receiveDTO) {
		 RiderCustomer riderCustomer = riderCustomerService.getById(receiveDTO.getId());
		 if(Objects.isNull(riderCustomer)){
			 throw new JeecgBootException("该用户不存在!");
		 }
		 //	获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 RiderCustomer r = riderCustomerService.getByPhone(loginUser.getPhone());
		 if (oConvertUtils.isEmpty(r)) {
			 return Result.error("请注册用户！");
		 }
		 riderCustomer.setTag(receiveDTO.getTag());
		 riderCustomer.setIntention(receiveDTO.getIntention());
		 riderCustomer.setPostRequirement(receiveDTO.getPostRequirement());
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("设置标签成功!");
	 }

	 /**
	  *  移除客户
	  *
	  * @param receiveDTO
	  * @return
	  */
	 @AutoLog(value = "移除客户")
	 @ApiOperation(value="移除客户", notes="移除客户")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/removeCustomer", method = {RequestMethod.POST})
	 public Result<String> removeCustomer(@RequestBody RiderCustomerReceiveDTO receiveDTO) {
		 RiderCustomer riderCustomer = riderCustomerService.getById(receiveDTO.getId());
		 if(Objects.isNull(riderCustomer)){
			 throw new JeecgBootException("该用户不存在!");
		 }
		 riderCustomer.setReceiver("");
		 riderCustomer.setReceiveStatus(0);
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("移除客户成功!");
	 }



	 /**
	  *  申请通过
	  *
	  * @param receiveDTO
	  * @return
	  */
	 @AutoLog(value = "申请通过")
	 @ApiOperation(value="申请通过", notes="申请通过")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/applyPass", method = {RequestMethod.POST})
	 public Result<String> applyPass(@RequestBody RiderCustomerReceiveDTO receiveDTO) {
		 RiderCustomer riderCustomer = riderCustomerService.getById(receiveDTO.getId());
		 if(Objects.isNull(riderCustomer)){
			 throw new JeecgBootException("该用户不存在!");
		 }
		 if(riderCustomer.getIdentity() == CustomerIdentityEnum.TOURIST.getCode()){
			 throw new JeecgBootException("该用户未报名，不允许申请!");
		 }
		 //判断是否存在已支付的岗位
		 RiderInterview one = riderInterviewService.getOne(new QueryWrapper<RiderInterview>()
				.eq("pay_status", 1)
				 .eq("phone", riderCustomer.getPhone())
				 .last(" limit 1 "));
		 if(one == null) {
			 //判断是否存在工厂岗位
			 String pid = "1946598810814877697";
			 List<String> categoryIds = sysCategoryService.queryAllChildIds(pid);
			 //判断是否存在报名工厂的岗位
			 List<RiderInterview> riderInterviews = riderInterviewService.queryListByCategory(riderCustomer.getPhone(), categoryIds);
			 if(CollectionUtils.isEmpty(riderInterviews)) {
				 return Result.error("该用户报名未支付，不允许申请！");
			 }
		 }
		 if(riderCustomer.getApplyStatus() == 1){
			 throw new JeecgBootException("该用户已申请，不能重复申请!");
		 }
		 riderCustomer.setApplyStatus(1);
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("申请通过成功!");
	 }


	 /**
	  * 我的推广人汇总
	  * @return
	  */
	 @ApiOperation(value="我的推广人汇总", notes="我的推广人汇总")
	 @RequestMapping(value = "/totalByReference", method = RequestMethod.GET)
	 public Result<RiderReferenceDTO> totalByReference(@RequestParam(name="customerId",required=false) String customerId) {
		 if(customerId == null){
			 return Result.error("参数错误");
		 }
		 RiderReferenceDTO referenceDTO = new RiderReferenceDTO();
		 referenceDTO.setListCount(0);
		 referenceDTO.setOneListCount(0);
		 referenceDTO.setTwoListCount(0);
		 referenceDTO.setThreeListCount(0);
		 List<RiderCustomer> ls = new ArrayList<>();
		 if(StringUtils.isNotEmpty(customerId)){
			 //获取推广人信息
			 LambdaQueryWrapper<RiderCustomer> query = new LambdaQueryWrapper<>();
			 query.eq(RiderCustomer::getReference, customerId);
			 ls = riderCustomerService.list(query);
			 referenceDTO.setListCount(ls.size());
			 Map<Integer, List<RiderCustomer>> map = ls.stream().collect(Collectors.groupingBy(RiderCustomer::getIdentity));
			 if(map.containsKey(CustomerIdentityEnum.TOURIST.getCode())){
			 	referenceDTO.setOneListCount(map.get(CustomerIdentityEnum.TOURIST.getCode()).size());
			 }
			 if(map.containsKey(CustomerIdentityEnum.RIDER.getCode())){
			 	referenceDTO.setTwoListCount(map.get(CustomerIdentityEnum.RIDER.getCode()).size());
			 }
			 if(map.containsKey(CustomerIdentityEnum.PARTNER.getCode())){
			 	referenceDTO.setThreeListCount(map.get(CustomerIdentityEnum.PARTNER.getCode()).size());
			 }
		 }
		 referenceDTO.setList(ls);
		 return Result.OK(referenceDTO);
	 }

	/**
	 *   添加
	 *
	 * @param riderCustomer
	 * @return
	 */
	@AutoLog(value = "客户管理-添加")
	@ApiOperation(value="客户管理-添加", notes="客户管理-添加")
	@RequiresPermissions("customer:rider_customer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RiderCustomer riderCustomer) {
		riderCustomerService.save(riderCustomer);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param riderCustomer
	 * @return
	 */
	@AutoLog(value = "客户管理-编辑")
	@ApiOperation(value="客户管理-编辑", notes="客户管理-编辑")
	@RequiresPermissions("customer:rider_customer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RiderCustomer riderCustomer) {
		riderCustomerService.updateById(riderCustomer);
		return Result.OK("编辑成功!");
	}

	 /**
	  *  更新站点城市
	  *
	  * @param riderCustomer
	  * @return
	  */
	 @AutoLog(value = "客户管理-更新站点城市")
	 @ApiOperation(value="客户管理-更新站点城市", notes="客户管理-更新站点城市")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/updateCity", method = {RequestMethod.POST})
	 public Result<String> updateCity(@RequestBody RiderCustomer riderCustomer) {
		 if(StringUtils.isEmpty(riderCustomer.getId())){
			 //	获取当前用户
			 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			 if (oConvertUtils.isEmpty(loginUser)) {
				 return Result.error("请登录系统！");
			 }
			 RiderCustomer r = riderCustomerService.getByPhone(loginUser.getPhone());
			 if (oConvertUtils.isEmpty(r)) {
				 return Result.error("请注册用户！");
			 }
			 riderCustomer.setId(r.getId());
		 }
		 if(StringUtils.isEmpty(riderCustomer.getSiteCity())){
			 throw new JeecgBootException("站点城市不能为空");
		 }
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("更新成功!");
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户管理-通过id删除")
	@ApiOperation(value="客户管理-通过id删除", notes="客户管理-通过id删除")
	@RequiresPermissions("customer:rider_customer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		riderCustomerService.deleteBatch( id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客户管理-批量删除")
	@ApiOperation(value="客户管理-批量删除", notes="客户管理-批量删除")
	@RequiresPermissions("customer:rider_customer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		riderCustomerService.deleteBatch(ids);
		return Result.OK("批量删除成功!");
	}

	 /**
	  *  更新推广人
	  *
	  * @param riderCustomer
	  * @return
	  */
	 @AutoLog(value = "客户管理-更新推广人")
	 @ApiOperation(value="客户管理-更新推广人", notes="客户管理-更新推广人")
	 @RequiresPermissions("customer:rider_customer:edit")
	 @RequestMapping(value = "/updateReference", method = {RequestMethod.POST})
	 public Result<String> updateReference(@RequestBody RiderCustomer riderCustomer) {
		 //	获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 RiderCustomer r = riderCustomerService.getByPhone(loginUser.getPhone());
		 if (oConvertUtils.isEmpty(r)) {
			 return Result.error("请注册用户！");
		 }
		 if (Objects.equals( CustomerIdentityEnum.PARTNER.getCode(),r.getIdentity())) {
			 return Result.error("当前用户已经是合伙人，不能修改推广人！");
		 }
		 riderCustomer.setId(r.getId());
		 if(StringUtils.isEmpty(riderCustomer.getReferencePhone())){
			 throw new JeecgBootException("推广人手机号不能为空");
		 }
		 if(riderCustomer.getReferencePhone().length() < 4){
			 throw new JeecgBootException("请输入推广人手机号后4位！");
		 }
		 List<RiderCustomer> list = riderCustomerService.getByReferencePhone(riderCustomer.getReferencePhone());
		 if( CollectionUtils.isEmpty(list)){
			 throw new JeecgBootException("推广人手机号后4位不存在");
		 }
		 if(list.size() > 1){
			 throw new JeecgBootException("推广人手机号后4位存在多个，请输入全部的手机号");
		 }
		 RiderCustomer referenceCustomer = list.get(0);
		 if (!Objects.equals( CustomerIdentityEnum.PARTNER.getCode(),referenceCustomer.getIdentity())) {
			 return Result.error("该推广人手机号不是合伙人，不能修改！");
		 }
		 // 修改当前用户的推广人信息
		 riderCustomer.setReference(referenceCustomer.getId());
		 riderCustomer.setReferencePhone(referenceCustomer.getPhone());
		 riderCustomerService.updateById(riderCustomer);
		 return Result.OK("更新成功!");
	 }

	 /**
	  *  升级为合伙人
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "客户管理-升级为合伙人")
	 @ApiOperation(value="客户管理-升级为合伙人", notes="客户管理-升级为合伙人")
	 @PostMapping(value = "/upgradePartner")
	 public Result<String> upgradePartner(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.riderCustomerService.upgradePartner(ids);
		 return Result.OK("批量更新成功!");
	 }

	 /**
	  *  升级为渠道商
	  *
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "客户管理-升级为渠道商")
	 @ApiOperation(value="客户管理-升级为渠道商", notes="客户管理-升级为渠道商")
	 @PostMapping(value = "/upgradeSite")
	 public Result<String> upgradeSite(@RequestParam(name="ids",required=true) String ids,@RequestParam(name="profit",required=true) Integer profit,@RequestParam(name="commission",required=true) Integer commission) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 if(Objects.isNull(profit)){
			 return Result.error("站点利润百分比不能为空!");
		 }
		 if(Objects.isNull(commission)){
			 return Result.error("推广佣金不能为空!");
		 }
		 this.riderCustomerService.upgradeSite(ids, profit,commission);
		 return Result.OK("批量更新成功!");
	 }

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "客户管理-通过id查询")
	@ApiOperation(value="客户管理-通过id查询", notes="客户管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RiderCustomerDTO> queryById(@RequestParam(name="id",required=true) String id) {
		RiderCustomer riderCustomer = riderCustomerService.getById(id);
		if(riderCustomer==null) {
			return Result.error("未找到对应数据");
		}
		RiderCustomerDTO riderCustomerDTO = riderCustomerService.convertTotal(riderCustomer);
		return Result.OK(riderCustomerDTO);
	}

	 @AutoLog(value = "客户管理-获取二维码")
	 @ApiOperation(value="客户管理-获取二维码", notes="客户管理-获取二维码")
	 @GetMapping(value = "/getQrcode")
	 public Result<String> getQrcode(@RequestParam(name="id",required=true) String id) {
		 RiderCustomer riderCustomer = riderCustomerService.getById(id);
		 if(riderCustomer==null) {
			 return Result.error("未找到对应数据");
		 }
		 //若不是合伙人，则不能获取
		 if(riderCustomer.getIdentity() != CustomerIdentityEnum.PARTNER.getCode()) {
			 return Result.error("您当前不是合伙人，请先升级为合伙人！");
		 }
		 //如果二维码为空，则新增二维码
		 if(StringUtils.isEmpty(riderCustomer.getQrcode())){
			 //生成二维码
			 RiderQrcode riderQrcode = new RiderQrcode();
			 riderQrcode.setCustomerId(riderCustomer.getId());
			 riderQrcode.setPhone(riderCustomer.getPhone());
			 riderQrcodeService.saveRiderQrcode(riderQrcode);
			 //更新客户的二维码
			 riderCustomer.setQrcode(riderQrcode.getUrl());
			 riderCustomerService.updateById(riderCustomer);
		 }
		 String qrcode = riderCustomer.getQrcode();
		 return Result.OK(qrcode);
	 }


    /**
    * 导出excel
    *
    * @param request
    * @param riderCustomer
    */
    @RequiresPermissions("customer:rider_customer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RiderCustomer riderCustomer) {
        return super.exportXls(request, riderCustomer, RiderCustomer.class, "客户管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("customer:rider_customer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RiderCustomer.class);
    }

}
