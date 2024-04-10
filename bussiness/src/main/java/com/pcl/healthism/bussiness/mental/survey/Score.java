package com.pcl.healthism.bussiness.mental.survey;

import com.google.common.base.Preconditions;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Score {
    public enum  Coarse {
        Q1(1, 4),
        Q2(2, 5),
        Q3(3, 4),
        Q4(4, 4),
        Q5(5, 4),
        Q6(6, 5),
        Q7(7, 5),
        Q8(8, 5),
        Q9(9, 4);

        Coarse(int id, int okId) {
            this.id = id;
            this.okId = okId;
        }

        private int id;
        private int okId;

        private static Map<Integer, Coarse> enumMap;

        static {
            enumMap = Stream.of(values()).collect(Collectors.toMap(l -> l.id, l -> l));
        }

        public static Result compute(List<Answer> answers) {
            Preconditions.checkArgument(answers.size() == 9, "答案对应的问题数目错误");
            int id = 0;
            int sum = 0;
            int []score = new int[9];
            for (Answer answer: answers) {
                Preconditions.checkArgument(enumMap.containsKey(answer.getId()), "问题id编号错误");
                Preconditions.checkArgument( answer.getOptions().size() <=5, "选型数量错误");
                Coarse it = enumMap.get(answer.getId());
                score[id] = answer.getOptions().stream().mapToInt(i -> i.equals(it.okId) ? 0 : 1).sum();
                sum += score[id];
                ++id;
            }
            Result result = new Result();
            result.setTotal(sum);
            result.setBody(score[6] + score[7]);
            result.setBehavior(score[4] + score[5]);
            result.setMind(score[2] + score[3]);
            result.setEmotion(score[0] + score[1]);
            result.setSocial(score[8]);
            return result;
        }

    }

    @Data
    public static class Result {
        private int total;
        private int emotion;
        private int body;
        private int mind;
        private int behavior;
        private int social;
    }

    public enum  Anxiety {
        ;
        public static int compute(List<Answer> answers) {
            Preconditions.checkArgument(answers.size() == 7, "答案对应的问题数目错误");
            answers.forEach(l -> Preconditions.checkArgument(l.getOptions().size() == 1, "选项数量错误"));
            return answers.stream().flatMap(l -> l.getOptions().stream())
                    .mapToInt(i -> i -1)
                    .sum();
        }
    }

    public enum  Depression {
        ;
        public static int compute(List<Answer> answers) {
            Preconditions.checkArgument(answers.size() == 9, "答案对应的问题数目错误");
            answers.forEach(l -> Preconditions.checkArgument(l.getOptions().size() == 1, "选项数量错误"));
            return answers.stream().flatMap(l -> l.getOptions().stream())
                    .mapToInt(i -> i -1)
                    .sum();
        }

    }

}
