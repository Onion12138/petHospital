package com.ecnu.six.pethospital.disease.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author onion
 * @date 2021/3/22 -8:07 上午
 */
@Data
@Entity
public class Disease {
    @Id
    @GeneratedValue
    private Long diseaseId;

    @Column
    private String name;

    @Column
    private Long parent;
}
