package com.ecnu.six.pethospital.exam.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import com.ecnu.six.pethospital.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HavenTong
 * @date 2021/3/24 下午8:28
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public ResponseData addQuestion(@RequestBody QuestionRequest questionRequest) {
        questionService.addQuestion(questionRequest);
        return ResponseData.success();
    }

    @PostMapping("/deleteQuestion")
    public ResponseData deleteQuestion(@RequestBody QuestionRequest questionRequest) {
        questionService.deleteQuestionById(questionRequest);
        return ResponseData.success();
    }

    @PostMapping("/findQuestionByDiseaseId")
    public ResponseData findQuestionByDiseaseId(@RequestBody QuestionRequest questionRequest) {
        return ResponseData.success(questionService.findQuestionsByDiseaseId(questionRequest));
    }

    @PostMapping("/findQuestionByKeyword")
    public ResponseData findQuestionByKeyword(@RequestBody QuestionRequest questionRequest) {
        return ResponseData.success(questionService.findQuestionsByKeyword(questionRequest));
    }

    @PostMapping("/findQuestionById")
    public ResponseData findQuestionById(@RequestBody QuestionRequest questionRequest) {
        return ResponseData.success(questionService.findQuestionById(questionRequest));
    }

    @PostMapping("/findAll")
    public ResponseData findAll() {
        return ResponseData.success(questionService.findAll());
    }

    @PostMapping("/updateQuestion")
    public ResponseData updateQuestion(@RequestBody QuestionRequest questionRequest) {
        questionService.updateQuestion(questionRequest);
        return ResponseData.success();
    }
}
