package com.pcl.healthism.bussiness.miniapp.common.entity;

import lombok.Data;

@Data
public class WxUser {

    public final static WxUser NULL = new WxUser();
    private String openId;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;

    //手机号码
    private String userMobile;

    public  boolean emptyUser () {
        return this == NULL;
    }



}
