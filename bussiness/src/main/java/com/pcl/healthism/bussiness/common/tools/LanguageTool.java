package com.pcl.healthism.bussiness.common.tools;

import com.google.common.base.Strings;
import com.pcl.healthism.bussiness.common.Pair;

import java.util.regex.Pattern;

public class LanguageTool {
    private static final String REGEX_MARK = "([\\s-,.?:;'\"!`])|([\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5])";
    private static final Pattern PATTERN_MARK = Pattern.compile(REGEX_MARK);
    private static final Pair<String, String>[] WRONG_PAIRS = new Pair[]{
            new Pair("上一部", "上一步"),
            new Pair("奉爪", "凤爪")
    };

    public static boolean isYes(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return false;
        }
        return content.contains("是")
                || content.contains("确认")
                || content.equals("y")
                || content.equals("Y")
                || content.equals("yes")
                ||content.equals("Yes");
    }

    public static boolean isNo(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return false;
        }
        return content.equals("否")
                || content.equals("n")
                || content.equals("N")
                || content.equals("No")
                ||content.equals("no");
    }

    public static  boolean isCancel(String content) {
        content = LanguageTool.removeUselessChars(content);
        if (content.equals("取消")) {
            return true;
        }
        return false;
    }

    /**
     * 移除标点符号和空白符号
     */
    public static String removeUselessChars(String content) {
        return PATTERN_MARK.matcher(content).replaceAll("");
    }

    public static boolean isGoBack(String content) {
        content = LanguageTool.removeUselessChars(content);
        if (content.equals("回退")
                || content.equals("退回")
                || content.equals("back")
                || content.equals("上一步")
                || content.equals("回去")
                || content.equals("返回")) {
            return true;
        }
        return false;
    }

    public static String cleanVoiceError(String content) {
        if (Strings.isNullOrEmpty(content)) {
            return content;
        }
        for (Pair<String, String> one : WRONG_PAIRS) {
            content = content.replace(one.getLeft(), one.getRight());
        }
        return content;
    }
}
