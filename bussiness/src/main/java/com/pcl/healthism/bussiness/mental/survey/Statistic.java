package com.pcl.healthism.bussiness.mental.survey;

import com.google.common.collect.Lists;
import com.pcl.healthism.bussiness.common.tools.JsonTool;
import com.pcl.healthism.dao.jdbc.MentalDataJdbc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@DependsOn("jsonTool")
public class Statistic {

    private final MentalDataJdbc mentalDataJdbc;

    private Map<Long, User> userMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void initData() {
        mentalDataJdbc.queryAllUserScore( row -> {
            long userId = row.getLong("user_id");
            if (!userMap.containsKey(userId)) { //取最新的userId

                long timestamp = row.getLong("timestamp");
                String json = row.getString("precise_answer");
                PreciseSurveyAnswer answer = JsonTool.parse(json, PreciseSurveyAnswer.class);
                userMap.put(userId, new User("", answer.getAnxietyScore() + answer.getDepressionScore(), timestamp));
            }
        });

        List<Long> userIds = new ArrayList<>(userMap.keySet());
        for(List<Long> patch: Lists.partition(userIds, 1000)) {
            mentalDataJdbc.queryDistrict(patch, row -> {
                long userId = row.getLong("user_id");
                userMap.get(userId).district = row.getString("district");
            });
        }
    }

    public void update(Long userId, String district, int totalScore, long timestamp) {
        userMap.put(userId, new User(district, totalScore, timestamp));
    }

    public Item queryRank(Long userId) {
        User user = userMap.get(userId);
        if (user == null) {
            return new Item("", 0, 0);
        }
        //
        List<User> users = userMap.values().stream()
                .filter(l -> l.district.equals(user.district))
                .sorted((a, b) -> a.totalScore == b.totalScore ?
                        (int)(b.timestamp - a.timestamp) : a.totalScore - b.totalScore)
                .collect(Collectors.toList());
        int rank = users.indexOf(user) + 1;
        return new Item(user.district, userMap.size(), rank);
    }

    @AllArgsConstructor
    private static class User {
        private String district;
        private int totalScore;
        private long timestamp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String district;
        private int total;
        private int rank;
    }
}
