package com.pcl.healthism.dao.po;

import lombok.Data;

@Data
public class WxUserInfoPO {
    private long id;
    private String openId;
    private String userMobile=""; // 用户手机号
    private String nickName="";
    private String gender="";  //性别表示：1，2等数字. 0未知
    private String language="";
    private String city="";
    private String province="";
    private String country="";
    private String avatarUrl="";
    private String unionId="";
    private long addTimestampMils;        //添加时间戳
    private long modTimestampMils;        //修改时间戳
}
