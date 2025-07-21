package org.jeecg.modules.rider.fadada.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasc.open.api.v5_1.res.user.UserIdentityInfoRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.fadada.dto.UserAuthNotifyDto;
import org.jeecg.modules.rider.fadada.service.SignaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 电子签回调接口
 */
@Slf4j
@RestController
@RequestMapping("/signatures/callback")
public class SignturesNotifyController {

    @Autowired
    private IRiderCustomerService riderCustomerService;

    @Autowired
    private SignaturesService signaturesService;

    /**
     * 用户授权回调
     * @param req
     * @return
     */
    @RequestMapping("/userauth")
    public void userauth(HttpServletRequest req, UserAuthNotifyDto dto) {
        log.info("用户授权回调信息接收成功:{}", JSONObject.toJSONString(dto));
        try {
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
        } catch (Exception e) {
            log.error("用户授权回调处理异常", e);
        }
        try {
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
        } catch (Exception e) {
            log.error("用户授权回调处理异常", e);
        }

    }



    /**
     * 用户签署回调
     * @param req
     * @return
     */
    @RequestMapping("/usersign")
    public void usersign(HttpServletRequest req, UserAuthNotifyDto dto) {
        log.info("用户签署回调信息接收成功:{}", JSONObject.toJSONString(dto));
        try {
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
                log.info("用户签署失败");
            }
        } catch (Exception e) {
            log.error("用户签署回调处理异常", e);
        }
        try {
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
        } catch (Exception e) {
            log.error("用户签署回调处理异常", e);
        }

    }

}
