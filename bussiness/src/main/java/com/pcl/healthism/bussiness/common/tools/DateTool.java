package com.pcl.healthism.bussiness.common.tools;

import com.google.common.base.Preconditions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTool {

    private final static DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 从例如2019-11获取他的unix时间戳秒
     */
    public static long fromYearMonthToSecs(String str) {
        List<Integer> values = StringTool.splitTo(str, "-");
        Preconditions.checkArgument(values.size() >= 2, "params should separator with -, actually" + str);
        if (values.size() == 2) {
            return toSeconds(values.get(0), values.get(1));
        } else {
            return toSeconds(values.get(0), values.get(1), values.get(2));
        }
    }

    public static long toSeconds(int year, int month, int day) {
        return LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public static long toSeconds(int year, int month) {
        return toSeconds(year, month, 1);
    }

    public static long toMils(int year, int month) {
        return toSeconds(year, month) * 1000L;
    }

    public static LocalDateTime fromSecs(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime fromMils(long mils) {
        return Instant.ofEpochMilli(mils).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long startMilsOfDay(int offset) {
        return LocalDate.now().plusDays(offset)
                .atStartOfDay(ZoneId.systemDefault())
                .toEpochSecond() * 1000L;
    }

    public static int toYYYYMMDD(LocalDateTime dateTime) {
        return Integer.valueOf(dateTime.format(YYYYMMDD));
    }

    /**
     * 转换成yyyy-MM-dd
     */
    public static String toYMDstr(LocalDateTime dateTime) {
        return YYYY_MM_DD.format(dateTime);
    }

    public static LocalDateTime fromYMD(String yMd) {
        return LocalDate.parse(yMd, YYYY_MM_DD).atStartOfDay();
    }

}
