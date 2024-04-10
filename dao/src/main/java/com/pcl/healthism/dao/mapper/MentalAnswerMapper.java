package com.pcl.healthism.dao.mapper;

import com.pcl.healthism.dao.po.MentalAnswerPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MentalAnswerMapper {

    int insert(MentalAnswerPO item);

    MentalAnswerPO query(@Param("id") long id);

    int update(MentalAnswerPO answerPO);

    List<MentalAnswerPO> queryByUserId(@Param("userId") long userId);
}
