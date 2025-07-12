package org.jeecg.modules.rider.fadada.controller;

import com.fasc.open.api.v5_1.res.common.ECorpAuthUrlRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @Description: 电子签管理
* @Author: jeecg-boot
* @Date:   2025-07-12
* @Version: V1.0
*/
@Api(tags="电子签管理")
@RestController
@RequestMapping("/signatures")
@Slf4j
public class SignaturesController{

    @Autowired
    private SignaturesService signaturesService;

    /**
    * 获取企业授权链接
    * @return
    */
   @ApiOperation(value="电子签管理-获取企业授权链接", notes="电子签管理-获取企业授权链接")
   @GetMapping(value = "/getCorpAuthUrl")
   public Result<ECorpAuthUrlRes> getCorpAuthUrl() {
       return Result.ok(signaturesService.getCorpAuthUrl());

   }


    /**
     *   站点申请
     */
    @AutoLog(value = "站点申请")
    @ApiOperation(value="站点申请", notes="站点申请")
    @RequiresPermissions("interview:rider_interview:add")
    @PostMapping(value = "/siteAdd")
    public Result<String> siteAdd(@RequestBody RiderInterview riderInterview) {
        if(StringUtils.isEmpty(riderInterview.getSiteId()) || StringUtils.isEmpty(riderInterview.getSiteName())){
            return Result.error("站点不能为空");
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(Objects.isNull(sysUser)){
            throw new JeecgBootException("请先登录");
        }
        return Result.OK("登记成功！");
    }

}
