package com.ecnu.six.pethospital.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午7:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionResponse {
    private Integer questionId;
    private String stem;
    private String[] choices;
    private String answer;
    private Integer score;
}
