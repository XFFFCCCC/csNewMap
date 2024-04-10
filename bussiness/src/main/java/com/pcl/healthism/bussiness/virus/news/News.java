package com.pcl.healthism.bussiness.virus.news;

import lombok.Data;

import java.security.Signature;

@Data
public class News {
    private long id;
    private String title;
    private String url;
    private String content;
    private String contributory;
    private long timestamp;
    private double latitude;
    private double longitude;
    private double distance;

}
