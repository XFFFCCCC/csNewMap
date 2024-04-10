package com.pcl.healthism.bussiness.virus.news;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pcl.healthism.bussiness.common.tools.JsonTool;
import com.pcl.healthism.bussiness.common.tools.SignTool;
import com.pcl.healthism.dao.jdbc.InitDB;
import com.pcl.healthism.dao.mapper.NcovNewsMapper;
import com.pcl.healthism.dao.po.NcovNewsPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@DependsOn("initDB")
@Slf4j
public class NewsService {

    private final NcovNewsMapper ncovNewsMapper;
    private final NewsSortService sortService;

    private Map<Long, News> allNews;
    private Cache<String, Integer> duplicateCache =CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    @PostConstruct
    private void init() {
        allNews = ncovNewsMapper.query(null, null).stream().map(l -> {
            News news = new News();
            BeanUtils.copyProperties(l, news);
            return news;
        }).collect(Collectors.toMap(News::getId, l -> l));
    }

    public void submit(News news) {
        checkArgument(news);
        checkRepeat(news);
        NcovNewsPO newsPO = from(news);
        newsPO.setAddTimestampMils(System.currentTimeMillis());
        newsPO.setModTimestampMils(System.currentTimeMillis());
        int affectRows = ncovNewsMapper.insert(newsPO);
        if (affectRows <= 0) {
            throw new IllegalStateException("保存失败!");
        }
        news.setId(newsPO.getId());
        addNews(news);
    }

    private synchronized void addNews(News news) {
        allNews.put(news.getId(), news);
    }

    public List<News> query(NewsQuery query) {
        log.info("###newsquery###,query={}", query);
        return sortService.sort(allNews, query.getLongitude(), query.getLatitude());
    }

    private void checkArgument(News news) {
        Preconditions.checkState(news.getTitle() != null && news.getTitle().length() <= 24, "标题不为空且不能超过24个字");
        Preconditions.checkState(news.getContent() != null && news.getContent().length() <= 64, "内容过短或过长");
        Preconditions.checkState(news.getUrl() != null && news.getUrl().length() > 5, "新闻原始域名不正确");
        Preconditions.checkState(news.getLatitude() > 15.0, "纬度不正确");
        Preconditions.checkState(news.getLongitude() > 90.0, "经度不正确");
        Preconditions.checkState(news.getTimestamp() > 1578635299000L, "请收集1月10号之后的新闻");
        if (news.getContributory() == null) {
            news.setContributory("");
        }
    }

    private NcovNewsPO from(News news) {
        NcovNewsPO newsPO = new NcovNewsPO();
        BeanUtils.copyProperties(news, newsPO, "id");
        return newsPO;
    }

    private void checkRepeat(News news) {
        String signature = SignTool.sign(JsonTool.serialize(news));
        if (duplicateCache.getIfPresent(signature) != null) {
            throw new IllegalArgumentException("重复提交");
        }
        if (allNews.values().stream().anyMatch(l -> l.getUrl().equals(news.getUrl()))) {
            throw new IllegalArgumentException("重复提交");
        }
        duplicateCache.put(signature, 0);
    }
}
