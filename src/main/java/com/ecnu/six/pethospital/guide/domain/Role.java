package com.ecnu.six.pethospital.guide.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;
    private String name;
    private String picture;
}
