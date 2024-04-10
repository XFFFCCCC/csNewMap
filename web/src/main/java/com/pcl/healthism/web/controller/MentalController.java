package com.pcl.healthism.web.controller;


import com.pcl.healthism.bussiness.mental.MentalIdService;
import com.pcl.healthism.bussiness.mental.SurveyService;
import com.pcl.healthism.bussiness.mental.info.MentalUserInfo;
import com.pcl.healthism.bussiness.mental.survey.CoarseResult;
import com.pcl.healthism.bussiness.mental.survey.CoarseSurveyAnswer;
import com.pcl.healthism.bussiness.mental.survey.PreciseResult;
import com.pcl.healthism.bussiness.mental.survey.PreciseSurveyAnswer;
import com.pcl.healthism.bussiness.virus.news.News;
import com.pcl.healthism.dao.po.MentalId;
import com.pcl.healthism.web.controller.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mental")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@Slf4j
public class MentalController {
    private final MentalIdService mentalIdService;
    private final SurveyService surveyService;

    @PostMapping("/id/generate")
    public long idGenerate() {
        return mentalIdService.generate();
    }

    @PostMapping("/survey/coarse")
    public Object coarseSurvey(@RequestBody CoarseSurveyAnswer answer) {
        return  Response.success(surveyService.submitCoarseSurvey(answer));
    }

    @PostMapping("/survey/precise")
    public Object preciseSurvey(@RequestBody PreciseSurveyAnswer answer) {
        return Response.success(surveyService.submitPreciseSurvey(answer));
    }

    @GetMapping("/survey/precise/history")
    public Object preciseSurveyHistory(@RequestParam("userId") long userId) {
        return Response.success(surveyService.queryUserAnswers(userId));
    }

    @PostMapping("/user/info")
    public Object preciseSurvey(@RequestBody MentalUserInfo userInfo) {
        surveyService.submitUserInfo(userInfo);
        return Response.success(null);
    }

    @GetMapping("/user/info")
    public Object queryUserInfo(@RequestParam("userId") long userId) {
        return Response.success(surveyService.query(userId));
    }
}
