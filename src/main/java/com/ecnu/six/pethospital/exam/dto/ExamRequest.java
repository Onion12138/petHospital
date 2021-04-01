package com.ecnu.six.pethospital.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private Integer paperId;
    private String examName;
    private Integer creatorId;
    private String startTime;

    private Integer usrId;
}
