package com.ecnu.six.pethospital.exam.dto;

import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperRequest {
    private Integer paperId;
    private String paperName;
    private Integer admId;
    private Integer duration;
    private List<QuestionScore> questionScores;
}
