package org.jeecg.modules.rider.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.rider.order.entity.RiderUserOrder;
import org.jeecg.modules.rider.order.mapper.RiderUserOrderMapper;
import org.jeecg.modules.rider.order.service.IRiderUserOrderService;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.enums.OrderStateEnum;
import org.jeecg.modules.rider.pay.util.DateUtils;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.jeecg.modules.rider.pay.util.Result;
import org.jeecg.modules.rider.pay.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
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
        RiderUserOrder tenantOrderDTO = new RiderUserOrder();
        BeanUtils.copyProperties(dto,tenantOrderDTO);
        //订单状态
        tenantOrderDTO.setOrderState(OrderStateEnum.NOTPAY.getCode());
        //获取当前用户
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //生成租户订单号
        Long maxOutTradeNo = this.getMaxOutTradeNo();
        String outTradeNo = UUIDUtils.getOutTradeNo(maxOutTradeNo);
        tenantOrderDTO.setPayId(user.getId());
        tenantOrderDTO.setOutTradeNo(outTradeNo);
        this.save(tenantOrderDTO);
        ObjectNode rootNode = WechatPayContants.OBJECT_MAPPER.createObjectNode();
        rootNode.put("outTradeNo",outTradeNo);
        return new Result().ok(rootNode);
    }
}
