package com.ecnu.six.pethospital.disease.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author onion
 * @date 2021/3/22 -8:25 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseVO {
    private Long diseaseId;
    private String name;
    private List<DiseaseVO> children;
}
