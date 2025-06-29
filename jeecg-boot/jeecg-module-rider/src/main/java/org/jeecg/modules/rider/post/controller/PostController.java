package org.jeecg.modules.rider.post.controller;

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

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.service.IPostService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysCategory;
import org.jeecg.modules.system.entity.SysThirdAccount;
import org.jeecg.modules.system.service.ISysCategoryService;
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
 * @Description: 岗位管理
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Api(tags="岗位管理")
@RestController
@RequestMapping("/post/post")
@Slf4j
public class PostController extends JeecgController<Post, IPostService> {
	@Autowired
	private IPostService postService;

	@Autowired
	private ISysCategoryService sysCategoryService;

	
	/**
	 * 分页列表查询
	 *
	 * @param post
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "岗位管理-分页列表查询")
	@ApiOperation(value="岗位管理-分页列表查询", notes="岗位管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Post>> queryPageList(Post post,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<Post> queryWrapper = QueryGenerator.initQueryWrapper(post, req.getParameterMap());
		Page<Post> page = new Page<Post>(pageNo, pageSize);
		IPage<Post> pageList = postService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 列表查询
	  *
	  * @param post
	  * @param req
	  * @return
	  */
	 @ApiOperation(value="岗位管理-小程序列表查询", notes="岗位管理-小程序列表查询")
	 @GetMapping(value = "/listForApp")
	 public Result<List<Post>> listForApp(Post post,
											  HttpServletRequest req) {
		 QueryWrapper<Post> queryWrapper = QueryGenerator.initQueryWrapper(post, req.getParameterMap());
		 //只查询已发布的岗位
		 queryWrapper.lambda().eq(Post::getPublishStatus, 1);
		 queryWrapper.lambda().orderByDesc(Post::getCreateTime);
		 List<Post> postList = postService.list(queryWrapper);
		 return Result.OK(postList);
	 }

	
	/**
	 *   添加
	 *
	 * @param post
	 * @return
	 */
	@AutoLog(value = "岗位管理-添加")
	@ApiOperation(value="岗位管理-添加", notes="岗位管理-添加")
	@RequiresPermissions("post:family_post:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Post post) {
		if(post.getCategoryId()!=null){
			SysCategory category = sysCategoryService.getById(post.getCategoryId());
			post.setCategoryName(category.getName());
		}
		postService.save(post);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param post
	 * @return
	 */
	@AutoLog(value = "岗位管理-编辑")
	@ApiOperation(value="岗位管理-编辑", notes="岗位管理-编辑")
	@RequiresPermissions("post:family_post:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Post post) {
		if(post.getCategoryId()!=null){
			SysCategory category = sysCategoryService.getById(post.getCategoryId());
			post.setCategoryName(category.getName());
		}
		postService.updateById(post);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "岗位管理-通过id删除")
	@ApiOperation(value="岗位管理-通过id删除", notes="岗位管理-通过id删除")
	@RequiresPermissions("post:family_post:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		postService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "岗位管理-批量删除")
	@ApiOperation(value="岗位管理-批量删除", notes="岗位管理-批量删除")
	@RequiresPermissions("post:family_post:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.postService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	 /**
	  *  批量发布
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "岗位管理-批量发布")
	 @ApiOperation(value="岗位管理-批量发布", notes="岗位管理-批量发布")
	 @RequiresPermissions("post:family_post:publishBatch")
	 @PostMapping(value = "/publishBatch")
	 public Result<String> publishBatch(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.postService.publishBatch(ids , 1);
		 return Result.OK("批量发布成功!");
	 }

	 /**
	  *  批量下架
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "岗位管理-批量下架")
	 @ApiOperation(value="岗位管理-批量下架", notes="岗位管理-批量下架")
	 @RequiresPermissions("post:family_post:cancelBatch")
	 @PostMapping(value = "/cancelBatch")
	 public Result<String> cancelBatch(@RequestParam(name="ids",required=true) String ids) {
		 if(StringUtils.isEmpty(ids)){
			 return Result.error("请选择行数据!");
		 }
		 this.postService.publishBatch(ids , 0);
		 return Result.OK("批量下架成功!");
	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "岗位管理-通过id查询")
	@ApiOperation(value="岗位管理-通过id查询", notes="岗位管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Post> queryById(@RequestParam(name="id",required=true) String id) {
		Post post = postService.getById(id);
		if(post==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(post);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param post
    */
    @RequiresPermissions("post:family_post:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Post post) {
        return super.exportXls(request, post, Post.class, "岗位管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("post:family_post:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Post.class);
    }

}
