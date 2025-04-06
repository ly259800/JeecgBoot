package org.jeecg.modules.rider.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.constant.WeChatConstants;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.rider.pay.config.WxpayServiceConfig;
import org.jeecg.modules.rider.pay.constants.BaseErrorCodeEnum;
import org.jeecg.modules.rider.pay.constants.WeChatServerEnum;
import org.jeecg.modules.rider.pay.constants.WechatApiTypeEnum;
import org.jeecg.modules.rider.pay.constants.WechatPayV3APIEnum;
import org.jeecg.modules.rider.pay.dto.OrderCloseDTO;
import org.jeecg.modules.rider.pay.dto.OrderQueryDTO;
import org.jeecg.modules.rider.pay.dto.WechatPayApiOutDTO;
import org.jeecg.modules.rider.pay.dto.WechatPayDTO;
import org.jeecg.modules.rider.pay.exception.WechatPayException;
import org.jeecg.modules.rider.pay.util.PriceUtils;
import org.jeecg.modules.rider.qrcode.entity.RiderQrcode;
import org.jeecg.modules.rider.security.dto.WxLoginDTO;
import org.jeecg.modules.rider.security.dto.WxResultDTO;
import org.jeecg.modules.rider.security.dto.WxSacnLoginResDTO;
import org.jeecg.modules.rider.security.dto.WxUesrInfoResDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;
import java.util.Objects;


/**
 * 微信支付API调用
 */
@Slf4j
@Service
public class WeChatPayApiInvoke {

    @Resource
    private WxpayServiceConfig wxpayServiceConfig;

    @Resource
    private RedisUtil redisUtil;

