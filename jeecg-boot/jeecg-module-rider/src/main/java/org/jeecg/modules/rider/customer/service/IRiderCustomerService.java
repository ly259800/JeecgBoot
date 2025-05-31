package org.jeecg.modules.rider.customer.service;

import org.jeecg.modules.rider.customer.dto.RiderCustomerDTO;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface IRiderCustomerService extends IService<RiderCustomer> {

    RiderCustomer getByOpenId(String openid);


    RiderCustomer getByPhone(String phone);


    List<RiderCustomer> getByReferencePhone(String referencePhone);

    void upgradePartner(String ids);

    void updateQrcode(String id);

    //添加用户佣金
    void addCommission(String id, BigDecimal commission);

    //用户佣金提现
    void subtractCommission(String id,BigDecimal commission,BigDecimal settleCommission);

    RiderCustomerDTO convertTotal(RiderCustomer entity);

}
