package com.ecnu.six.pethospital.exam.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.service.PaperService;
import com.ecnu.six.pethospital.exam.vo.PaperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HavenTong
 * @date 2021/3/25 下午4:08
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @PostMapping("/addPaper")
    public ResponseData addPaper(@RequestBody PaperRequest paperRequest) {
        paperService.addPaper(paperRequest);
        return ResponseData.success();
    }

    @PostMapping("/updatePaper")
    public ResponseData updatePaper(@RequestBody PaperRequest paperRequest) {
        paperService.updatePaper(paperRequest);
        return ResponseData.success();
    }

    @PostMapping("/deletePaper")
    public ResponseData deletePaper(@RequestBody PaperRequest paperRequest) {
        paperService.deletePaper(paperRequest);
        return ResponseData.success();
    }

    @PostMapping("/findPaperById")
    public ResponseData findPaperById(@RequestBody PaperRequest paperRequest) {
        return ResponseData.success(paperService.findPaperById(paperRequest));
    }

    @PostMapping("/findAll")
    public ResponseData findAll() {
        return ResponseData.success(paperService.findAll());
    }
}
