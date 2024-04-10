package com.pcl.healthism.dao.po;

import lombok.Data;

@Data
public class MentalUserInfoPO {
    private long id;
    private long userId;
    private int gender;  // 1男，2女，0未知
    private String ageSection; //年龄区间
    private String district;   //行政区
    private String healthStatus;  //健康状态
    private long addTimestampMils;        //添加时间戳
    private long modTimestampMils;        //修改时间戳
}
