package com.pcl.healthism.bussiness.mental.survey;

import lombok.Data;

import java.util.List;

@Data
public class PreciseSurveyAnswer {
    private long logId;
    private long userId;
    private List<Answer> anxietyAnswers;
    private List<Answer> depressionAnswers;

    private int anxietyScore;
    private int depressionScore;
}
