package org.jeecg.modules.pay.controller;


import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pay.constants.BaseErrorCodeEnum;
import org.jeecg.modules.pay.dto.WechatPayApiOutDTO;
import org.jeecg.modules.pay.entity.ResponseSignVerifyParams;
import org.jeecg.modules.pay.exception.WechatPayException;
import org.jeecg.modules.pay.util.service.WeChatPayNotifyInvoke;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信回调接口
 */
@Slf4j
@RestController
public class WeChatPayNotifyController {

    @Resource
    private WeChatPayNotifyInvoke weChatPayNotifyInvoke;

    @RequestMapping("/wxpay/notify")
    public WechatPayApiOutDTO notify(HttpServletRequest req) {
        log.info("微信支付异步消息回调接收成功");
        WechatPayApiOutDTO outDTO = new WechatPayApiOutDTO();
        ResponseSignVerifyParams param = null;
        try {
            // 1.解析回调参数以及验签
            param = weChatPayNotifyInvoke.getRequestParam(req);
            if (param == null) {
                log.error("微信支付异步消息回调参数为空！");
            }
            // 2.回调处理
            weChatPayNotifyInvoke.execNotify(param);
            outDTO.setCode(BaseErrorCodeEnum.REQ_SUCCESS.getCode());
            outDTO.setMessage(BaseErrorCodeEnum.REQ_SUCCESS.getMsg());
        } catch (WechatPayException e) {
            log.error("微信支付异步消息回调处理异常", e);
            outDTO.setCode(e.getCode());
            outDTO.setMessage(e.getMsg());
        } catch (Exception e) {
            log.error("微信支付异步消息回调处理异常", e);
            outDTO.setCode(BaseErrorCodeEnum.CALLBACK_HANDLED.getCode());
            outDTO.setMessage(e.getMessage());
        }
        return outDTO;
    }

}
