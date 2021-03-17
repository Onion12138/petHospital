package com.ecnu.six.pethospital.disease.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author onion
 * @date 2021/3/15 -9:48 上午
 */
@Data
public class Case {
    @Id
    private Integer id;

    private String inspection;

    private String name;

    private String diagnose;

    private String treatment;
}
