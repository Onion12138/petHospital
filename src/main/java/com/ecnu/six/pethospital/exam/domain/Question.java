package com.ecnu.six.pethospital.exam.domain;

import com.ecnu.six.pethospital.disease.domain.Disease;
import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author HavenTong
 * @date 2021/3/24 上午10:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer questionId;
    private Integer admId;
    private Boolean isDeleted;
    private Integer diseaseId;
    private String stem;
    private String choice;
    private String answer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime questionCreatedTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime questionUpdatedTime;

    private Disease disease;
    private LocalUser adm;
}
