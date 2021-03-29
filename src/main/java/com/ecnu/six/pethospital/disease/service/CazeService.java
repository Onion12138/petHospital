package com.ecnu.six.pethospital.disease.service;

import com.ecnu.six.pethospital.disease.domain.Caze;
import com.ecnu.six.pethospital.disease.dto.CaseDTO;
import org.springframework.data.domain.Page;

/**
 * @author onion
 * @date 2021/3/22 -9:35 上午
 */
public interface CazeService {
    Caze findOne(Long caseId);

    Page<Caze> findAll(Integer page, Integer size);

    void deleteOne(Long caseId);

    void addOne(CaseDTO caseDTO);

    void updateOne(CaseDTO caseDTO);
}
