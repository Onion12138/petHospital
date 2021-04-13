package com.ecnu.six.pethospital.exam.domain;

import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
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
 * @date 2021/3/24 上午10:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    private Integer paperId;
    private String paperName;
    private Integer admId;
    private Boolean isDeleted;
    private Integer duration;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime paperCreatedTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime paperUpdatedTime;

    private LocalUser adm;
}
