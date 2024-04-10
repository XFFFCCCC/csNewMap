package com.pcl.healthism.bussiness.mental.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentalUserInfo {
    private long userId;
    private int gender;  // 1男，2女，0未知
    private String ageSection; //年龄区间
    private String district;   //行政区
    private String healthStatus;  //健康状态
}
