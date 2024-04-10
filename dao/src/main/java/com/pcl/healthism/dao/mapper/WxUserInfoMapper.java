package com.pcl.healthism.dao.mapper;

import com.pcl.healthism.dao.po.WxUserInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxUserInfoMapper {

    int insert(WxUserInfoPO wxUserInfoPO);

    WxUserInfoPO query(@Param("openId") String openId);

    int update(WxUserInfoPO wxUserInfoPO);
}
