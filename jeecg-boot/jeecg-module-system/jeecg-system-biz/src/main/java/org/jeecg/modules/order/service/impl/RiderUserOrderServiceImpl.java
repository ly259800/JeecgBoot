package org.jeecg.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.order.service.IRiderUserOrderService;
import org.jeecg.modules.pay.enums.OrderStateEnum;
import org.jeecg.modules.pay.util.DateUtils;
import org.jeecg.modules.pay.util.PriceUtils;
import org.jeecg.modules.pay.util.Result;
import org.jeecg.modules.order.entity.RiderUserOrder;
import org.jeecg.modules.order.mapper.RiderUserOrderMapper;
import org.jeecg.modules.pay.config.WxpayServiceConfig;
import org.jeecg.modules.pay.constants.WechatPayContants;
import org.jeecg.modules.pay.util.UUIDUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Description: 用户个人订单
 * @Author: jeecg-boot
 * @Date:   2025-03-28
 * @Version: V1.0
 */
@Service
public class RiderUserOrderServiceImpl extends ServiceImpl<RiderUserOrderMapper, RiderUserOrder> implements IRiderUserOrderService {

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;

    @Override
    public RiderUserOrder getByOutTradeNo(String outTradeNo) {
        QueryWrapper<RiderUserOrder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderUserOrder::getOutTradeNo,outTradeNo).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Long getMaxOutTradeNo() {
        //获取当天的最大入库单号
        QueryWrapper<RiderUserOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(out_trade_no) out_trade_no");
        queryWrapper.lambda().between(RiderUserOrder::getCreateTime, DateUtils.getStartOfDay(new Date()),DateUtils.getEndOfDay(new Date()));
        queryWrapper.lambda().likeRight(RiderUserOrder::getOutTradeNo,"VIP");
        RiderUserOrder maxInstoreNo = baseMapper.selectOne(queryWrapper);
        if(Objects.isNull(maxInstoreNo) || StringUtils.isEmpty(maxInstoreNo.getOutTradeNo())){
            return 1L;
        } else {
            String instoreNo = maxInstoreNo.getOutTradeNo().substring(11);
            long l = Long.valueOf(instoreNo).longValue();
            return l+1;
        }
    }

    @Override
    public Result saveUserOrder(RiderUserOrder dto) {
        // 参数校验
        if (!PriceUtils.checkZero(dto.getTotalAmount())) {
            throw new JeecgBootException("订单金额必须大于0！");
        }
        //订单状态
        dto.setOrderState(OrderStateEnum.NOTPAY.getCode());
        //获取当前用户
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //生成租户订单号
        Long maxOutTradeNo = this.getMaxOutTradeNo();
        String outTradeNo = UUIDUtils.getOutTradeNo(maxOutTradeNo);
        dto.setPayId(user.getId());
        dto.setOutTradeNo(outTradeNo);
        this.save(dto);
        ObjectNode rootNode = WechatPayContants.OBJECT_MAPPER.createObjectNode();
        rootNode.put("outTradeNo",outTradeNo);
        return new Result().ok(rootNode);
    }
}
