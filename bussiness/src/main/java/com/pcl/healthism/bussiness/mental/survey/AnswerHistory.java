package com.pcl.healthism.bussiness.mental.survey;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class AnswerHistory {

    private List<Item> answers;

    public AnswerHistory() {
        this.answers = new ArrayList<>();
    }

    public List<Item> getAnswers() {
        return answers;
    }

    public void add(String date, int anxietyScore, int depressionScore) {
        answers.add(new Item(date, anxietyScore, depressionScore));
    }

    @Data
    @AllArgsConstructor
    public static class Item {
        private String date;
        private int anxietyScore;
        private int depressionScore;
    }
}
