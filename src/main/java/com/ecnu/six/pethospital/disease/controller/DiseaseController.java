package com.ecnu.six.pethospital.disease.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.service.DiseaseService;
import com.ecnu.six.pethospital.disease.vo.DiseaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/findAll")
    public ResponseData findAll() {
        List<DiseaseVO> diseaseVOList = diseaseService.findAll();
        return ResponseData.success(diseaseVOList);
    }

    @PostMapping("/addOne")
    public ResponseData addOne(@RequestParam String name, @RequestParam Long parent) {
        diseaseService.addOne(name, parent);
        return ResponseData.success();
    }

    @PostMapping("/updateOne")
    public ResponseData updateOne(@RequestParam Long diseaseId,
                                  @RequestParam String name,
                                  @RequestParam Long parent) {
        diseaseService.updateOne(diseaseId, name, parent);
        return ResponseData.success();
    }

    @PostMapping("/deleteOne")
    public ResponseData deleteOne(@RequestParam Long diseaseId) {
        diseaseService.deleteOne(diseaseId);
        return ResponseData.success();
    }
}
