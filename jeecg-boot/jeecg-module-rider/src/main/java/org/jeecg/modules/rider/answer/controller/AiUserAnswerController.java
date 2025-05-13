package org.jeecg.modules.rider.answer.controller;

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
import org.jeecg.modules.rider.answer.entity.AiUserAnswer;
import org.jeecg.modules.rider.answer.service.IAiUserAnswerService;

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
 * @Description: 用户答复
 * @Author: jeecg-boot
 * @Date:   2025-05-13
 * @Version: V1.0
 */
@Api(tags="用户答复")
@RestController
@RequestMapping("/answer/aiUserAnswer")
@Slf4j
public class AiUserAnswerController extends JeecgController<AiUserAnswer, IAiUserAnswerService> {
	@Autowired
	private IAiUserAnswerService aiUserAnswerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param aiUserAnswer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户答复-分页列表查询")
	@ApiOperation(value="用户答复-分页列表查询", notes="用户答复-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AiUserAnswer>> queryPageList(AiUserAnswer aiUserAnswer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<AiUserAnswer> queryWrapper = QueryGenerator.initQueryWrapper(aiUserAnswer, req.getParameterMap());
		Page<AiUserAnswer> page = new Page<AiUserAnswer>(pageNo, pageSize);
		IPage<AiUserAnswer> pageList = aiUserAnswerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param aiUserAnswer
	 * @return
	 */
	@AutoLog(value = "用户答复-添加")
	@ApiOperation(value="用户答复-添加", notes="用户答复-添加")
	@RequiresPermissions("answer:ai_user_answer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AiUserAnswer aiUserAnswer) {
		aiUserAnswerService.save(aiUserAnswer);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param aiUserAnswer
	 * @return
	 */
	@AutoLog(value = "用户答复-编辑")
	@ApiOperation(value="用户答复-编辑", notes="用户答复-编辑")
	@RequiresPermissions("answer:ai_user_answer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AiUserAnswer aiUserAnswer) {
		aiUserAnswerService.updateById(aiUserAnswer);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户答复-通过id删除")
	@ApiOperation(value="用户答复-通过id删除", notes="用户答复-通过id删除")
	@RequiresPermissions("answer:ai_user_answer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		aiUserAnswerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户答复-批量删除")
	@ApiOperation(value="用户答复-批量删除", notes="用户答复-批量删除")
	@RequiresPermissions("answer:ai_user_answer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.aiUserAnswerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户答复-通过id查询")
	@ApiOperation(value="用户答复-通过id查询", notes="用户答复-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AiUserAnswer> queryById(@RequestParam(name="id",required=true) String id) {
		AiUserAnswer aiUserAnswer = aiUserAnswerService.getById(id);
		if(aiUserAnswer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(aiUserAnswer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param aiUserAnswer
    */
    @RequiresPermissions("answer:ai_user_answer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AiUserAnswer aiUserAnswer) {
        return super.exportXls(request, aiUserAnswer, AiUserAnswer.class, "用户答复");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("answer:ai_user_answer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AiUserAnswer.class);
    }

}
