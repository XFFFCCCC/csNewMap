package com.pcl.healthism.dao.mapper;

import com.pcl.healthism.dao.po.NcovNewsPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NcovNewsMapper {

    int insert(NcovNewsPO item);

    List<NcovNewsPO> query(@Param("begin") Long begin, @Param("end") Long end);

}
