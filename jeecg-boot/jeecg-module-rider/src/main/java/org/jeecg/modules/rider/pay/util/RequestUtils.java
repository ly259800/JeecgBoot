package org.jeecg.modules.rider.pay.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * 请求工具类
 * @author hzpsl
 *
 */
public class RequestUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RequestUtils.class);

    private RequestUtils() {
    }

    /**
     * Gets request parameters.
     *
     * @param req the req
     * @return the request parameters
     */
    public static JSONObject getRequestParameters(HttpServletRequest req) {
        JSONObject paramJson = new JSONObject();
        //所有入参
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String paramValue = req.getParameter(paramName);
            if (paramValue.length() != 0) {
                paramJson.put(paramName, paramValue);
            }
        }
        return paramJson;
    }

    /**
     * Gets request parameters.
     *
     * @param req the req
     * @return the request parameters
     */
    public static JSONObject getRequestParameters(HttpServletRequest req, String charsetName) {
        JSONObject paramJson = new JSONObject();
        //所有入参
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();

            String paramValue = req.getParameter(paramName);
            if (paramValue.length() != 0) {
                paramJson.put(paramName, charsetVal(paramValue, charsetName));
            }
        }
        return paramJson;
    }

    /**
     * @param v
     * @param charsetName
     * @return
     */
    private static String charsetVal(String v, String charsetName) {
        try {
            return new String(v.getBytes(StandardCharsets.ISO_8859_1), charsetName);
        } catch (UnsupportedEncodingException e) {
            LOG.error("RequestUtils.charsetVal error:{}", e.getMessage());
            return "";
        }
    }

    /**
     * Gets request stream.
     *
     * @param req the req
     * @return the request stream
     */
    public static String getRequestStream(HttpServletRequest req) {
        try {
            return getInputStream(req.getInputStream(), "utf-8");
        } catch (IOException e) {
            LOG.error("RequestUtils.getRequestStream error,", e);
        }
        return "";
    }

    /**
     * Gets input stream.
     *
     * @param in the in
     * @return the input stream
     * @throws IOException the io exception
     */
    public static String getInputStream(InputStream in) {
        return getInputStream(in, "UTF-8");
    }

    /**
     * Gets input stream.
     *
     * @param inputStream the in
     * @param charsetName the recv encoding
     * @return the input stream
     * @throws IOException the io exception
     */
    @SuppressWarnings("deprecation")
    public static String getInputStream(InputStream inputStream, String charsetName) {
        try {
            int available = inputStream.available();
            if (available > 0) {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[512];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                return result.toString(charsetName);
            }
            LOG.info("inputStream.available:0");
        } catch (Exception e) {
            LOG.error("getInputStream error:{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return "";
    }

    /**
     * 获取信息
     *
     * @param req
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> getHeadersInfo(HttpServletRequest req) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取客户端IP地址，此方法用在proxy环境中
     * @param req
     * @return
     */
/*    public static String getRemoteAddr(final HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if(StringUtils.isNotBlank(ip)){
            final String[] ips = StringUtils.split(ip,',');
            if(ips!=null){
                for(String tmpip : ips){
                    if(StringUtils.isBlank(tmpip)) {
                        continue;
                    }
                    tmpip = tmpip.trim();
                    if(isIPAddr(tmpip) && !tmpip.startsWith("10.") && !tmpip.startsWith("192.168.") && !"127.0.0.1".equals(tmpip)){
                        return tmpip.trim();
                    }
                }
            }
        }
        ip = req.getHeader("x-real-ip");
        if(isIPAddr(ip)) {
            return ip;
        }
        ip = req.getRemoteAddr();
        if(ip.indexOf('.')==-1) {
            ip = System.getProperty("myapplication.ip");;
        }
        return ip;
    }*/

    /**
     * 判断字符串是否是一个IP地址
     * @param addr
     * @return
     */
    public static boolean isIPAddr(final String addr){
        if(StringUtils.isEmpty(addr)) {
            return false;
        }
        final String[] ips = StringUtils.split(addr, '.');
        if(ips.length != 4) {
            return false;
        }
        try{
            final int ipa = Integer.parseInt(ips[0]);
            final int ipb = Integer.parseInt(ips[1]);
            final int ipc = Integer.parseInt(ips[2]);
            final int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
                    && ipc <= 255 && ipd >= 0 && ipd <= 255;
        }catch(final Exception e){}
        return false;
    }

}
