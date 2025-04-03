package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.rider.customer.dto.RiderCustomerDTO;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import org.jeecg.modules.rider.customer.enums.CustomerIdentityEnum;
import org.jeecg.modules.rider.customer.service.IRiderCustomerService;
import org.jeecg.modules.rider.pay.service.WeChatPayApiInvoke;
import org.jeecg.modules.rider.pay.util.Result;
import org.jeecg.modules.rider.security.dto.*;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private IRiderCustomerService riderCustomerService;

    @Autowired
    private ISysUserService sysUserService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Autowired
    private BaseCommonService baseCommonService;

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
                throw new JeecgBootException("sessionKey已失效,请重新授权!");
            }
            openId = resultDTO.getOpenid();
        }

        if (StringUtils.isEmpty(openId)) {
            throw new JeecgBootException("鉴权不能为空!");
        }
        JSONObject obj = new JSONObject();
        //根据openid查找用户
        RiderCustomer userDTO = riderCustomerService.getByOpenId(openId);
        if (Objects.isNull(userDTO)) {
            Result result = new Result();
            result.setCode(10070);
            result.setMsg("用户未注册,请先注册!");
            return result;
        }
        SysUser userByPhone = sysUserService.getUserByPhone(userDTO.getPhone());
        if(Objects.isNull(userByPhone)){
            throw new JeecgBootException("该手机号用户不存在!");
        }
        //6. 生成token
        String token = JwtUtil.sign(userByPhone.getUsername(), userByPhone.getPassword());
        // 设置超时时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
        Result result = new Result();
        //token 信息
        obj.put("token", userDTO);
        result.setData(obj);
        result.setCode(200);
        baseCommonService.addLog("用户名: " + userByPhone.getUsername() + ",登录成功[移动端]！", CommonConstant.LOG_TYPE_1, null);
        return result;

    }

    @PostMapping("user/register")
    @ApiOperation(value = "用户注册")
    @Transactional(rollbackFor = Exception.class)
    public Result registerUser(@RequestBody @Valid RiderCustomerDTO dto) {
        if (StringUtils.isEmpty(dto.getSessionKey())) {
            throw new JeecgBootException("sessionKey已失效,请重新授权!");
        }
        if(StringUtils.isEmpty(dto.getPhone())){
            throw new JeecgBootException("手机号不能为空！");
        }
        WxResultDTO resultDTO = (WxResultDTO) redisUtil.get(dto.getSessionKey());
        dto.setWxOpenId(resultDTO.getOpenid());
        dto.setUnionid(resultDTO.getUnionid());
        dto.setIdentity(CustomerIdentityEnum.TOURIST.getCode());
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    //新增客户信息
                    RiderCustomer riderCustomer = new RiderCustomer();
                    BeanUtils.copyProperties(dto,riderCustomer);
                    riderCustomerService.save(riderCustomer);
                    //新增用户信息
                    SysUser user = new SysUser();
                    //手机号作为用户名
                    user.setUsername(dto.getPhone());
                    //密码默认为手机号后8位
                    user.setPassword(dto.getPhone().substring(3));
                    user.setAvatar(dto.getAvatar());
                    user.setPhone(dto.getPhone());
                    user.setRealname(dto.getName());
                    user.setSex(dto.getSex());
                    user.setCreateTime(new Date());//设置创建时间
                    String salt = oConvertUtils.randomGen(8);
                    user.setSalt(salt);
                    String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);
                    user.setPassword(passwordEncode);
                    user.setStatus(1);
                    user.setDelFlag(CommonConstant.DEL_FLAG_0);
                    //用户表字段org_code不能在这里设置他的值
                    user.setOrgCode(null);
                    // 保存用户走一个service 保证事务
                    sysUserService.saveUser(user, "1169504891467464705", null, null);
                    baseCommonService.addLog("小程序注册用户，username： " +user.getUsername() ,CommonConstant.LOG_TYPE_2, 2);
                }catch (Exception e){
                    //抛出异常，事务回滚
                    throw e;
                }
                return null;
            }
        });
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
