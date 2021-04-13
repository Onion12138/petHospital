package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import com.ecnu.six.pethospital.exam.vo.QuestionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HavenTong
 * @date 2021/3/24 下午8:16
 */
public interface QuestionService {

    boolean addQuestion(QuestionRequest questionRequest);

    boolean deleteQuestionById(QuestionRequest questionRequest);

    List<QuestionResponse> findQuestionsByDiseaseId(QuestionRequest questionRequest);

    List<QuestionResponse> findQuestionsByKeyword(QuestionRequest questionRequest);

    QuestionResponse findQuestionById(QuestionRequest questionRequest);

    List<QuestionResponse> findAll();

    boolean updateQuestion(QuestionRequest questionRequest);
}
