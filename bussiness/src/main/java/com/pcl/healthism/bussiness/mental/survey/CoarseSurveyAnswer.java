package com.pcl.healthism.bussiness.mental.survey;

import lombok.Data;

import java.util.List;

@Data
public class CoarseSurveyAnswer {
    private long userId;
    List<Answer> answers;
    private Score.Result score;
}
