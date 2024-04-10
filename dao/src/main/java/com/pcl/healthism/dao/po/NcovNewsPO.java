package com.pcl.healthism.dao.po;

import lombok.Data;


/**
 * 新冠疫情新闻
 */
@Data
public class NcovNewsPO {
    private long id;
    private String title;
    private String url;
    private String content;
    private String contributory;
    private long timestamp;
    private double latitude;
    private double longitude;
    private int committed;
    private long addTimestampMils;        //添加时间戳
    private long modTimestampMils;        //修改时间戳
}
