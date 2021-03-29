package com.ecnu.six.pethospital.disease.service.impl;

import com.ecnu.six.pethospital.disease.dao.CaseDao;
import com.ecnu.six.pethospital.disease.domain.Caze;
import com.ecnu.six.pethospital.disease.dto.CaseDTO;
import com.ecnu.six.pethospital.disease.service.CazeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author onion
 * @date 2021/3/22 -9:35 上午
 */
@Service
public class CazeServiceImpl implements CazeService {
    @Autowired
    private CaseDao caseDao;

    @Override
    public Caze findOne(Long caseId) {
        return caseDao.findById(caseId).get();
    }

    @Override
    public Page<Caze> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return caseDao.findAll(pageable);
    }

    @Override
    public void deleteOne(Long caseId) {
        Optional<Caze> optional = caseDao.findById(caseId);
        if(optional.isPresent()) {
            Caze caze = optional.get();
            caze.setValid(false);
            caseDao.save(caze);
        }
    }

    @Override
    public void addOne(@Valid CaseDTO caseDTO) {
        Caze caze = new Caze();
        BeanUtils.copyProperties(caseDTO, caze);
        caze.setValid(true);
        caze.setCreateTime(LocalDateTime.now());
        caze.setUpdateTime(LocalDateTime.now());
        caseDao.save(caze);
    }

    @Override
    public void updateOne(@Valid CaseDTO caseDTO) {
        Caze caze = new Caze();
        BeanUtils.copyProperties(caseDTO, caze);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        caze.setCreateTime(LocalDateTime.parse(caseDTO.getCreateTime(), formatter));
        caze.setUpdateTime(LocalDateTime.now());
        caseDao.save(caze);
    }
}