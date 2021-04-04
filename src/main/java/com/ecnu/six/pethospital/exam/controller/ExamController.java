package com.ecnu.six.pethospital.exam.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.exam.domain.ExamScore;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.exam.dto.ExamScoreRequest;
import com.ecnu.six.pethospital.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:19
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping("/addExam")
    public ResponseData addExam(@RequestBody ExamRequest examRequest) {
        examService.addExam(examRequest);
        return ResponseData.success();
    }

    @PostMapping("/findAvailableExams")
    public ResponseData findAvailableExams(@RequestBody ExamRequest examRequest) {
        return ResponseData.success(examService.findAvailableExams(examRequest));
    }

    @PostMapping("/saveScore")
    public ResponseData saveScore(@RequestBody ExamScoreRequest examScoreRequest) {
        examService.saveScore(examScoreRequest);
        return ResponseData.success();
    }
}
