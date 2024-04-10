package com.pcl.healthism.bussiness.mental.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoarseResult {
    private long logId;
    private long userId;
    private Score.Result score;
}
