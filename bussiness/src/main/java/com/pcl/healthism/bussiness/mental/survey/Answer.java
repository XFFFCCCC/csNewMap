package com.pcl.healthism.bussiness.mental.survey;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    private int id;
    List<Integer> options;
}
