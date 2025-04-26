package org.jeecg.modules.rider.pay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.rider.pay.dto.OrderCloseDTO;
import org.jeecg.modules.rider.pay.dto.OrderQueryDTO;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.dto.WechatTransferDTO;
import org.jeecg.modules.rider.pay.service.WeChatPayService;
import org.jeecg.modules.rider.pay.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("wechatpay")
@Api(tags="微信支付")
@Slf4j
public class WeChatPayController {

    @Resource
    private WeChatPayService weChatPayServise;

    @PostMapping("prepay")
    @ApiOperation("预下单")
    @AutoLog("预下单")
    public Result prepay(@RequestBody @Valid WechatPayDTO payDto) throws Exception{
        return weChatPayServise.preOrder(payDto);
    }

    @PostMapping("querypay")
    @ApiOperation("查询订单")
    @AutoLog("查询订单")
    public Result querypay(@RequestBody @Valid OrderQueryDTO queryDto) {
        return weChatPayServise.queryOrder(queryDto);
    }

    @PostMapping("closepay")
    @ApiOperation("关闭订单")
    @AutoLog("关闭订单")
    public Result closepay(@RequestBody @Valid OrderCloseDTO closeDto) {
        return weChatPayServise.closeOrder(closeDto);
    }


    @PostMapping("transfer")
    @ApiOperation("商户转账")
    @AutoLog("商户转账")
    public Result transfer(@RequestBody @Valid WechatTransferDTO transferDTO) throws Exception {
        return weChatPayServise.transfer(transferDTO);
    }


}
