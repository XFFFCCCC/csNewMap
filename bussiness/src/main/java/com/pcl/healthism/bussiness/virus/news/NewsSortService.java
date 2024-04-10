package com.pcl.healthism.bussiness.virus.news;

import com.pcl.healthism.bussiness.common.tools.DateTool;
import com.pcl.healthism.bussiness.common.tools.GPSTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsSortService {

    List<News> sort(Map<Long, News> allNews, double longitude, double latitude) {
        //过滤超过10天的新闻
        long bTenDays = DateTool.startMilsOfDay(-10);
        List<News> newsList = new ArrayList<>( allNews.values());
        List<Tuple> tuples = newsList.stream()
                .filter(l -> l.getTimestamp() >= bTenDays)
                .map(l -> new Tuple(l.getId(), GPSTool.distance(longitude, latitude, l.getLongitude(), l.getLatitude())))
                .sorted(Comparator.comparingDouble(Tuple::getDist))
                .collect(Collectors.toList());
        return tuples.stream()
                .map(l -> {
                    News news = allNews.get(l.getId());
                    News nn = new News();
                    BeanUtils.copyProperties(news, nn);
                    nn.setDistance(l.getDist());
                    return nn;
                }).limit(20).collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private static class Tuple {
        private long id;
        private double dist;
    }
}
