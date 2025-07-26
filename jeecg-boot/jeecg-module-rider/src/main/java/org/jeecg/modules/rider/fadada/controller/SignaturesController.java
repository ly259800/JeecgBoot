package org.jeecg.modules.rider.fadada.controller;

import com.fasc.open.api.v5_1.res.common.EUrlRes;
import com.fasc.open.api.v5_1.res.signtask.SignTaskActorGetUrlRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.jeecg.modules.rider.interview.dto.RiderInterviewDTO;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.jeecg.modules.rider.post.entity.Post;
import org.jeecg.modules.rider.post.service.IPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private IRiderInterviewService riderInterviewService;

    @Autowired
    private IPostService postService;


    @Value("${fadada.templateId}")
    private String templateId;


    @Value("${fadada.partnerTemplateId}")
    private String partnerTemplateId;

    /**
    * 获取个人授权链接
    * @return
    */
   @ApiOperation(value="电子签管理-获取个人授权链接", notes="电子签管理-获取个人授权链接")
   @GetMapping(value = "/getCorpAuthUrl")
   public Result<EUrlRes> getCorpAuthUrl(@RequestParam("url") String url) {
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
       return Result.ok(signaturesService.getUserAuthUrl(riderCustomer,url));
   }


    /**
     * 个人授权解除绑定
     * @return
     */
    @ApiOperation(value="电子签管理-个人授权解除绑定", notes="电子签管理-个人授权解除绑定")
    @GetMapping(value = "/userUnbind")
    public Result userUnbind() {
        //	获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
        if (oConvertUtils.isEmpty(riderCustomer)) {
            return Result.error("请注册用户！");
        }
        signaturesService.userUnbind(riderCustomer.getOpenUserId());
        //更新用户的身份证号
        riderCustomer.setIdCard("");
        riderCustomerService.updateById(riderCustomer);
        return Result.ok();
    }


    /**
     *   创建签署任务
     */
    @AutoLog(value = "创建签署任务")
    @ApiOperation(value="创建签署任务", notes="创建签署任务")
    @PostMapping(value = "/createSginTask")
    public Result<SignTaskActorGetUrlRes> createSginTask(@RequestBody RiderInterview riderInterview) {
        //	获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
        if (oConvertUtils.isEmpty(riderCustomer)) {
            return Result.error("请注册用户！");
        }
        if(StringUtils.isEmpty(riderCustomer.getIdCard())){
            Result result = new Result();
            result.setCode(10080);
            result.setMessage("用户未实名,请先实名认证!");
            return result;
        }
        if(StringUtils.isEmpty(riderInterview.getId())){
            return Result.error("请选择报名记录！");
        }
        RiderInterview interview = riderInterviewService.getById(riderInterview.getId());
        if(Objects.isNull(interview)){
            return Result.error("该报名记录不存在！");
        }
        if(Objects.equals(interview.getSignStatus() , 1)){
            return Result.error("该报名记录已签署！");
        }
        if(StringUtils.isNotEmpty(interview.getSignTaskId())){
            // 已经签署过合同，直接返回链接
            SignTaskActorGetUrlRes actorGetUrlRes = signaturesService.getActorUrlBySignTaskId(templateId, interview.getSignTaskId(), riderCustomer.getId(),false);
            return Result.OK(actorGetUrlRes);
        }
        Post post = postService.getById(interview.getSiteId());
        if(post == null){
            return Result.error("该报名记录岗位不存在！");
        }
        RiderInterviewDTO interviewDTO = new RiderInterviewDTO();
        BeanUtils.copyProperties(interview, interviewDTO);
        interviewDTO.setContacts(post.getContacts());
        //若价格为0，则取岗位的价格
        if(Objects.isNull(interviewDTO.getPrice()) || interviewDTO.getPrice().compareTo(BigDecimal.ZERO)<=0){
            interviewDTO.setPrice(post.getPrice());
        }
        SignTaskActorGetUrlRes res = signaturesService.createWithTemplate(templateId, riderCustomer, interviewDTO);
        //更新报名记录的合同
        if(StringUtils.isNotEmpty(interviewDTO.getSignTaskId())){
            RiderInterview update = new RiderInterview();
            update.setSignTaskId(interviewDTO.getSignTaskId());
            update.setId(interview.getId());
            riderInterviewService.updateById(update);
        }
        return Result.OK(res);
    }


    /**
     *   创建主理人协议签署任务
     */
    @AutoLog(value = "创建主理人协议签署任务")
    @ApiOperation(value="创建主理人协议签署任务", notes="创建主理人协议签署任务")
    @PostMapping(value = "/createPartnerSginTask")
    public Result<SignTaskActorGetUrlRes> createPartnerSginTask() {
        //	获取当前用户
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (oConvertUtils.isEmpty(loginUser)) {
            return Result.error("请登录系统！");
        }
        RiderCustomer riderCustomer = riderCustomerService.getByPhone(loginUser.getPhone());
        if (oConvertUtils.isEmpty(riderCustomer)) {
            return Result.error("请注册用户！");
        }
        if(StringUtils.isEmpty(riderCustomer.getIdCard())){
            Result result = new Result();
            result.setCode(10080);
            result.setMessage("用户未实名,请先实名认证!");
            return result;
        }
        if(StringUtils.isNotEmpty(riderCustomer.getSignTaskId())){
            // 已经签署过合同，直接返回链接
            SignTaskActorGetUrlRes actorGetUrlRes = signaturesService.getActorUrlBySignTaskId(partnerTemplateId, riderCustomer.getSignTaskId(), riderCustomer.getId(),true);
            return Result.OK(actorGetUrlRes);
        }
        SignTaskActorGetUrlRes res = signaturesService.createWithPartnerTemplate(partnerTemplateId, riderCustomer);
        //更新报名记录的合同
        if(StringUtils.isNotEmpty(riderCustomer.getSignTaskId())){
            RiderCustomer update = new RiderCustomer();
            update.setSignTaskId(riderCustomer.getSignTaskId());
            update.setId(riderCustomer.getId());
            riderCustomerService.updateById(update);
        }
        return Result.OK(res);
    }


}
