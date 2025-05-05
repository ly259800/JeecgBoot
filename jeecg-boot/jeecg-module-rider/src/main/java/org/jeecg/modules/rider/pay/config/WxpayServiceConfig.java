package org.jeecg.modules.rider.pay.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class WxpayServiceConfig {
    private static final String APPLICATION_MODEL_PRD = "prod";
    protected static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    protected static final SecureRandom RANDOM = new SecureRandom();

    @Value("${spring.profiles.active}")
    private String server;

    /**
     * 微信小程序appid
     */
    @Value("${wechat.miniprogram.appid}")
    private String appid;

    /**
     * 微信小程序密钥
     */
    @Value("${wechat.miniprogram.secret}")
    private String secret;

    /**
     * 微信网站应用appid
     */
    @Value("${wechat.web.appid}")
    private String webAppid;

    /**
     * 微信网站应用密钥
     */
    @Value("${wechat.web.secret}")
    private String webSecret;

    @Value("${wechatpay.openSignCheck:true}")
    private boolean openSignCheck;

    /**
     * 商户号
     */
    @Value("${wechatpay.merchant.id}")
    private String mchid;

    /**
     * 商户证书序列号
     */
    @Value("${wechatpay.merchant.serialNO}")
    private String serialNO;

    /**
     * 商户APIV3密钥
     */
    @Value("${wechatpay.api.V3Key}")
    private String apiV3Key;

    /**
     * 微信支付回调地址
     */
    @Value("${wechatpay.notify.url}")
    private String notifyUrl;

    /**
     * 微信支付回调手机号
     */
    @Value("${wechatpay.test.mobile}")
    private String mobile;

    /**
     * 微信商户转账回调地址
     */
    @Value("${wechatpay.transfer.notifyurl}")
    private String transferNotifyUrl;

    /**
     * 微信商户转账信息类型
     */
    @Value("${wechatpay.transfer.info_type}")
    private String infotype;

    /**
     * 微信商户转账信息内容
     */
    @Value("${wechatpay.transfer.info_content}")
    private String infoContent;

    /**
     * 转账场景ID
     */
    @Value("${wechatpay.transfer.scene_id}")
    private String sceneId;

    /**
     * 微信商户转账信息内容
     */
    @Value("${wechatpay.transfer.user_recv_perception}")
    private String userRecvPerception;

    /**
     * 获取微信支付client
     * @return
     */
    @SneakyThrows
    public CloseableHttpClient getWechatpayClient(){
        //获取商户API证书私钥文件内容
        ClassPathResource cpr = new ClassPathResource("/static/privatekey/apiclient_key.pem");
        // 加载商户API证书私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(cpr.getInputStream());
        // 生成构造器
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchid, serialNO, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(getValidator()));
        // 初始化httpClient
        CloseableHttpClient httpClient = builder.build();
        return httpClient;
    }

    /**
     * 获取微信支付Validator
     * @return
     */
    @SneakyThrows
    public Verifier getValidator(){
        //获取商户API证书私钥文件内容
        ClassPathResource cpr = new ClassPathResource("/static/privatekey/apiclient_key.pem");
        // 加载商户API证书私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(cpr.getInputStream());
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 生成凭证
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchid,
                new PrivateKeySigner(serialNO, merchantPrivateKey));

        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(mchid, wechatPay2Credentials, apiV3Key.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        Verifier verifier = certificatesManager.getVerifier(mchid);
        return verifier;
    }

    /**
     * 获取微信支付Validator
     * @return
     */
    @SneakyThrows
    public X509Certificate getCertificate(){
        //获取商户API证书私钥文件内容
        ClassPathResource cpr = new ClassPathResource("/static/privatekey/apiclient_cert.pem");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(cpr.getInputStream());
    }

    /**
     * 生成带签名的支付信息
     * @param prePayId 预支付ID
     * @return
     */
    @SneakyThrows
    public ObjectNode createSignature(String prePayId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        String nonceStr = generateNonceStr();
        long timestamp = generateTimestamp();
        StringBuilder paySign = new StringBuilder();    // 小程序签名
        paySign.append(appid).append("\n");
        paySign.append(timestamp).append("\n");
        paySign.append(nonceStr).append("\n");
        paySign.append("prepay_id=").append(prePayId).append("\n");
        //获取商户API证书私钥文件内容
        ClassPathResource cpr = new ClassPathResource("/static/privatekey/apiclient_key.pem");
        // 加载商户API证书私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(cpr.getInputStream());
        try {
            //生成签名
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(merchantPrivateKey);
            sign.update(paySign.toString().getBytes(StandardCharsets.UTF_8));
            rootNode.put("paySign", Base64.getEncoder().encodeToString(sign.sign()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        }
        rootNode.put("appId", appid)
                .put("timeStamp", timestamp)
                .put("nonceStr", nonceStr)
                .put("package", prePayId)
                .put("signType", "RSA");
        return rootNode;
    }


    /**
     * 获取数据解密工具
     * @return
     */
    @SneakyThrows
    public AesUtil getAesUtil(){
        return new AesUtil(apiV3Key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 是否开启验签
     * @return
     */
    public boolean isOpenSignCheck() {
        // 生产环境一定是开启验签的，其他环境可根据openSignCheck 参数在配置文件中配置是否验签
        return server.equalsIgnoreCase(APPLICATION_MODEL_PRD) || openSignCheck;
    }

    /**
     * 是否生产环境
     * @return
     */
    public boolean isProdEnvironment() {
        // 生产环境一定是开启验签的，其他环境可根据openSignCheck 参数在配置文件中配置是否验签
        return server.equalsIgnoreCase(APPLICATION_MODEL_PRD);
    }

    protected long generateTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    protected String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public String getNotifyUrl(){
        return this.notifyUrl;
    }

    public List<String> getTestMobile() {
        if(StringUtils.isNotEmpty(mobile)){
            String[] split = mobile.split(",");
            return Arrays.asList(split);
        }
        return new ArrayList<>();
    }

    public String getAppid() {
        return this.appid;
    }

    public String getMchid() {
        return this.mchid;
    }

    public String getSecret() {
        return secret;
    }

    public String getWebAppid() {
        return webAppid;
    }

    public String getWebSecret() {
        return webSecret;
    }

    public String getTransferNotifyUrl() {
        return transferNotifyUrl;
    }

    public String getInfotype() {
        return infotype;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public String getSceneId() {
        return sceneId;
    }

    public String getUserRecvPerception() {
        return userRecvPerception;
    }

    public String getSerialNO() {
        return serialNO;
    }
}
