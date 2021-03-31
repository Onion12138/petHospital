package com.ecnu.six.pethospital.guide.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    private Integer id;
    private String name;
    private String message;
    private String picture;
}