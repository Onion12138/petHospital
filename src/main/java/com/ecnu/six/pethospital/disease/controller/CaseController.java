package com.ecnu.six.pethospital.disease.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.domain.Caze;
import com.ecnu.six.pethospital.disease.dto.CaseDTO;
import com.ecnu.six.pethospital.disease.service.impl.CazeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author onion
 * @date 2021/3/22 -9:34 上午
 */
@RestController
@RequestMapping("/case")
public class CaseController {
    @Autowired
    private CazeServiceImpl caseService;
    @GetMapping("/findOne")
    public ResponseData findOne(@RequestParam Long caseId) {
        Caze caze = caseService.findOne(caseId);
        return ResponseData.success(caze);
    }
    @GetMapping("/findAll")
    public ResponseData findAll(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "5") Integer size) {
        Page<Caze> casePage = caseService.findAll(page, size);
        return ResponseData.success(casePage);
    }
    @PostMapping("/deleteOne")
    public ResponseData deleteOne(@RequestParam Long caseId) {
        caseService.deleteOne(caseId);
        return ResponseData.success();
    }
    @PostMapping("/addOne")
    public ResponseData addOne(@RequestBody CaseDTO caseDTO) {
        caseService.addOne(caseDTO);
        return ResponseData.success();
    }
    @PostMapping("/updateOne")
    public ResponseData updateOne(@RequestBody CaseDTO caseDTO) {
        caseService.updateOne(caseDTO);
        return ResponseData.success();
    }
}
