package com.pcl.healthism.bussiness.common.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StringTool {

    public static List<Integer> splitTo(String value, String separator) {
        return Stream.of(value.split(separator))
                .filter(l -> !Strings.isBlank(l))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static List<Double> splitToD(String value, String separator) {
        return Stream.of(value.split(separator))
                .filter(l -> !Strings.isBlank(l))
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encode url error, url={}", url, e);
        }
        return "";
    }
}
