package com.ecnu.six.pethospital.disease.service.impl;

import com.ecnu.six.pethospital.disease.dao.CaseDao;
import com.ecnu.six.pethospital.disease.domain.Case;
import com.ecnu.six.pethospital.disease.service.CaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author onion
 * @date 2021/3/22 -9:35 上午
 */
@Service
public class CaseService implements CaseServiceImpl {
    @Autowired
    private CaseDao caseDao;

    @Override
    public Case findOne(Long caseId) {
        return caseDao.findById(caseId).get();
    }

    @Override
    public Page<Case> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return caseDao.findAll(pageable);
    }

    @Override
    public void deleteOne(Long caseId) {
        caseDao.deleteById(caseId);
    }

    @Override
    public void addOne(Case caze) {
        caseDao.save(caze);
    }
}
