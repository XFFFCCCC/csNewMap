package com.pcl.healthism.web.controller;

import com.pcl.healthism.bussiness.common.tools.DateTool;
import com.pcl.healthism.bussiness.common.tools.IPTool;
import com.pcl.healthism.bussiness.virus.data.GlobalDataService;
import com.pcl.healthism.bussiness.virus.news.News;
import com.pcl.healthism.bussiness.virus.news.NewsQuery;
import com.pcl.healthism.bussiness.virus.news.NewsService;
import com.pcl.healthism.bussiness.virus.poi.PoiService;
import com.pcl.healthism.dao.jdbc.VirusDataJdbc;
import com.pcl.healthism.web.controller.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class DataController {

    private final GlobalDataService globalDataService;
    private final PoiService poiService;
    private final NewsService newsService;
    private final VirusDataJdbc virusDataJdbc;

    @GetMapping("/query/poi")
    public Object queryEmotion(HttpServletRequest request) {
        return poiService.get();
    }

    @GetMapping("/query/global")
    public Object queryFood(HttpServletRequest request) {
       return globalDataService.get();
    }

    @PostMapping("/news/submit")
    public Object submitNews(@RequestBody News news) {
        newsService.submit(news);
        return Response.success("ok");
    }

    @GetMapping("/news/query")
    public Object queryNews(NewsQuery query) {
        return newsService.query(query);
    }

    @GetMapping("/news/statistic")
    public Object newsStatistic(@RequestParam("date") String date) {
        long begin = DateTool.fromYMD(date).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
        long end = begin + 86400 *1000L;
        return virusDataJdbc.queryStatistic(begin, end);
    }

}
