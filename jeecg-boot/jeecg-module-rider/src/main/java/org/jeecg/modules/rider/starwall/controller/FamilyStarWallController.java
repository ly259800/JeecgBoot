package org.jeecg.modules.rider.starwall.controller;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.course.entity.VideoCourse;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.likerecord.entity.FamilyLikeRecord;
import org.jeecg.modules.rider.likerecord.service.IFamilyLikeRecordService;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.params.service.IRiderParamsService;
import org.jeecg.modules.rider.starwall.entity.FamilyStarWall;
import org.jeecg.modules.rider.starwall.service.IFamilyStarWallService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
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
 * @Description: 首页星光墙
 * @Author: jeecg-boot
 * @Date:   2025-10-22
 * @Version: V1.0
 */
@Api(tags="首页星光墙")
@RestController
@RequestMapping("/starwall/familyStarWall")
@Slf4j
public class FamilyStarWallController extends JeecgController<FamilyStarWall, IFamilyStarWallService> {
	@Autowired
	private IFamilyStarWallService familyStarWallService;
	@Autowired
	private IFamilyLikeRecordService familyLikeRecordService;

	@Autowired
	private IRiderCustomerService riderCustomerService;

	@Autowired
	private IRiderParamsService riderParamsService;

	 @Autowired
	 private TransactionTemplate transactionTemplate;

	 @Autowired
	 private RedisUtil redisUtil;
	
	/**
	 * 分页列表查询
	 *
	 * @param familyStarWall
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "首页星光墙-分页列表查询")
	@ApiOperation(value="首页星光墙-分页列表查询", notes="首页星光墙-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FamilyStarWall>> queryPageList(FamilyStarWall familyStarWall,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<FamilyStarWall> queryWrapper = QueryGenerator.initQueryWrapper(familyStarWall, req.getParameterMap());
		Page<FamilyStarWall> page = new Page<FamilyStarWall>(pageNo, pageSize);
		IPage<FamilyStarWall> pageList = familyStarWallService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 列表查询
	  *
	  * @param familyStarWall
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="星光墙-列表查询", notes="星光墙-列表查询")
	 @GetMapping(value = "/listForApp")
	 public Result<List<FamilyStarWall>> listForApp(FamilyStarWall familyStarWall,
												 HttpServletRequest req) {
		 // 自定义查询规则
		 Map<String, QueryRuleEnum> customeRuleMap = new HashMap<>();
		 QueryWrapper<FamilyStarWall> queryWrapper = QueryGenerator.initQueryWrapper(familyStarWall, req.getParameterMap(),customeRuleMap);
		 queryWrapper.lambda().orderByDesc(FamilyStarWall::getLikeCnt);
		 List<FamilyStarWall> starWallList = familyStarWallService.list(queryWrapper);
		 return Result.OK(starWallList);
	 }

	 /**
	  *   点赞
	  *
	  * @param familyStarWall
	  * @return
	  */
	 @AutoLog(value = "首页星光墙-点赞")
	 @ApiOperation(value="首页星光墙-点赞", notes="首页星光墙-点赞")
	 @PostMapping(value = "/like")
	 public Result<String> like(@RequestBody FamilyStarWall familyStarWall) {
		 //获取当前用户
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 if (oConvertUtils.isEmpty(loginUser)) {
			 return Result.error("请登录系统！");
		 }
		 RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
		 if (oConvertUtils.isEmpty(riderCustomer)) {
			 return Result.error("请注册用户！");
		 }
		 // 构建防重复提交的key（用户ID+订单关键信息）
		 String lockKey = "starwall:like:" + familyStarWall.getId() + ":" + riderCustomer.getId();
		 if (redisUtil.hasKey(lockKey)) {
			 throw new JeecgBootException("操作太频繁，请勿重复提交");
		 }
		 //尝试获取锁，3秒内不允许重复提交
		 redisUtil.set(lockKey, "1", 3L);
		 //获取当天时间
		 Date date = Date.from(DateUtils.getLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		 QueryWrapper<FamilyLikeRecord> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(FamilyLikeRecord::getStarId, familyStarWall.getId())
				 .eq(FamilyLikeRecord::getCustomerId, riderCustomer.getId())
				 .gt(FamilyLikeRecord::getCreateTime, date);
		 long count = familyLikeRecordService.count(queryWrapper);
		 if (count > 0) {
			 return Result.error("您今天已经点赞过这条记录了！");
		 }
		 Integer todayNum = 10;
		 RiderParams star_wall_like_num = riderParamsService.getByCode("STAR_WALL_LIKE_NUM");
		 if (oConvertUtils.isNotEmpty(star_wall_like_num)) {
			 todayNum = Integer.parseInt(star_wall_like_num.getParamValue());
		 }
		 queryWrapper.clear();
		 queryWrapper.lambda().eq(FamilyLikeRecord::getCustomerId, riderCustomer.getId())
				 .gt(FamilyLikeRecord::getCreateTime, date);
		 count = familyLikeRecordService.count(queryWrapper);
		 if (count > todayNum) {
			 return Result.error("您今天点赞已经超过"+todayNum+"次了，不能再点赞！");
		 }
		 transactionTemplate.execute(new TransactionCallback<Void>() {
			 @Override
			 public Void doInTransaction(TransactionStatus status) {
				 try {
					//点赞数加1
					 familyStarWallService.addLikeCnt(familyStarWall.getId(), 1);
					 //添加点赞记录
					 FamilyLikeRecord likeRecord = new FamilyLikeRecord();
					 likeRecord.setCustomerId(riderCustomer.getId());
					 likeRecord.setStarId(familyStarWall.getId());
					 familyLikeRecordService.save(likeRecord);
				 }catch (Exception e){
					 //抛出异常，事务回滚
					 throw e;
				 }
				 return null;
			 }
		 });
		 return Result.OK("点赞成功！");
	 }

	
	/**
	 *   添加
	 *
	 * @param familyStarWall
	 * @return
	 */
	@AutoLog(value = "首页星光墙-添加")
	@ApiOperation(value="首页星光墙-添加", notes="首页星光墙-添加")
	@RequiresPermissions("starwall:family_star_wall:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FamilyStarWall familyStarWall) {
		familyStarWallService.save(familyStarWall);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param familyStarWall
	 * @return
	 */
	@AutoLog(value = "首页星光墙-编辑")
	@ApiOperation(value="首页星光墙-编辑", notes="首页星光墙-编辑")
	@RequiresPermissions("starwall:family_star_wall:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FamilyStarWall familyStarWall) {
		familyStarWallService.updateById(familyStarWall);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "首页星光墙-通过id删除")
	@ApiOperation(value="首页星光墙-通过id删除", notes="首页星光墙-通过id删除")
	@RequiresPermissions("starwall:family_star_wall:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		familyStarWallService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "首页星光墙-批量删除")
	@ApiOperation(value="首页星光墙-批量删除", notes="首页星光墙-批量删除")
	@RequiresPermissions("starwall:family_star_wall:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.familyStarWallService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "首页星光墙-通过id查询")
	@ApiOperation(value="首页星光墙-通过id查询", notes="首页星光墙-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FamilyStarWall> queryById(@RequestParam(name="id",required=true) String id) {
		FamilyStarWall familyStarWall = familyStarWallService.getById(id);
		if(familyStarWall==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(familyStarWall);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param familyStarWall
    */
    @RequiresPermissions("starwall:family_star_wall:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FamilyStarWall familyStarWall) {
        return super.exportXls(request, familyStarWall, FamilyStarWall.class, "首页星光墙");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("starwall:family_star_wall:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FamilyStarWall.class);
    }

}
