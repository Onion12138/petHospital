package com.ecnu.six.pethospital.exam.domain;

import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/24 上午10:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private Integer examId;
    private Integer paperId;
    private String examName;
    private Integer creatorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    private Adm adm;
    private Paper paper;
    private List<QuestionScore> questionScores;
}
