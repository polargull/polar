package com.polarbear.util;

public class RandomUtil {

    public static int getRegisterVerificationCode() {
        return randomNum(666666, 888888);
    }

    // 公式：(上界-下界+1)*rnd+下界
    public static int randomNum(int min, int max) {
        return (int) ((max - min + 1) * Math.random() + min);
    }
}
