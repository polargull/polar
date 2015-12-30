package com.polarbear.util;

public class RandomUtil {

    public static int getRegisterVerificationCode() {
        return (int)randomNum(666666, 888888);
    }

    public static long getMockCellPhone() {
        return randomNum(13600000000l, 13611265999l);
    }

    // 公式：(上界-下界+1)*rnd+下界
    public static long randomNum(long min, long max) {
        return (long) ((max - min + 1) * Math.random() + min);
    }
    
    public static void main(String[] args) {
        System.out.println(getMockCellPhone());
    }
}
