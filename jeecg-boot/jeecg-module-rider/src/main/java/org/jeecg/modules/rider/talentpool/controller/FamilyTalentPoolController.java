package org.jeecg.modules.rider.talentpool.controller;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.params.service.IRiderParamsService;
import org.jeecg.modules.rider.talentpool.entity.FamilyTalentPool;
import org.jeecg.modules.rider.talentpool.service.IFamilyTalentPoolService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 人才库
 * @Author: jeecg-boot
 * @Date:   2025-10-19
 * @Version: V1.0
 */
@Api(tags="人才库")
@RestController
@RequestMapping("/talentpool/familyTalentPool")
@Slf4j
public class FamilyTalentPoolController extends JeecgController<FamilyTalentPool, IFamilyTalentPoolService> {
	@Autowired
	private IFamilyTalentPoolService familyTalentPoolService;

	@Autowired
	private IRiderCustomerService riderCustomerService;

	 private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);


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
				 IFamilyTalentPoolService talentPoolService = (IFamilyTalentPoolService) ApplicationContextUtil.getContext().getBean("familyTalentPoolServiceImpl");
				 QueryWrapper<FamilyTalentPool> queryWrapper = new QueryWrapper<>();
				 queryWrapper.lambda().eq(FamilyTalentPool::getApplyStatus,0)
						 .eq(FamilyTalentPool::getReceiveStatus,1)
						 .le(FamilyTalentPool::getReceiveTime, DateUtils.getAfterDate(DateUtils.date2Str(DateUtils.getDate(), DateUtils.yyyyMMdd.get()),0-remove_date))
						 .orderByDesc(FamilyTalentPool::getCreateTime);
				 List<FamilyTalentPool> list = talentPoolService.list(queryWrapper);
				 if(!CollectionUtils.isEmpty(list)){
					 List<String> idList = list.stream().map(x -> x.getId()).collect(Collectors.toList());
					 UpdateWrapper<FamilyTalentPool> updateWrapper = new UpdateWrapper();
					 updateWrapper.lambda().set(FamilyTalentPool::getReceiveStatus,0)
							 .set(FamilyTalentPool::getReceiver,null)
							 .set(FamilyTalentPool::getReceiveTime,null)
							 .in(FamilyTalentPool::getId,idList);
					 talentPoolService.update(updateWrapper);
				 }
				 log.info("定时移出主理人领取列表完成");
			 } catch (Exception e) {
				 log.error("定时移出主理人领取列表异常：",e);
			 }
		 }, 0, 10, TimeUnit.SECONDS);

		 pool.scheduleWithFixedDelay(() -> {
			 try {
				 log.info("定时移入简历库列表开始");
				 IRiderParamsService paramsService = (IRiderParamsService) ApplicationContextUtil.getContext().getBean("riderParamsServiceImpl");
				 RiderParams resume_before_date = paramsService.getByCode("RESUME_BEFORE_DATE");
				 int before_date = 7;
				 if(Objects.nonNull(resume_before_date) && StringUtils.isNotBlank(resume_before_date.getParamValue())){
					 before_date = Integer.parseInt(resume_before_date.getParamValue());
				 }
				 //查找未移入简历库的会员
				 IRiderCustomerService customerService = (IRiderCustomerService) ApplicationContextUtil.getContext().getBean("riderCustomerServiceImpl");
				 QueryWrapper<RiderCustomer> queryWrapper = new QueryWrapper<>();
				 queryWrapper.lambda().eq(RiderCustomer::getIdentity,CustomerIdentityEnum.TOURIST.getCode())
						 .le(RiderCustomer::getCreateTime, DateUtils.getAfterDate(DateUtils.date2Str(DateUtils.getDate(), DateUtils.yyyyMMdd.get()),0-before_date))
						 .eq(RiderCustomer::getMoveStatus,0);
				 List<RiderCustomer> list = customerService.list(queryWrapper);
				 if(!CollectionUtils.isEmpty(list)){
					 List<String> phoneList = list.stream().map(x -> x.getPhone()).collect(Collectors.toList());
					 //查找已存在简历库的数据
					 IFamilyTalentPoolService talentPoolService = (IFamilyTalentPoolService) ApplicationContextUtil.getContext().getBean("familyTalentPoolServiceImpl");
					 QueryWrapper<FamilyTalentPool> poolQueryWrapper = new QueryWrapper<>();
					 poolQueryWrapper.lambda().in(FamilyTalentPool::getPhone,phoneList);
					 //获取已存在手机号列表
					 List<FamilyTalentPool> existList = talentPoolService.list(poolQueryWrapper);
					 Set<String> existPhoneList = existList.stream().map(x -> x.getPhone()).collect(Collectors.toSet());
					 //	不存在的手机号插入到简历库信息
					 List<FamilyTalentPool> saveList = list.stream().filter(x -> !existPhoneList.contains(x.getPhone())).map(x -> {
						 FamilyTalentPool talentPool = new FamilyTalentPool();
						 talentPool.setName(x.getName());
						 talentPool.setPhone(x.getPhone());
						 talentPool.setCustomerId(x.getId());
						 return talentPool;
					 }).collect(Collectors.toList());
					 if(!CollectionUtils.isEmpty( saveList)){
						 talentPoolService.saveBatch( saveList);

						 //更新移入状态
						 UpdateWrapper<RiderCustomer> updateWrapper = new UpdateWrapper();
						 updateWrapper.lambda().set(RiderCustomer::getMoveStatus,1)
								 .in(RiderCustomer::getPhone,saveList.stream().map(x -> x.getPhone()).collect(Collectors.toSet()));
						 customerService.update(updateWrapper);
					 }
				 }
				 log.info("定时移入简历库列表完成");
			 } catch (Exception e) {
				 log.error("定时移入简历库列表异常：",e);
			 }
		 }, 0, 10, TimeUnit.SECONDS);
	 }


	/**
	 * 分页列表查询
	 *
	 * @param familyTalentPool
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "人才库-分页列表查询")
	@ApiOperation(value="人才库-分页列表查询", notes="人才库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FamilyTalentPool>> queryPageList(FamilyTalentPool familyTalentPool,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<FamilyTalentPool> queryWrapper = QueryGenerator.initQueryWrapper(familyTalentPool, req.getParameterMap());
		Page<FamilyTalentPool> page = new Page<FamilyTalentPool>(pageNo, pageSize);
		IPage<FamilyTalentPool> pageList = familyTalentPoolService.page(page, queryWrapper);
		pageList.getRecords().forEach(x -> {
			if(StringUtils.isEmpty(x.getName()) && StringUtils.isNotEmpty(x.getCustomerId())){
				RiderCustomer customer = riderCustomerService.getById(x.getCustomerId());
				if(Objects.nonNull(customer)){
					x.setName(customer.getName());
				}
			}
		});
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param familyTalentPool
	 * @return
	 */
	@AutoLog(value = "人才库-添加")
	@ApiOperation(value="人才库-添加", notes="人才库-添加")
	@RequiresPermissions("talentpool:family_talent_pool:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FamilyTalentPool familyTalentPool) {
		familyTalentPoolService.save(familyTalentPool);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param familyTalentPool
	 * @return
	 */
	@AutoLog(value = "人才库-编辑")
	@ApiOperation(value="人才库-编辑", notes="人才库-编辑")
	@RequiresPermissions("talentpool:family_talent_pool:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FamilyTalentPool familyTalentPool) {
		familyTalentPoolService.updateById(familyTalentPool);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人才库-通过id删除")
	@ApiOperation(value="人才库-通过id删除", notes="人才库-通过id删除")
	@RequiresPermissions("talentpool:family_talent_pool:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		familyTalentPoolService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人才库-批量删除")
	@ApiOperation(value="人才库-批量删除", notes="人才库-批量删除")
	@RequiresPermissions("talentpool:family_talent_pool:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.familyTalentPoolService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "人才库-通过id查询")
	@ApiOperation(value="人才库-通过id查询", notes="人才库-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FamilyTalentPool> queryById(@RequestParam(name="id",required=true) String id) {
		FamilyTalentPool familyTalentPool = familyTalentPoolService.getById(id);
		if(familyTalentPool==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(familyTalentPool);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param familyTalentPool
    */
    @RequiresPermissions("talentpool:family_talent_pool:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FamilyTalentPool familyTalentPool) {
        return super.exportXls(request, familyTalentPool, FamilyTalentPool.class, "人才库");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("talentpool:family_talent_pool:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<FamilyTalentPool> list = ExcelImportUtil.importExcel(file.getInputStream(), FamilyTalentPool.class, params);
				//update-begin-author:taoyan date:20190528 for:批量插入数据
				long start = System.currentTimeMillis();
				List<String> phoneList = list.stream().map(x -> x.getPhone()).collect(Collectors.toList());
				//查找已存在简历库的数据
				QueryWrapper<FamilyTalentPool> poolQueryWrapper = new QueryWrapper<>();
				poolQueryWrapper.lambda().in(FamilyTalentPool::getPhone,phoneList);
				//获取已存在手机号列表
				List<FamilyTalentPool> existList = familyTalentPoolService.list(poolQueryWrapper);
				List<String> existPhoneList = existList.stream().map(x -> x.getPhone()).collect(Collectors.toList());
				//查找不存在的手机号
				Set<FamilyTalentPool> notExistList = list.stream().filter(x -> !existPhoneList.contains(x.getPhone())).collect(Collectors.toSet());
				if(!CollectionUtils.isEmpty(notExistList)){
					familyTalentPoolService.saveBatch(notExistList);
				}
				//400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
				//1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
				log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				//update-end-author:taoyan date:20190528 for:批量插入数据
				return Result.ok("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				//update-begin-author:taoyan date:20211124 for: 导入数据重复增加提示
				String msg = e.getMessage();
				log.error(msg, e);
				if(msg!=null && msg.indexOf("Duplicate entry")>=0){
					return Result.error("文件导入失败:有重复数据！");
				}else{
					return Result.error("文件导入失败:" + e.getMessage());
				}
				//update-end-author:taoyan date:20211124 for: 导入数据重复增加提示
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.error("文件导入失败！");
    }





 }
