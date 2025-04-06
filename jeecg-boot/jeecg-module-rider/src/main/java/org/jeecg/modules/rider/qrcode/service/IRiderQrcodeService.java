package org.jeecg.modules.rider.qrcode.service;

import org.jeecg.modules.rider.qrcode.entity.RiderQrcode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 小程序二维码
 * @Author: jeecg-boot
 * @Date:   2025-04-06
 * @Version: V1.0
 */
public interface IRiderQrcodeService extends IService<RiderQrcode> {

    void saveRiderQrcode(RiderQrcode riderQrcode);

    RiderQrcode getByScene(String scene);

}
