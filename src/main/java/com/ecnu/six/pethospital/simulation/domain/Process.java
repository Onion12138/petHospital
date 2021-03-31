package com.ecnu.six.pethospital.simulation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    private Integer id;
    private String name;
    private Integer roleId;
}
