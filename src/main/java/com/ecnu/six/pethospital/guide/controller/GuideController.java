package com.ecnu.six.pethospital.guide.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.guide.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:24
 */
@RestController
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    private GuideService guideService;

    @PostMapping("/getDepartments")
    public ResponseData getDepartments() {
        return ResponseData.success(guideService.getDepartments());
    }
}
