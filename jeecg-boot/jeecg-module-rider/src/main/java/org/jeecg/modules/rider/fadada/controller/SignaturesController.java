package org.jeecg.modules.rider.fadada.controller;

import com.fasc.open.api.v5_1.res.common.EUrlRes;
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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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

    @Autowired
    private IRiderCustomerService riderCustomerService;

    /**
    * 获取个人授权链接
    * @return
    */
   @ApiOperation(value="电子签管理-获取个人授权链接", notes="电子签管理-获取个人授权链接")
   @GetMapping(value = "/getCorpAuthUrl")
   public Result<EUrlRes> getCorpAuthUrl() {
       //	获取当前用户
       LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
       if (oConvertUtils.isEmpty(loginUser)) {
           return Result.error("请登录系统！");
       }
       RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
       if (oConvertUtils.isEmpty(riderCustomer)) {
           return Result.error("请注册用户！");
       }
       if(StringUtils.isNotEmpty(riderCustomer.getIdCard())){
           return Result.error("用户已经实名成功！");
       }
       return Result.ok(signaturesService.getUserAuthUrl(riderCustomer));
   }


    /**
     *   创建签署任务
     */
    @AutoLog(value = "创建签署任务")
    @ApiOperation(value="创建签署任务", notes="创建签署任务")
    @PostMapping(value = "/createSginTask")
    public Result<String> createSginTask(@RequestBody RiderInterview riderInterview) {
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
