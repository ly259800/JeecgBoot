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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.post.entity.PostDetail;
import org.jeecg.modules.rider.post.service.IPostDetailService;

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
 * @Description: 岗位详情
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Api(tags="岗位详情")
@RestController
@RequestMapping("/post/postDetail")
@Slf4j
public class PostDetailController extends JeecgController<PostDetail, IPostDetailService> {
	@Autowired
	private IPostDetailService postDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param postDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "岗位详情-分页列表查询")
	@ApiOperation(value="岗位详情-分页列表查询", notes="岗位详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PostDetail>> queryPageList(PostDetail postDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<PostDetail> queryWrapper = QueryGenerator.initQueryWrapper(postDetail, req.getParameterMap());
		Page<PostDetail> page = new Page<PostDetail>(pageNo, pageSize);
		IPage<PostDetail> pageList = postDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param postDetail
	 * @return
	 */
	@AutoLog(value = "岗位详情-添加")
	@ApiOperation(value="岗位详情-添加", notes="岗位详情-添加")
	@RequiresPermissions("post:family_post_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PostDetail postDetail) {
		postDetailService.save(postDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param postDetail
	 * @return
	 */
	@AutoLog(value = "岗位详情-编辑")
	@ApiOperation(value="岗位详情-编辑", notes="岗位详情-编辑")
	@RequiresPermissions("post:family_post_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PostDetail postDetail) {
		postDetailService.updateById(postDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "岗位详情-通过id删除")
	@ApiOperation(value="岗位详情-通过id删除", notes="岗位详情-通过id删除")
	@RequiresPermissions("post:family_post_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		postDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "岗位详情-批量删除")
	@ApiOperation(value="岗位详情-批量删除", notes="岗位详情-批量删除")
	@RequiresPermissions("post:family_post_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.postDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "岗位详情-通过id查询")
	@ApiOperation(value="岗位详情-通过id查询", notes="岗位详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PostDetail> queryById(@RequestParam(name="id",required=true) String id) {
		PostDetail postDetail = postDetailService.getById(id);
		if(postDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(postDetail);
	}

}
