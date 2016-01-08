package com.polarbear.util.money;

import java.math.BigDecimal;

public class Arith {
    public static double add(double n1, double n2) {
        return new BigDecimal(n1).add(new BigDecimal(n2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double multiply(double n1, double n2) {
        return new BigDecimal(n1).multiply(new BigDecimal(n2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}