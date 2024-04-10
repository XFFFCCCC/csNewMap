package com.pcl.healthism.bussiness.common.tools;

import static com.pcl.healthism.bussiness.common.tools.NumberTool.getIntegerFromLiteral;

public class WeightTool {
    private static final String[] KILOGRAMS = {"kg", "千克", "公斤", "L", "升", "公升"};
    private static final String[] GRAMS = {"g", "克", "ml", "cc", "毫升"};


    public static int parseWeight(String weightText) {
        int ratio =1;
        for (String kilogram : KILOGRAMS) {
            if (weightText.endsWith(kilogram)) {
                weightText = weightText.substring(0, weightText.length() - kilogram.length());
                ratio = 1000;
            }
        }
        for (String gram : GRAMS) {
            if (weightText.endsWith(gram)) {
                weightText = weightText.substring(0, weightText.length() - gram.length());
                ratio = 1;
            }
        }
        if (weightText.endsWith("m") && ratio == 1000) {
            weightText = weightText.substring(0, weightText.length() - 1);
            ratio = 1;
        }
        if (weightText.endsWith("斤")) {
            weightText = weightText.substring(0, weightText.length() - 1);
            ratio = 500;
        }
        return getIntegerFromLiteral(weightText.trim()) * ratio;
    }

}
