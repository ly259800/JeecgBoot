package org.jeecg.modules.rider.security;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.rider.pay.service.WeChatPayApiInvoke;
import org.jeecg.modules.rider.pay.util.Result;
import org.jeecg.modules.rider.security.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;


/**
 * 登录
 */
@RestController
@Slf4j
public class WxLoginController {
    @Resource
    private WeChatPayApiInvoke weChatPayApiInvoke;

    @Autowired
    private RedisUtil redisUtil;

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @PostMapping("wx/login")
    @ApiOperation(value = "获取openid")
    public Result WxLogin(HttpServletRequest request, @RequestBody WxLoginDTO login) {
        String code = login.getCode();
        JSONObject tempResult = new JSONObject();
        if (StringUtils.isEmpty(code)) {
            throw new JeecgBootException("参数不能为空");
        }
        WxResultDTO resultDTO = weChatPayApiInvoke.getOpenidByCode(login);
        //接口返回成功
        if (Objects.nonNull(resultDTO) && StringUtils.isNotEmpty(resultDTO.getOpenid())) {
            String key = UUID.randomUUID().toString();
            tempResult.put("sessionKey", key);
            redisUtil.set(key, resultDTO);
        } else {
            log.error("微信接口调用失败:", resultDTO.getErrmsg());
            throw new JeecgBootException("微信接口调用失败,请联系客服!");
        }
        return new Result().ok(tempResult);
    }


    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @PostMapping("user/login")
    @ApiOperation(value = "微信登录")
    public Result userLogin(HttpServletRequest request, @RequestBody WxLoginDTO login) {
        String openId = "";
        if (StringUtils.isNotEmpty(login.getSessionKey())) {
            WxResultDTO resultDTO = (WxResultDTO) redisUtil.get(login.getSessionKey());
            if (Objects.isNull(resultDTO)) {
                Result result = new Result();
                result.setCode(10070);
                result.setMsg("sessionKey已失效,请重新授权!");
                return result;
            }
            openId = resultDTO.getOpenid();
        }

        if (StringUtils.isEmpty(openId)) {
            throw new JeecgBootException("鉴权不能为空!");
        }
        //根据openid查找用户
        /*SysUserDTO userDTO = sysUserService.getByOpenId(openId);
        if (Objects.nonNull(userDTO)) {
            Result token = sysUserOpenIdService.createToken(userDTO.getId());
            if (Objects.isNull(userDTO.getTenantCode())) {
                token.setCode(-3);
                token.setMsg("当前未加入任何企业");
                return token;
            }
            //用户已存在，直接返回该用户
            return token;
        } else {
            Result res = new Result();
            res.setCode(201);
            res.setMsg("微信未绑定用户!");
            return res;
        }*/
        return new Result();
    }
/*
    @PostMapping("user/bindLogin")
    @ApiOperation(value = "用户绑定微信登录")
    @Transactional(rollbackFor = Exception.class)
    public Result bindLogin(HttpServletRequest request, @RequestBody WxBindDTO bindDTO) {
        SysLogLoginEntity log = getSysLogLoginEntity(2, request, sourceUtils);
        return sysUserOpenIdService.binding(log, bindDTO);
    }

    @PostMapping("user/inviteWayJoin")
    @ApiOperation(value = "邀请方式加入")
    public Result inviteWayJoin(HttpServletRequest request, @RequestBody InviteJoinDTO bindDTO) {
        ValidatorUtils.validateEntity(bindDTO, DefaultGroup.class);
        SysLogLoginEntity log = getSysLogLoginEntity(3, request, sourceUtils);
        return sysUserOpenIdService.inviteJoin(log, bindDTO);
    }

    private SysLogLoginEntity getSysLogLoginEntity(int operation, HttpServletRequest request, SourceUtils sourceUtils) {
        SysLogLoginEntity log = new SysLogLoginEntity();
        //绑定登录
        log.setOperation(operation);
        log.setCreateDate(new Date());
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setSourceFrom(sourceUtils.getResourceType(request.getHeader(HttpHeaders.USER_AGENT)));
        log.setTenantCode(SecurityUser.getTenantCode());
        return log;
    }

    @PostMapping("user/register")
    @ApiOperation(value = "用户注册")
    @Transactional(rollbackFor = Exception.class)
    public Result registerUser(@RequestBody @Valid WxRegisterDTO dto) {
        if (StringUtils.isEmpty(dto.getSessionKey())) {
            Result result = new Result();
            result.setCode(ErrorCode.SESSIONKEY_EXPIRED);
            result.setMsg("sessionKey为空,请重新授权!");
            return result;
        }
        return sysUserAndTenantService.register(dto);
    }*/

}
