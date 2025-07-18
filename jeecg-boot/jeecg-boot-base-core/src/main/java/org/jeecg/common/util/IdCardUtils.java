package org.jeecg.common.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class IdCardUtils {

    /**
     * 根据身份证号获取年龄
     * @param idCard 身份证号码
     * @return 年龄
     */
    public static int getAgeFromIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("身份证号不合法");
        }

        String birthDateStr = idCard.substring(6, 14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * 根据身份证号获取性别
     * @param idCard 身份证号码
     * @return 性别(1:男, 2:女)
     */
    public static int getGenderFromIdCard(String idCard) {
        if (idCard == null || idCard.length() != 18) {
            throw new IllegalArgumentException("身份证号不合法");
        }

        // 第17位数字表示性别，奇数为男，偶数为女
        char genderChar = idCard.charAt(16);
        return (genderChar % 2 == 1) ? 1 : 2;
    }

    /**
     * 根据身份证号获取性别描述
     * @param idCard 身份证号码
     * @return 性别描述("男"或"女")
     */
    public static String getGenderDescFromIdCard(String idCard) {
        return getGenderFromIdCard(idCard) == 1 ? "男" : "女";
    }
}