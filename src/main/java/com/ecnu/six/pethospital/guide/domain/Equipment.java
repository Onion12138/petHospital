package com.ecnu.six.pethospital.guide.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    private Integer id;
    private String name;
    private String message;
    private Integer posX;
    private Integer posY;
    private String picture;
    private Integer departmentId;
}
