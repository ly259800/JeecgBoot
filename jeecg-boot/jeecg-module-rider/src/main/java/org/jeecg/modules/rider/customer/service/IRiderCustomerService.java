package org.jeecg.modules.rider.customer.service;

import org.jeecg.modules.rider.customer.dto.RiderCustomerDTO;
import org.jeecg.modules.rider.customer.entity.RiderCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 客户管理
 * @Author: jeecg-boot
 * @Date:   2025-03-22
 * @Version: V1.0
 */
public interface IRiderCustomerService extends IService<RiderCustomer> {

    RiderCustomer getByOpenId(String openid);


    RiderCustomer getByPhone(String phone);

    void upgradePartner(String ids);

}
