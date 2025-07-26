package org.jeecg.modules.rider.fadada.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasc.open.api.utils.crypt.FddCryptUtil;
import com.fasc.open.api.v5_1.res.user.UserIdentityInfoRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.fadada.dto.UserAuthNotifyDto;
import org.jeecg.modules.rider.fadada.dto.UserSignNotifyDto;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.jeecg.modules.rider.interview.entity.RiderInterview;
import org.jeecg.modules.rider.interview.service.IRiderInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 电子签回调接口
 */
@Slf4j
@RestController
@RequestMapping("/signatures")
public class SignturesNotifyController {

    @Autowired
    private IRiderCustomerService riderCustomerService;


    @Autowired
    private IRiderInterviewService riderInterviewService;

    @Autowired
    private SignaturesService signaturesService;


    @Value("${fadada.appSecret}")
    private String appSecret;

    /**
     * 法大大回调
     * @return
     */
    @RequestMapping("/callback")
    public String userauth(@RequestHeader HttpHeaders headers,
                           @RequestParam("bizContent") String bizContent) {
        log.info("用户授权回调信息接收成功:{}", JSONObject.toJSONString(bizContent));
        try {
            //获取请求头参数
            String appId = headers.getFirst("X-FASC-App-Id");
            String signType = headers.getFirst("X-FASC-Sign-Type");
            String sign = headers.getFirst("X-FASC-Sign");
            String timestamp = headers.getFirst("X-FASC-Timestamp");
            //事件名称，开发者可以根据不同事件名称去解析bizContent的值，实现不同的逻辑
            String event = headers.getFirst("X-FASC-Event");
            String nonce = headers.getFirst("X-FASC-Nonce");
            //验签
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("X-FASC-App-Id", appId);
            paramMap.put("X-FASC-Sign-Type", "HMAC-SHA256");
            paramMap.put("X-FASC-Timestamp", timestamp);
            paramMap.put("X-FASC-Nonce", nonce);
            paramMap.put("X-FASC-Event", event);
            paramMap.put("bizContent", bizContent);
            //参数排序，ascii码排序
            String sortParam = FddCryptUtil.sortParameters(paramMap);
            //生成签名后可以进行校验
            String signature =  FddCryptUtil.sign(sortParam, timestamp, appSecret);
            if(!signature.equals(sign)) {
                if(Objects.equals(event,"user-authorize")){
                    userAuth(JSONObject.parseObject(bizContent, UserAuthNotifyDto.class));
                } else if(Objects.equals(event,"sign-task-finished")){
                    usersign(JSONObject.parseObject(bizContent, UserSignNotifyDto.class));
                }
                return "{\"msg\":\"success\"}";
            }
        } catch (Exception e){
            log.error("法大大回调处理异常", e);
        }
        return "{\"msg\":\"success\"}";
    }

    private void userAuth(UserAuthNotifyDto dto) {
        if(StringUtils.isNotEmpty(dto.getClientUserId()) && StringUtils.isNotEmpty(dto.getOpenUserId())){
            RiderCustomer riderCustomer = riderCustomerService.getById(dto.getClientUserId());
            //更新法大大的openId
            if(Objects.nonNull(riderCustomer) ){
                RiderCustomer updateCustomer = new RiderCustomer();
                updateCustomer.setId(riderCustomer.getId());
                updateCustomer.setOpenUserId(dto.getOpenUserId());
                riderCustomerService.updateById(updateCustomer);
            }
        } else {
            log.info("用户授权失败");
        }
        //授权成功，获取身份证信息
        if(Objects.equals(dto.getAuthResult(), "success")){
            UserIdentityInfoRes identityInfo = signaturesService.getIdentityInfo(dto.getOpenUserId());
            //已经实名成功
            if(Objects.nonNull(identityInfo) && Objects.equals(identityInfo.getIdentStatus(), "identified")){
                RiderCustomer riderCustomer = riderCustomerService.getById(dto.getClientUserId());
                if(Objects.nonNull(riderCustomer)){
                    RiderCustomer updateCustomer = new RiderCustomer();
                    updateCustomer.setId(riderCustomer.getId());
                    updateCustomer.setIdCard(identityInfo.getUserIdentInfo().getIdentNo());
                    updateCustomer.setName(identityInfo.getUserIdentInfo().getUserName());
                    riderCustomerService.updateById(updateCustomer);
                }
            }
        }
    }

    private void usersign(UserSignNotifyDto dto) {
        //签署任务已完成
        if(Objects.equals("task_finished", dto.getSignTaskStatus())){
            RiderInterview interview = riderInterviewService.getById(dto.getTransReferenceId());
            //更新签署成功状态
            if(Objects.nonNull(interview) ){
                RiderInterview updateInterview = new RiderInterview();
                updateInterview.setId(interview.getId());
                updateInterview.setSignStatus(1);
                riderInterviewService.updateById(updateInterview);
            }
        } else {
            log.info("用户签署失败");
        }
    }

}
