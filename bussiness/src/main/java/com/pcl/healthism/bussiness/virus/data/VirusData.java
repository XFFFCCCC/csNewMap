package com.pcl.healthism.bussiness.virus.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VirusData {
    private String date;
    private int diagnosed;
    private int suspect;
    private int death;
    private int cured;
    private List<String> list;
    private List<History> history;
    private List<Area> area;

    @Data
    public static class History {
        private String date;
        private int confirmedNum;
        private int suspectedNum;
        private int curesNum;
        private int deathsNum;
    }

    @Data
    public static class Area {
        private String provinceName;
        private String provinceShortName;
        private String preProvinceName;
        private int confirmedCount;
        private int suspectedCount;
        private int curedCount;
        private int deadCount;
        private String comment;
        private int locationId;
        private List<City> cities;
        private Increased yesterdayIncreased;

    }

    @Data
    public static class City {
        private String cityName;
        private int confirmedCount;
        private int suspectedCount;
        private int curedCount;
        private int deadCount;
    }

    @Data
    public static class Increased {
        private int confirmedCount;
        private int suspectedCount;
        private int curedCount;
        private int deadCount;
    }

}
