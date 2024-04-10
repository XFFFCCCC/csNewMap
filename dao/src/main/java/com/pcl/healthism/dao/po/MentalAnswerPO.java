package com.pcl.healthism.dao.po;

import lombok.Data;

@Data
public class MentalAnswerPO {
    private long id;
    private long userId;
    private String coarseAnswer;
    private String preciseAnswer;
    private long timestamp;
}
