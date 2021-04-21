package com.ecnu.six.pethospital.exam.vo;

import com.ecnu.six.pethospital.exam.domain.Question;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 上午12:29
 */
@Data
@Builder
public class QuestionResponse {
    private Integer questionId;
    private Integer admId;
    private String admName;
    private Long diseaseId;
    private String diseaseName;
    private String stem;
    private String[] choices;
    private String answer;
    private String questionCreatedTime;
    private String questionUpdatedTime;

    public static QuestionResponse fromQuestion(Question question) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .admId(question.getAdm().getId())
                .admName(question.getAdm().getNickName())
                .diseaseId(question.getDisease().getDiseaseId())
                .diseaseName(question.getDisease().getName())
                .stem(question.getStem())
                .choices(question.getChoice().split("\\|"))
                .answer(question.getAnswer())
                .questionCreatedTime(formatter.format(question.getQuestionCreatedTime()))
                .questionUpdatedTime(formatter.format(question.getQuestionUpdatedTime()))
                .build();
    }
}
