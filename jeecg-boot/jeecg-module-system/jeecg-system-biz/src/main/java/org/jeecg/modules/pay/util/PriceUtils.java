package org.jeecg.modules.pay.util;

import java.math.BigDecimal;

/**
 * 精度金额计算工具类
 */
public class PriceUtils {

    /**
     * 元转分，确保price保留两位有效数字
     * @return
     */
    public static int YuanToFen(BigDecimal amount) {
        BigDecimal bigDecimal = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 分转元，转换为bigDecimal在toString
     * @return
     */
    public static BigDecimal FenToYuan(int price) {
        return BigDecimal.valueOf(price).divide(new BigDecimal(100));
    }

    /**
     * 校验是否大于0
     * @param v
     * @return
     */
    public static boolean checkZero(BigDecimal v) {
        if (v == null) {
            return false;
        }
        return (v.compareTo(BigDecimal.ZERO) >0);
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的和
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的和
     */
    public static double add(double v1, double v2 , int scale) {
        return add(v1,v2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的和，以字符串格式返回
     */
    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的差
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的差，以字符串格式返回
     */
    public static String subtract(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }



    /**
     * 提供精确的乘法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的积
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    /**
     * 提供精确的乘法运算
     * @param v1 参数1
     * @param v2 参数2
     * @param scale 小数点位数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2 , int scale) {
        return multiply(v1,v2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 参数1
     * @param v2 参数2
     * @return 两个参数的积，以字符串格式返回
     */
    public static String multiply(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    /**
     * 当取完精度后，value在数字上小于、等于或大于baseValue时，返回-1、0或1
     *
     * @param value
     * @param baseValue
     * @param scale
     * @return
     */
    public static int compareTo(double value, double baseValue, int scale) {
        BigDecimal v1 = BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
        BigDecimal v2 = BigDecimal.valueOf(baseValue).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return v1.compareTo(v2);
    }

    /**
     * 当取完精度后，value在数字上小于、等于或大于baseValue时，返回-1、0或1
     *
     * @param value
     * @param baseValue
     * @param scale
     * @return
     */
    public static int compareTo(String value, String baseValue, int scale) {
        BigDecimal v1 = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
        BigDecimal v2 = new BigDecimal(baseValue).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return v1.compareTo(v2);
    }

    /**
     * 原始值比较（未进行精度处理），value在数字上小于、等于或大于baseValue时，返回-1、0或1
     *
     * @param value
     * @param baseValue
     * @return
     */
    public static int simpleCompareTo(double value, double baseValue) {
        BigDecimal v1 = BigDecimal.valueOf(value);
        BigDecimal v2 = BigDecimal.valueOf(baseValue);
        return v1.compareTo(v2);
    }

    /**
     * 原始值比较（未进行精度处理），value在数字上小于、等于或大于baseValue时，返回-1、0或1
     *
     * @param value
     * @param baseValue
     * @return
     */
    public static int simpleCompareTo(String value, String baseValue) {
        BigDecimal v1 = new BigDecimal(value);
        BigDecimal v2 = new BigDecimal(baseValue);
        return v1.compareTo(v2);
    }
}
