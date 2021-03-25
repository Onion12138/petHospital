package com.ecnu.six.pethospital.exam.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HavenTong
 * @date 2021/3/24 下午8:21
 */
@Data
@Builder
public class QuestionRequest {
    private Integer questionId;
    private Integer admId;
    private Integer diseaseId;
    private String stem;
    private String choice;
    private String answer;
    private String keyword;
}
