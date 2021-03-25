package com.ecnu.six.pethospital.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {
    private Integer examId;
    private Integer paperId;
    private String examName;
    private Integer admId;
    private String admName;
    private String startTime;
    private String endTime;
}
