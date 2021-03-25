package com.ecnu.six.pethospital.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午11:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamScoreRequest {
    private Integer examId;
    private Integer usrId;
    private Integer score;
}
