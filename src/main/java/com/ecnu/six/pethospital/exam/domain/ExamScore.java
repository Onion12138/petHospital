package com.ecnu.six.pethospital.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamScore {
    private Integer examId;
    private Integer usrId;
    private Integer score;
}
