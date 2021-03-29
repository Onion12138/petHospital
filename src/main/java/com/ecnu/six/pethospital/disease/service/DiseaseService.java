package com.ecnu.six.pethospital.disease.service;

import com.ecnu.six.pethospital.disease.vo.DiseaseVO;

import java.util.List;

/**
 * @author onion
 * @date 2021/3/22 -8:10 上午
 */
public interface DiseaseService {
    List<DiseaseVO> findAll();

    void addOne(String name, Long parent);

    void updateOne(Long diseaseId, String name, Long parent);

    void deleteOne(Long diseaseId);
}
