package br.com.lynx.util;

import java.math.BigDecimal;

public class MathUtil {

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static float stringToFloat(String value) {
        try {
            value = value.replace(",", ".");

            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}