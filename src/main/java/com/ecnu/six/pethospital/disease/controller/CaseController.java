package com.ecnu.six.pethospital.disease.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.domain.Case;
import com.ecnu.six.pethospital.disease.service.impl.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author onion
 * @date 2021/3/22 -9:34 上午
 */
@RestController("/case")
public class CaseController {
    @Autowired
    private CaseService caseService;
    @GetMapping("/findOne")
    public ResponseData findOne(@RequestParam Long caseId) {
        Case caze = caseService.findOne(caseId);
        return ResponseData.success(caze);
    }
    @GetMapping("/findAll")
    public ResponseData findAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Case> casePage = caseService.findAll(page, size);
        return ResponseData.success(casePage);
    }
    @PostMapping("/deleteOne")
    public ResponseData deleteOne(@RequestParam Long caseId) {
        caseService.deleteOne(caseId);
        return ResponseData.success();
    }
    @PostMapping("/addOne")
    public ResponseData addOne(@RequestBody Case caze) {
        caseService.addOne(caze);
        return ResponseData.success();
    }
    @PostMapping("/updateOne")
    public ResponseData updateOne(@RequestBody Case caze) {
        caseService.addOne(caze);
        return ResponseData.success();
    }
}
