package com.pcl.healthism.bussiness.mental;

import com.pcl.healthism.dao.mapper.MentalIdMapper;
import com.pcl.healthism.dao.po.MentalId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentalIdService {

    private final MentalIdMapper mentalIdMapper;

    public long generate() {
        MentalId mentalId = new MentalId();
        mentalIdMapper.insert(mentalId);
        return mentalId.getId();
    }
}
