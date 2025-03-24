package org.jeecg.modules.rider.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.WechatPayContants;
import org.jeecg.modules.rider.pay.dao.TenantOrderMapper;
import org.jeecg.modules.rider.pay.dto.TenantOrderSaveDTO;
import org.jeecg.modules.rider.pay.entity.SysTenantOrderEntity;
import org.jeecg.modules.rider.pay.enums.OrderStateEnum;
import org.jeecg.modules.rider.pay.enums.OrderTypeEnum;
import org.jeecg.modules.rider.pay.service.TenantOrderService;
import org.jeecg.modules.rider.pay.util.DateUtils;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.jeecg.modules.rider.pay.util.Result;
import org.jeecg.modules.rider.pay.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 租户订单信息表
 *
 * @author admin
 * @since 3.0 2022-02-08
 */
@Service
@Slf4j
public class TenantOrderServiceImpl extends ServiceImpl<TenantOrderMapper, SysTenantOrderEntity> implements TenantOrderService {

    @Resource
    private TenantOrderMapper tenantOrderMapper;

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;
    
    @Override
    public Result saveTenantOrder(TenantOrderSaveDTO dto) {
        // 参数校验
        if (!PriceUtils.checkZero(dto.getTotalAmount())) {
            throw new JeecgBootException("订单金额必须大于0！");
        }
        SysTenantOrderEntity tenantOrderDTO = new SysTenantOrderEntity();
        BeanUtils.copyProperties(dto,tenantOrderDTO);
        //到期时间
        tenantOrderDTO.setExpirationDate(DateUtils.strFormatDate(dto.getExpirationDate(),DateUtils.Y_M_D));
        //订单状态
        tenantOrderDTO.setOrderState(OrderStateEnum.NOTPAY.getCode());
        //获取订单类型
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.getEnum(dto.getOrderType());
        if(Objects.isNull(orderTypeEnum)){
            throw new JeecgBootException(String.format("订单类型【%s】不存在!", dto.getOrderType()));
        }
        switch (orderTypeEnum){
            case EXPAND:
                if(dto.getExpandNum()<=0){
                    throw new JeecgBootException("扩容数量必须大于0！");
                }
                break;
            case RENEW:
                if(dto.getRenewYear()>0){
                    //续费时间起
                    tenantOrderDTO.setRenewDateStart(DateUtils.getAfterDate(dto.getExpirationDate(),1));
                    //续费时间止
                    tenantOrderDTO.setRenewDateEnd(DateUtils.getAfterDateByYear(dto.getExpirationDate(),dto.getRenewYear()));
                } else {
                    throw new JeecgBootException("续费年限必须大于0！");
                }
                break;
            default:
        }
        //记录订单创建人
        /*UserDetail user = SecurityUser.getUser();
        if(Objects.nonNull(user)){
            tenantOrderDTO.setUserId(user.getId());
            tenantOrderDTO.setUsername(user.getUsername());
        }*/
        //查询该租户下未支付和支付失败的订单
        QueryWrapper<SysTenantOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()//.eq(SysTenantOrderEntity::getTenantCode,SecurityUser.getTenantCode())
                .in(SysTenantOrderEntity::getOrderState,OrderStateEnum.NOTPAY.getCode(),OrderStateEnum.PAYERROR.getCode());
        Long orderCount = tenantOrderMapper.selectCount(queryWrapper);
        if (orderCount > 0) {
            throw new JeecgBootException("已存在未支付成功的订单，请先支付或取消!");
        }
        //生成租户订单号
        Long maxOutTradeNo = this.getMaxOutTradeNo();
        String outTradeNo = UUIDUtils.getOutTradeNo(maxOutTradeNo);
        //若不是生产环境,则订单号加上TEST
        if(!wxpayServiceConfig.isProdEnvironment()){
            outTradeNo = outTradeNo.replace("VIP", "DEV");
        }
        tenantOrderDTO.setOutTradeNo(outTradeNo);
        this.save(tenantOrderDTO);
        ObjectNode rootNode = WechatPayContants.OBJECT_MAPPER.createObjectNode();
        rootNode.put("outTradeNo",outTradeNo);
        return new Result().ok(rootNode);
    }

    @Override
    public SysTenantOrderEntity getByOutTradeNo(String outTradeNo) {
        QueryWrapper<SysTenantOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysTenantOrderEntity::getOutTradeNo,outTradeNo).last("limit 1");
        return tenantOrderMapper.selectOne(wrapper);
    }

    @Override
    public Long getMaxOutTradeNo() {
        //获取当天的最大入库单号
        QueryWrapper<SysTenantOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(SysTenantOrderEntity::getCreateTime,DateUtils.getStartOfDay(new Date()),DateUtils.getEndOfDay(new Date()));
        if(wxpayServiceConfig.isProdEnvironment()){
            queryWrapper.lambda().likeRight(SysTenantOrderEntity::getOutTradeNo,"VIP");
        } else {
            queryWrapper.lambda().likeRight(SysTenantOrderEntity::getOutTradeNo,"DEV");
        }
        String maxInstoreNo = tenantOrderMapper.getMaxOutTradeNo(queryWrapper);
        if(StringUtils.isEmpty(maxInstoreNo)){
            return 1L;
        } else {
            String instoreNo = maxInstoreNo.substring(11);
            long l = Long.valueOf(instoreNo).longValue();
            return l+1;
        }
    }
}