    /**
     * JSAPI/小程序下单API
     * @param payDto
     * @return
     */
    @SneakyThrows
    public WechatPayApiOutDTO JsapiPay(WechatPayDTO payDto) {
        HttpPost httpPost = new HttpPost(WechatPayV3APIEnum.JSAPI.uri(WeChatServerEnum.CHINA));
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", wxpayServiceConfig.getMchid())
                .put("appid", wxpayServiceConfig.getAppid())
                .put("description", payDto.getDescription())
                .put("notify_url", wxpayServiceConfig.getNotifyUrl())
                .put("out_trade_no", payDto.getOutTradeNo());
        //获取支付的测试手机号，若是测试手机号，则仅支付1分
        List<String> testMobile = wxpayServiceConfig.getTestMobile();
        if(testMobile.contains(payDto.getMobile())){
            rootNode.putObject("amount")
                    .put("total", 1)//订单总金额，单位为分
                    .put("currency", "CNY");//人民币
        } else {
            rootNode.putObject("amount")
                    .put("total", PriceUtils.YuanToFen(payDto.getTotalAmount()))//订单总金额，单位为分
                    .put("currency", "CNY");//人民币
        }
        rootNode.putObject("payer")
                .put("openid", payDto.getOpenid());
        objectMapper.writeValue(bos, rootNode);
        log.info("微信-- 小程序支付，请求参数data：{}", objectMapper.writeValueAsString(rootNode));
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        try (CloseableHttpClient httpClient = wxpayServiceConfig.getWechatpayClient()) {
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                return buildOutDTO(response, WechatApiTypeEnum.JSAPI);
            }
        }
    }

    /**
     * 微信支付订单号查询API
     * @param queryDto
     * @return
     */
    @SneakyThrows
    public WechatPayApiOutDTO queryByTransactionId(OrderQueryDTO queryDto){
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mchid", wxpayServiceConfig.getMchid());
        URI uri = UriComponentsBuilder.fromHttpUrl(WechatPayV3APIEnum.QUERY_TRANSACTION_ID.uri(WeChatServerEnum.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(queryDto.getTransactionId())
                .toUri();
        log.info("微信-- 支付订单号查询，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = wxpayServiceConfig.getWechatpayClient()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return buildOutDTO(response,WechatApiTypeEnum.PAYORDER);
            }
        }
    }

    /**
     * 商户订单号查询API
     * @param queryDto
     * @return
     */
    @SneakyThrows
    public WechatPayApiOutDTO queryByOutTradeNo(OrderQueryDTO queryDto){
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mchid", wxpayServiceConfig.getMchid());
        URI uri = UriComponentsBuilder.fromHttpUrl(WechatPayV3APIEnum.QUERY_OUT_TRADE_NO.uri(WeChatServerEnum.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(queryDto.getOutTradeNo())
                .toUri();
        log.info("微信-- 商户订单号查询，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = wxpayServiceConfig.getWechatpayClient()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return buildOutDTO(response,WechatApiTypeEnum.OUTTRADENO);
            }
        }
    }

    /**
     * 关闭订单API
     * @param closeDto
     * @return
     */
    @SneakyThrows
    public WechatPayApiOutDTO close(OrderCloseDTO closeDto){
        URI uri = UriComponentsBuilder.fromHttpUrl(WechatPayV3APIEnum.CLOSE.uri(WeChatServerEnum.CHINA))
                .build()
                .expand(closeDto.getOutTradeNo())
                .toUri();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid",wxpayServiceConfig.getMchid());
        objectMapper.writeValue(bos, rootNode);
        log.info("微信-- 关闭订单,请求url：{},请求参数：{}", uri.toString(),objectMapper.writeValueAsString(rootNode));
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        try(CloseableHttpClient httpClient = wxpayServiceConfig.getWechatpayClient()){
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                WechatPayApiOutDTO outVO = new WechatPayApiOutDTO();
                int statusCode = response.getStatusLine().getStatusCode();
                //表示关闭订单成功
                if(statusCode == 204 || statusCode == 200 ){
                    log.info("微信-- {}，返回结果result：{}",WechatApiTypeEnum.COLSE.getMsg(), response.getStatusLine().getStatusCode());
                    outVO.setStatus(statusCode);
                    outVO.setMessage(BaseErrorCodeEnum.REQ_SUCCESS.getMsg());
                } else {
                    //关闭订单失败
                    String bodyAsString = Objects.isNull(response.getEntity())?"":EntityUtils.toString(response.getEntity());
                    log.info("微信-- {}，返回结果result：{}",WechatApiTypeEnum.COLSE.getMsg(), response.getStatusLine().getStatusCode()+"【"+bodyAsString+"】");
                    outVO.setStatus(statusCode);
                    outVO.setMessage(BaseErrorCodeEnum.REQ_FAIL.getMsg());
                }
                return outVO;
            }
        }
    }

    /**
     * 通过code获取小程序openid
     * @param loginDTO
     * @return
     */
    @SneakyThrows
    public WxResultDTO getOpenidByCode(WxLoginDTO loginDTO){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code")
                .build()
                .expand(wxpayServiceConfig.getAppid(),wxpayServiceConfig.getSecret(),loginDTO.getCode())
                .toUri();
        log.info("微信-- 通过code获取小程序openid，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 通过code获取小程序openid，返回结果result：{}",bodyAsString);
                WxResultDTO wxResultDTO = JSONObject.parseObject(bodyAsString, WxResultDTO.class);
                return wxResultDTO;
            }
        }
    }

    /**
     * 通过code获取小程序手机号
     * @param loginDTO
     * @return
     */
    @SneakyThrows
    public String getPhoneByCode(WxLoginDTO loginDTO,String accessToken){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token={access_token}")
                .build()
                .expand(accessToken)
                .toUri();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("code",loginDTO.getCode());
        objectMapper.writeValue(bos, rootNode);
        log.info("微信-- 通过code获取手机号,请求url：{},请求参数：{}", uri.toString(),objectMapper.writeValueAsString(rootNode));
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 通过code获取手机号，返回结果result：{}",bodyAsString);
                JsonNode root = objectMapper.readTree(bodyAsString);
                return root.path("phone_info").path("purePhoneNumber").asText();
            }
        }
    }

    /**
     * 小程序获取access_token
     * @return
     */
    @SneakyThrows
    public WxSacnLoginResDTO getAccessToken(){
        Object accessToken = redisUtil.get(WeChatConstants.AccessToken);
        if(Objects.nonNull(accessToken)){
            WxSacnLoginResDTO wxSacnLoginResDTO = JSONObject.parseObject(accessToken.toString(), WxSacnLoginResDTO.class);
            return wxSacnLoginResDTO;
        }
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}")
                .build()
                .expand(wxpayServiceConfig.getAppid(),wxpayServiceConfig.getSecret())
                .toUri();
        log.info("获取手机号-- 获取access_token，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("获取手机号-- 获取access_token，返回结果result：{}",bodyAsString);
                WxSacnLoginResDTO wxSacnLoginResDTO = JSONObject.parseObject(bodyAsString, WxSacnLoginResDTO.class);
                if(StringUtils.isNotEmpty(wxSacnLoginResDTO.getAccess_token())){
                    //有2小时的有效时间，放缓存
                    redisUtil.set(WeChatConstants.AccessToken,bodyAsString,WeChatConstants.expiresTime);
                }
                return wxSacnLoginResDTO;
            }
        }
    }

    /**
     * 生成小程序二维码
     * @param riderQrcode
     * @return
     */
    @SneakyThrows
    public byte[] createQrCode(RiderQrcode riderQrcode, String accessToken) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token={access_token}")
                .build()
                .expand(accessToken)
                .toUri();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("scene",riderQrcode.getScene());
        if(StringUtils.isNotEmpty(riderQrcode.getPage())){
            rootNode.put("page",riderQrcode.getPage());
        }
        if(StringUtils.isNotEmpty(riderQrcode.getEnvVersion())){
            rootNode.put("env_version",riderQrcode.getEnvVersion());
        }
        objectMapper.writeValue(bos, rootNode);
        log.info("微信-- 生成小程序二维码,请求url：{},请求参数：{}", uri.toString(),objectMapper.writeValueAsString(rootNode));
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                byte[] bytes = EntityUtils.toByteArray(response.getEntity());
                // 检查是否是错误响应(微信接口错误时返回JSON而不是图片)
                if (bytes.length > 0 && bytes[0] == '{') {
                    String error = new String(bytes);
                    throw new WechatPayException(500,"生成二维码失败: " + error);
                }
                return bytes;
            }
        }
    }

    /**
     * 通过code获取access_token
     * @param loginDTO
     * @return
     */
    @SneakyThrows
    public WxSacnLoginResDTO getAccessTokenByCode(WxLoginDTO loginDTO){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code")
                .build()
                .expand(wxpayServiceConfig.getWebAppid(),wxpayServiceConfig.getWebSecret(),loginDTO.getCode())
                .toUri();
        log.info("微信-- 通过code获取access_token，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 通过code获取access_token，返回结果result：{}",bodyAsString);
                WxSacnLoginResDTO wxSacnLoginResDTO = JSONObject.parseObject(bodyAsString, WxSacnLoginResDTO.class);
                return wxSacnLoginResDTO;
            }
        }
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param resDTO
     * @return
     */
    @SneakyThrows
    public WxSacnLoginResDTO checkAccessToken(WxSacnLoginResDTO resDTO){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/auth?access_token={access_token}&openid={openid}")
                .build()
                .expand(resDTO.getAccess_token(),resDTO.getOpenid())
                .toUri();
        log.info("微信-- 检验授权凭证（access_token）是否有效，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 检验授权凭证（access_token）是否有效，返回结果result：{}",bodyAsString);
                WxSacnLoginResDTO wxSacnLoginResDTO = JSONObject.parseObject(bodyAsString, WxSacnLoginResDTO.class);
                return wxSacnLoginResDTO;
            }
        }
    }

    /**
     * 刷新或续期access_token使用
     * @param resDTO
     * @return
     */
    @SneakyThrows
    public WxSacnLoginResDTO refreshAccessToken(WxSacnLoginResDTO resDTO){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refresh_token}")
                .build()
                .expand(wxpayServiceConfig.getWebAppid(),resDTO.getRefresh_token())
                .toUri();
        log.info("微信-- 刷新或续期access_token使用，请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 刷新或续期access_token使用，返回结果result：{}",bodyAsString);
                WxSacnLoginResDTO wxSacnLoginResDTO = JSONObject.parseObject(bodyAsString, WxSacnLoginResDTO.class);
                return wxSacnLoginResDTO;
            }
        }
    }

    /**
     * 获取用户个人信息（UnionID机制）
     * @param resDTO
     * @return
     */
    @SneakyThrows
    public WxUesrInfoResDTO getUserInfo(WxSacnLoginResDTO resDTO){
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.weixin.qq.com/sns/userinfo?access_token={access_token}&openid={openid}")
                .build()
                .expand(resDTO.getAccess_token(),resDTO.getOpenid())
                .toUri();
        log.info("微信-- 获取用户个人信息（UnionID机制），请求url：{}", uri.toString());
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("Accept", "application/json");
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String bodyAsString = EntityUtils.toString(response.getEntity());
                log.info("微信-- 获取用户个人信息（UnionID机制），返回结果result：{}",bodyAsString);
                WxUesrInfoResDTO result = JSONObject.parseObject(bodyAsString, WxUesrInfoResDTO.class);
                return result;
            }
        }
    }

    @SneakyThrows
    private WechatPayApiOutDTO buildOutDTO (CloseableHttpResponse response, WechatApiTypeEnum apiTypeEnum) {
        WechatPayApiOutDTO outVO = new WechatPayApiOutDTO();
        String bodyAsString = EntityUtils.toString(response.getEntity());
        log.info("微信-- {}，返回结果result：{}",apiTypeEnum.getMsg(), bodyAsString);
        JSONObject jsonObject = JSONObject.parseObject(bodyAsString);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            //接口返回错误码
            String code = jsonObject.getString("code");
            //接口返回错误信息
            String message = jsonObject.getString("message");
            BaseErrorCodeEnum errorCode = BaseErrorCodeEnum.getEnum(code);
            if(Objects.nonNull(errorCode)){
                outVO.setStatus(errorCode.getStatus());
                outVO.setCode(errorCode.getCode());
                outVO.setMessage(StringUtils.isNotEmpty(message)?message:errorCode.getMsg());
                outVO.setData(jsonObject);
                return outVO;
            } else {
                log.error("微信支付，错误码[" + code + "]不存在!");
                throw new WechatPayException(BaseErrorCodeEnum.PAY_UNKNOW_ERROR,code);
            }
        }
        outVO.setStatus(statusCode);
        outVO.setMessage(BaseErrorCodeEnum.REQ_SUCCESS.getMsg());
        switch (apiTypeEnum){
            case JSAPI:
                ObjectNode signature = wxpayServiceConfig.createSignature(jsonObject.getString("prepay_id"));
                outVO.setData(signature);
                break;
            case PAYORDER:
            case OUTTRADENO:
                outVO.setData(jsonObject);
                break;
            default:
        }
        return outVO;
    }

    public ObjectNode createSignature(String prePayId){
        ObjectNode signature = wxpayServiceConfig.createSignature(prePayId);
        return signature;
    }

}
