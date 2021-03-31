package com.ecnu.six.pethospital.simulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepRequest {
    private Integer processId;
}
