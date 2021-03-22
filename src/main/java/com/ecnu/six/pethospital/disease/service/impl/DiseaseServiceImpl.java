package com.ecnu.six.pethospital.disease.service.impl;

import com.ecnu.six.pethospital.disease.dao.DiseaseDao;
import com.ecnu.six.pethospital.disease.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author onion
 * @date 2021/3/22 -8:11 上午
 */
@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    private DiseaseDao diseaseDao;


}
