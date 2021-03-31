package com.ecnu.six.pethospital.guide.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Integer id;
    private String name;
    private Integer pos1X;
    private Integer pos1Y;
    private Integer pos2X;
    private Integer pos2Y;
    private String picture;
    private Integer principalId;
    private Boolean isPharmacy;
    private String message;

    private Role principal;
}
