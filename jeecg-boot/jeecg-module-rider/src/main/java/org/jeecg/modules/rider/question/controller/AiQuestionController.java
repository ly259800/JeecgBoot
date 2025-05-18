package org.jeecg.modules.rider.question.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.question.entity.AiOption;
import org.jeecg.modules.rider.question.entity.AiQuestion;
import org.jeecg.modules.rider.question.vo.AiQuestionPage;
import org.jeecg.modules.rider.question.service.IAiQuestionService;
import org.jeecg.modules.rider.question.service.IAiOptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 问题
 * @Author: jeecg-boot
 * @Date:   2025-05-18
 * @Version: V1.0
 */
@Api(tags="问题")
@RestController
@RequestMapping("/question/aiQuestion")
@Slf4j
public class AiQuestionController {
	@Autowired
	private IAiQuestionService aiQuestionService;
	@Autowired
	private IAiOptionService aiOptionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param aiQuestion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "问题-分页列表查询")
	@ApiOperation(value="问题-分页列表查询", notes="问题-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AiQuestion>> queryPageList(AiQuestion aiQuestion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<AiQuestion> queryWrapper = QueryGenerator.initQueryWrapper(aiQuestion, req.getParameterMap());
		Page<AiQuestion> page = new Page<AiQuestion>(pageNo, pageSize);
		IPage<AiQuestion> pageList = aiQuestionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param aiQuestionPage
	 * @return
	 */
	@AutoLog(value = "问题-添加")
	@ApiOperation(value="问题-添加", notes="问题-添加")
    @RequiresPermissions("question:ai_question:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AiQuestionPage aiQuestionPage) {
		AiQuestion aiQuestion = new AiQuestion();
		BeanUtils.copyProperties(aiQuestionPage, aiQuestion);
		aiQuestionService.saveMain(aiQuestion, aiQuestionPage.getAiOptionList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param aiQuestionPage
	 * @return
	 */
	@AutoLog(value = "问题-编辑")
	@ApiOperation(value="问题-编辑", notes="问题-编辑")
    @RequiresPermissions("question:ai_question:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AiQuestionPage aiQuestionPage) {
		AiQuestion aiQuestion = new AiQuestion();
		BeanUtils.copyProperties(aiQuestionPage, aiQuestion);
		AiQuestion aiQuestionEntity = aiQuestionService.getById(aiQuestion.getId());
		if(aiQuestionEntity==null) {
			return Result.error("未找到对应数据");
		}
		aiQuestionService.updateMain(aiQuestion, aiQuestionPage.getAiOptionList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "问题-通过id删除")
	@ApiOperation(value="问题-通过id删除", notes="问题-通过id删除")
    @RequiresPermissions("question:ai_question:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		aiQuestionService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "问题-批量删除")
	@ApiOperation(value="问题-批量删除", notes="问题-批量删除")
    @RequiresPermissions("question:ai_question:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.aiQuestionService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "问题-通过id查询")
	@ApiOperation(value="问题-通过id查询", notes="问题-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AiQuestion> queryById(@RequestParam(name="id",required=true) String id) {
		AiQuestion aiQuestion = aiQuestionService.getById(id);
		if(aiQuestion==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(aiQuestion);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "选项通过主表ID查询")
	@ApiOperation(value="选项主表ID查询", notes="选项-通主表ID查询")
	@GetMapping(value = "/queryAiOptionByMainId")
	public Result<List<AiOption>> queryAiOptionListByMainId(@RequestParam(name="id",required=true) String id) {
		List<AiOption> aiOptionList = aiOptionService.selectByMainId(id);
		return Result.OK(aiOptionList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param aiQuestion
    */
    @RequiresPermissions("question:ai_question:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AiQuestion aiQuestion) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<AiQuestion> queryWrapper = QueryGenerator.initQueryWrapper(aiQuestion, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<AiQuestion> aiQuestionList = aiQuestionService.list(queryWrapper);

      // Step.3 组装pageList
      List<AiQuestionPage> pageList = new ArrayList<AiQuestionPage>();
      for (AiQuestion main : aiQuestionList) {
          AiQuestionPage vo = new AiQuestionPage();
          BeanUtils.copyProperties(main, vo);
          List<AiOption> aiOptionList = aiOptionService.selectByMainId(main.getId());
          vo.setAiOptionList(aiOptionList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "问题列表");
      mv.addObject(NormalExcelConstants.CLASS, AiQuestionPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("问题数据", "导出人:"+sysUser.getRealname(), "问题"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("question:ai_question:importExcel")
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
              List<AiQuestionPage> list = ExcelImportUtil.importExcel(file.getInputStream(), AiQuestionPage.class, params);
              for (AiQuestionPage page : list) {
                  AiQuestion po = new AiQuestion();
                  BeanUtils.copyProperties(page, po);
                  aiQuestionService.saveMain(po, page.getAiOptionList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
