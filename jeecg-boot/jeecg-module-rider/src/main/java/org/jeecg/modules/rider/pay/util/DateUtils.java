package org.jeecg.modules.rider.pay.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String Y_M_D = "yyyy-MM-dd";

    public static String Y_M = "yyyy-MM";

    public static String Y_M_D_h_m_s = "yyyy-MM-dd HH:mm:ss";

    //获取指定日期后N天
    @SneakyThrows
    public static Date getAfterDate(String strDate,int day) {
        Date date = new SimpleDateFormat(Y_M_D).parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);//得到后N天
        Date newDate = calendar.getTime();
        return newDate;
    }

    //获取指定日期后N天
    @SneakyThrows
    public static Date getAfterDate(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);//得到后N天
        Date newDate = calendar.getTime();
        return newDate;
    }

    //获取指定日期后N年
    @SneakyThrows
    public static Date getAfterDateByYear(String strDate,int year) {
        Date date = new SimpleDateFormat(Y_M_D).parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);    //得到后N年
        Date newDate = calendar.getTime();
        return newDate;
    }

    //获取指定日期后N年
    @SneakyThrows
    public static Date getAfterDateByYear(Date date,int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);    //得到后N年
        Date newDate = calendar.getTime();
        return newDate;
    }

    //获取指定日期后N个月
    @SneakyThrows
    public static Date getAfterDateByMonth(Date date,int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        /**
         * 取上个月
         */
        calendar.add(Calendar.MONTH, month+1);
        //获取上个月份中的最小值，即第一天
        int lastDay = calendar.getMinimum(Calendar.DATE);
        // 设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
        Date newDate = calendar.getTime();
        return newDate;
    }

    //转换为日期格式
    @SneakyThrows
    public static Date strFormatDate(String str,String format) {
        Date date = new SimpleDateFormat(format).parse(str);
        return date;
    }

    //转换为字符串格式
    @SneakyThrows
    public static String dateFormatStr(Date date,String format) {
        String str = new SimpleDateFormat(format).format(date);
        return str;
    }

    //获取指定年月的第一天
    @SneakyThrows
    public static Date getMinDateMonth(String month){
        Date nowDate = strFormatDate(month,Y_M);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    //获取当月的第一天
    @SneakyThrows
    public static Date getMinDateCurrMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();
    }

    //获取指定年月的最后一天
    @SneakyThrows
    public static Date getMaxDateMonth(String month){
        Date nowDate = strFormatDate(month,Y_M);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    // 获得某天最大时间 例:2022-03-15 23:59:59
    public static Date getEndOfDay(Date date) {
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), shanghaiZoneId);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(shanghaiZoneId).toInstant());
    }

    // 获得某天最小时间 例:2022-03-15 00:00:00
    public static Date getStartOfDay(Date date) {
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), shanghaiZoneId);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(shanghaiZoneId).toInstant());
    }

    // 获取2个日期相差的天数
    public static int getDayBySubtractDate(Date date,Date subDate) {
        //时间戳相差的毫秒数
        long num = date.getTime() - subDate.getTime();
        //除以一天的毫秒数
        long day = num / (24 * 60 * 60 * 1000);
        return (int) day;
    }

    public static Date getBeforeDateByMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);    //得到N个月
        Date newDate = calendar.getTime();
        return newDate;
    }
}
