package com.ecnu.six.pethospital.simulation.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepResponse {
    private Integer id;
    private String name;
    private String message;
    private String picture;
    private String video;
}
