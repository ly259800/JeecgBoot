package org.jeecg.modules.rider.course.controller;

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
import org.jeecg.modules.rider.course.entity.VideoUnlockRecord;
import org.jeecg.modules.rider.course.service.IVideoUnlockRecordService;

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
 * @Description: 视频解锁记录表
 * @Author: jeecg-boot
 * @Date:   2025-06-29
 * @Version: V1.0
 */
@Api(tags="视频解锁记录表")
@RestController
@RequestMapping("/course/videoUnlockRecord")
@Slf4j
public class VideoUnlockRecordController extends JeecgController<VideoUnlockRecord, IVideoUnlockRecordService> {
	@Autowired
	private IVideoUnlockRecordService videoUnlockRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param videoUnlockRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "视频解锁记录表-分页列表查询")
	@ApiOperation(value="视频解锁记录表-分页列表查询", notes="视频解锁记录表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<VideoUnlockRecord>> queryPageList(VideoUnlockRecord videoUnlockRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        QueryWrapper<VideoUnlockRecord> queryWrapper = QueryGenerator.initQueryWrapper(videoUnlockRecord, req.getParameterMap());
		Page<VideoUnlockRecord> page = new Page<VideoUnlockRecord>(pageNo, pageSize);
		IPage<VideoUnlockRecord> pageList = videoUnlockRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param videoUnlockRecord
	 * @return
	 */
	@AutoLog(value = "视频解锁记录表-添加")
	@ApiOperation(value="视频解锁记录表-添加", notes="视频解锁记录表-添加")
	@RequiresPermissions("course:video_unlock_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody VideoUnlockRecord videoUnlockRecord) {
		videoUnlockRecordService.save(videoUnlockRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param videoUnlockRecord
	 * @return
	 */
	@AutoLog(value = "视频解锁记录表-编辑")
	@ApiOperation(value="视频解锁记录表-编辑", notes="视频解锁记录表-编辑")
	@RequiresPermissions("course:video_unlock_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody VideoUnlockRecord videoUnlockRecord) {
		videoUnlockRecordService.updateById(videoUnlockRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "视频解锁记录表-通过id删除")
	@ApiOperation(value="视频解锁记录表-通过id删除", notes="视频解锁记录表-通过id删除")
	@RequiresPermissions("course:video_unlock_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		videoUnlockRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "视频解锁记录表-批量删除")
	@ApiOperation(value="视频解锁记录表-批量删除", notes="视频解锁记录表-批量删除")
	@RequiresPermissions("course:video_unlock_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.videoUnlockRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "视频解锁记录表-通过id查询")
	@ApiOperation(value="视频解锁记录表-通过id查询", notes="视频解锁记录表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<VideoUnlockRecord> queryById(@RequestParam(name="id",required=true) String id) {
		VideoUnlockRecord videoUnlockRecord = videoUnlockRecordService.getById(id);
		if(videoUnlockRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(videoUnlockRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param videoUnlockRecord
    */
    @RequiresPermissions("course:video_unlock_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VideoUnlockRecord videoUnlockRecord) {
        return super.exportXls(request, videoUnlockRecord, VideoUnlockRecord.class, "视频解锁记录表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("course:video_unlock_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, VideoUnlockRecord.class);
    }

}
