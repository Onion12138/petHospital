package com.ecnu.six.pethospital.disease.service;

import com.ecnu.six.pethospital.disease.domain.Case;
import org.springframework.data.domain.Page;

/**
 * @author onion
 * @date 2021/3/22 -9:35 上午
 */
public interface CaseServiceImpl {
    Case findOne(Long caseId);

    Page<Case> findAll(Integer page, Integer size);

    void deleteOne(Long caseId);

    void addOne(Case caze);
}
