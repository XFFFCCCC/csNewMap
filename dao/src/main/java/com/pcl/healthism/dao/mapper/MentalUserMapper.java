package com.pcl.healthism.dao.mapper;

import com.pcl.healthism.dao.po.MentalAnswerPO;
import com.pcl.healthism.dao.po.MentalUserInfoPO;
import org.apache.ibatis.annotations.Param;

public interface MentalUserMapper {

    int insert(MentalUserInfoPO item);

    MentalUserInfoPO query(@Param("userId") long userId);

    int update(MentalUserInfoPO item);

}
