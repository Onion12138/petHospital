package com.ecnu.six.pethospital.disease.service.impl;

import com.ecnu.six.pethospital.disease.dao.DiseaseDao;
import com.ecnu.six.pethospital.disease.domain.Disease;
import com.ecnu.six.pethospital.disease.service.DiseaseService;
import com.ecnu.six.pethospital.disease.vo.DiseaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author onion
 * @date 2021/3/22 -8:11 上午
 */
@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    private DiseaseDao diseaseDao;

    @Override
    public List<DiseaseVO> findAll() {
        List<Disease> diseaseList = diseaseDao.findAll();
        List<DiseaseVO> diseaseVOList = new ArrayList<>();
        for (Disease disease : diseaseList) {
            DiseaseVO diseaseVO = new DiseaseVO();
            if (disease.getParent() == -1) {
                diseaseVO.setDiseaseId(disease.getDiseaseId());
                diseaseVO.setName(diseaseVO.getName());
                diseaseVO.setChildren(diseaseList.stream().filter(e->e.getParent().equals(disease.getDiseaseId()))
                        .map(this::transformVO).collect(Collectors.toList()));
            }
        }
        return diseaseVOList;
    }
    private DiseaseVO transformVO(Disease disease) {
        return new DiseaseVO(disease.getDiseaseId(), disease.getName(), null);
    }
}
