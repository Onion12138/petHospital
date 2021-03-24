package com.ecnu.six.pethospital.exam.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author HavenTong
 * @date 2021/3/24 上午10:47
 */
@Data
public class Question {
    private Integer questionId;
    private Integer admId;
    private Boolean isDeleted;
    private Integer diseaseId;
    private String stem;
    private String option;
    private String answer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime questionCreatedTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime questionUpdatedTime;
}
