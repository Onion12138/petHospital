package com.ecnu.six.pethospital.disease.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author onion
 * @date 2021/3/15 -9:48 上午
 */
@Data
@Entity
public class Caze {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String inspection;

    @Column
    private String name;

    @Column
    private String diagnosis;

    @Column
    private String treatment;

    @Column
    private Boolean valid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column
    private LocalDateTime createTime;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime updateTime;
}
