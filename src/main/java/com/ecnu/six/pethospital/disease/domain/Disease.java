package com.ecnu.six.pethospital.disease.domain;

import lombok.Data;

/**
 * @author onion
 * @date 2021/3/22 -8:07 上午
 */
@Data
public class Disease {
    private Long diseaseId;
    private String name;
    private Long parent;
}
