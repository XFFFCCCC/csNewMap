package com.pcl.healthism.bussiness.mental.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreciseResult {
    private long userId;
    private int anxietyScore;
    private int depressionScore;
    Statistic.Item rankInfo;
}
