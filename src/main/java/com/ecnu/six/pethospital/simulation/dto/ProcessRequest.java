package com.ecnu.six.pethospital.simulation.dto;

import com.ecnu.six.pethospital.simulation.domain.Step;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRequest {
    private Integer id;
    private String name;
    private Integer roleId;

    private List<StepRequest> steps;
}
