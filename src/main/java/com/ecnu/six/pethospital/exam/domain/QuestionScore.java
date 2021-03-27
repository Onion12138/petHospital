package com.ecnu.six.pethospital.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionScore {
    private Integer paperId;
    private Integer questionId;
    private Integer score;

    private Paper paper;
    private Question question;
}
