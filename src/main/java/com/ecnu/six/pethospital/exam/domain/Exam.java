package com.ecnu.six.pethospital.exam.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author HavenTong
 * @date 2021/3/24 上午10:54
 */
@Data
public class Exam {
    private Integer examId;
    private Integer paperId;
    private String examName;
    private Integer creatorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
}
