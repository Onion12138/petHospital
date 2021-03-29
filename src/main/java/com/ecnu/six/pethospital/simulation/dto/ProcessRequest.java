package com.ecnu.six.pethospital.simulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer roleId;
}
