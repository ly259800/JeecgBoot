package org.jeecg.modules.rider.qrcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.rider.params.entity.RiderParams;
import org.jeecg.modules.rider.pay.service.WeChatPayApiInvoke;
import org.jeecg.modules.rider.qrcode.entity.RiderQrcode;
import org.jeecg.modules.rider.qrcode.mapper.RiderQrcodeMapper;
import org.jeecg.modules.rider.qrcode.service.IRiderQrcodeService;
import org.jeecg.modules.rider.security.dto.WxSacnLoginResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @Description: 小程序二维码
 * @Author: jeecg-boot
 * @Date:   2025-04-06
 * @Version: V1.0
 */
@Service
public class RiderQrcodeServiceImpl extends ServiceImpl<RiderQrcodeMapper, RiderQrcode> implements IRiderQrcodeService {

    @Autowired
    private WeChatPayApiInvoke weChatPayApiInvoke;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Value(value="${jeecg.uploadType}")
    private String uploadType;

    @Override
    @SneakyThrows
    public void saveRiderQrcode(RiderQrcode riderQrcode) {
        //生成一个二维码传参
        this.save(riderQrcode);
        String scence = riderQrcode.getId();
        riderQrcode.setScene(scence);
        WxSacnLoginResDTO accessToken = weChatPayApiInvoke.getAccessToken();
        if(StringUtils.isNotEmpty(accessToken.getAccess_token())){
            byte[] qrCodeBytes = weChatPayApiInvoke.createQrCode(riderQrcode, accessToken.getAccess_token());
            String qrcodeUrl = CommonUtils.uploadOnlineImage(qrCodeBytes, upLoadPath, "qrcode", uploadType);
            riderQrcode.setUrl(qrcodeUrl);
            this.updateById(riderQrcode);
        } else {
            throw new JeecgBootException("获取access_token失败,请退出页面重试!");
        }
    }

    @Override
    public RiderQrcode getByScene(String scene) {
        QueryWrapper<RiderQrcode> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RiderQrcode::getScene,scene).last("limit 1");
        return baseMapper.selectOne(wrapper);
    }
}
