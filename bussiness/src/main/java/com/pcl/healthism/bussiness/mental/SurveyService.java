package com.pcl.healthism.bussiness.mental;

import com.google.common.base.Preconditions;
import com.pcl.healthism.bussiness.common.tools.DateTool;
import com.pcl.healthism.bussiness.common.tools.JsonTool;
import com.pcl.healthism.bussiness.common.tools.StringTool;
import com.pcl.healthism.bussiness.mental.info.MentalUserInfo;
import com.pcl.healthism.bussiness.mental.survey.*;
import com.pcl.healthism.dao.mapper.MentalAnswerMapper;
import com.pcl.healthism.dao.mapper.MentalUserMapper;
import com.pcl.healthism.dao.po.MentalAnswerPO;
import com.pcl.healthism.dao.po.MentalUserInfoPO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final MentalUserMapper mentalUserMapper;
    private final MentalAnswerMapper mentalAnswerMapper;
    private final Statistic statistic;

    private final static DateTimeFormatter MM_DD = DateTimeFormatter.ofPattern("M月dd日");

    public CoarseResult submitCoarseSurvey(CoarseSurveyAnswer answer) {
        Score.Result score = Score.Coarse.compute(answer.getAnswers());
        answer.setScore(score);
        MentalAnswerPO answerPO = new MentalAnswerPO();
        answerPO.setCoarseAnswer(JsonTool.serialize(answer));
        answerPO.setUserId(answer.getUserId());
        answerPO.setTimestamp(System.currentTimeMillis());
        answerPO.setPreciseAnswer("");
        mentalAnswerMapper.insert(answerPO);
        return new CoarseResult(answerPO.getId(), answer.getUserId(), score);
    }

    public AnswerHistory queryUserAnswers(long userId) {
        List<MentalAnswerPO> answerPOS = mentalAnswerMapper.queryByUserId(userId);
        answerPOS.sort((a, b) -> (int)(b.getTimestamp() - a.getTimestamp()));
        Set<String> dates = new HashSet<>();
        AnswerHistory history = new AnswerHistory();
        answerPOS.stream().filter(l -> !StringTool.isEmpty(l.getPreciseAnswer())).forEach(l -> {
            String date = DateTool.fromMils(l.getTimestamp()).format(MM_DD);
            if (dates.add(date)) {
                PreciseSurveyAnswer answer = JsonTool.parse(l.getPreciseAnswer(), PreciseSurveyAnswer.class);
                history.add(date, answer.getAnxietyScore(), answer.getDepressionScore());
            }
        });
        return history;
    }


    public PreciseResult submitPreciseSurvey(PreciseSurveyAnswer answer) {
        MentalAnswerPO answerPO = mentalAnswerMapper.query(answer.getLogId());
        Preconditions.checkArgument(answerPO != null, "记录不存在");
        int anxietyScore = Score.Anxiety.compute(answer.getAnxietyAnswers());
        int depressionScore = Score.Depression.compute(answer.getDepressionAnswers());
        answer.setAnxietyScore(anxietyScore);
        answer.setDepressionScore(depressionScore);
        answerPO.setPreciseAnswer(JsonTool.serialize(answer));
        mentalAnswerMapper.update(answerPO);

        PreciseResult preciseResult = new PreciseResult();
        preciseResult.setUserId(answer.getUserId());
        preciseResult.setAnxietyScore(anxietyScore);
        preciseResult.setDepressionScore(depressionScore);
        MentalUserInfoPO userInfo = mentalUserMapper.query(answerPO.getUserId());
        statistic.update(answerPO.getUserId(), userInfo.getDistrict(),anxietyScore + depressionScore, answerPO.getTimestamp());
        preciseResult.setRankInfo(statistic.queryRank(answerPO.getUserId()));
        return preciseResult;
    }

    public MentalUserInfo query(long userId) {
        MentalUserInfoPO  userInfoPO = mentalUserMapper.query(userId);
        if (userInfoPO != null) {
            MentalUserInfo userInfo = new MentalUserInfo();
            BeanUtils.copyProperties(userInfoPO, userInfo);
            return userInfo;
        }
        throw new IllegalArgumentException("用户不存在");
    }

    public void submitUserInfo(MentalUserInfo userInfo) {
        MentalUserInfoPO userInfoPO = mentalUserMapper.query(userInfo.getUserId());
        if (userInfoPO == null) {
            userInfoPO = new MentalUserInfoPO();
            BeanUtils.copyProperties(userInfo, userInfoPO);
            userInfoPO.setAddTimestampMils(System.currentTimeMillis());
            userInfoPO.setModTimestampMils(System.currentTimeMillis());
            mentalUserMapper.insert(userInfoPO);
        } else {
            userInfoPO.setHealthStatus(userInfo.getHealthStatus());
            userInfoPO.setModTimestampMils(System.currentTimeMillis());
            mentalUserMapper.update(userInfoPO);
        }
    }
}
