package com.pcl.healthism.bussiness.common.tools;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberTool {
    private static final Map<Character, Integer> unitChineseNumber = Stream.of(new Object[][]{
            {'零', 0}, {'一', 1}, {'二', 2}, {'两', 2}, {'俩', 2}, {'三', 3}, {'仨', 3},
            {'四', 4}, {'五', 5}, {'六', 6}, {'七', 7}, {'八', 8}, {'九', 9}
    }).collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

    private static final Map<Character, Integer> multiChineseNumber = Stream.of(new Object[][]{
            {'百', 100}, {'千', 1000}, {'万', 10000}, {'亿', 100000000}
    }).collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

    public static double getDoubleFromLiteral(String numberStr) {
        try {
            return Double.valueOf(numberStr);
        } catch (NumberFormatException e) {
            if (numberStr.equals("半")) return 0.5;
            return getChineseInt(numberStr);
        }
    }

    public static int getIntegerFromLiteral(String numberStr) {
        try {
            return Integer.valueOf(numberStr);
        } catch (NumberFormatException e) {
            return getChineseInt(numberStr);
        }
    }

    private static int getChineseInt(String numberStr) {
        int integer = 0;
        boolean unit = false;
        for (char c : numberStr.toCharArray()) {
            if (c == '十') {
                if (integer == 0) integer += 10;
                else integer *= 10;
                unit = false;
            } else if (unitChineseNumber.containsKey(c)) {
                if (!unit) {
                    integer += unitChineseNumber.get(c);
                    unit = true;
                }
            } else if (multiChineseNumber.containsKey(c)) {
                integer *= multiChineseNumber.get(c);
                unit = false;
            }
        }
        return integer;
    }
}
