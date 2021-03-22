package com.ecnu.six.pethospital.disease.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.service.DiseaseService;
import com.ecnu.six.pethospital.disease.vo.DiseaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author onion
 * @date 2021/3/22 -8:15 上午
 */
@RestController("/disease")
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("findAll")
    public ResponseData findAll() {
        List<DiseaseVO> diseaseVOList = diseaseService.findAll();
        return ResponseData.success(diseaseVOList);
    }
}
