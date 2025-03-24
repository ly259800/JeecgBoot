package org.jeecg.modules.rider.pay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDUtils {

    /**
     * 入库单号 VIP20220102002
     * 字母VIP+时间（年月日）+001（默认3位，不够递增变4位）
     * @param incrId
     * @return
     */
    public static String getOutTradeNo(Long incrId){
        // 1.前缀
        String start = "VIP";
        // 2.时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dayTime = sdf.format(new Date());
        // 3.订单号为 VIP+时间+自增id
        String value =  start + dayTime + String.format("%03d", incrId) ;
        return value;
    }

    /**
     * 入库单号 CY0001
     * 字母CY开头，默认1开始，4位起步，不足的前面补0
     * @param incrId
     * @return
     */
    public static String getInstoreNo(Long incrId){
        // 1.前缀
        String start = "CY";
        // 2.入库单号为 CY+自增id
        String value =  start + String.format("%04d", incrId) ;
        return value;
    }

    /**
     * 外协单号 WX0001
     * 字母WX开头，默认1开始，4位起步，不足的前面补0
     * @param incrId
     * @return
     */
    public static String getAssistNo(Long incrId){
        // 1.前缀
        String start = "WX";
        // 2.入库单号为 WX+自增id
        String value =  start + String.format("%04d", incrId) ;
        return value;
    }

    /**
     * 发货单号 FH0001
     * 字母FH开头，默认1开始，4位起步，不足的前面补0
     * @param incrId
     * @return
     */
    public static String getDeliverNo(Long incrId){
        // 1.前缀
        String start = "FH";
        // 2.入库单号为 FH+自增id
        String value =  start + String.format("%04d", incrId) ;
        return value;
    }

}
