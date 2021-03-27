package com.ecnu.six.pethospital.exam.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午7:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperResponse {
    private Integer paperId;
    private String paperName;
    private Integer admId;
    private String admName;
    private Integer duration;
    private String paperCreatedTime;
    private String paperUpdatedTime;
    private List<PaperQuestionResponse> questions;
}
