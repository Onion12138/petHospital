package com.ecnu.six.pethospital.exam.service.impl;

import com.ecnu.six.pethospital.exam.dao.QuestionDao;
import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import com.ecnu.six.pethospital.exam.service.QuestionService;
import com.ecnu.six.pethospital.exam.vo.QuestionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author HavenTong
 * @date 2021/3/24 下午8:26
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Override
    public boolean addQuestion(QuestionRequest questionRequest) {
        Question question = Question.builder()
                .admId(questionRequest.getAdmId())
                .diseaseId(questionRequest.getDiseaseId())
                .stem(questionRequest.getStem())
                .choice(questionRequest.getChoice())
                .answer(questionRequest.getAnswer())
                .questionUpdatedTime(LocalDateTime.now())
                .questionCreatedTime(LocalDateTime.now()).build();
        return questionDao.addQuestion(question) == 1;
    }

    @Override
    public boolean deleteQuestionById(QuestionRequest questionRequest) {
        return questionDao.deleteQuestionById(questionRequest.getQuestionId()) == 1;
    }

    @Override
    public List<QuestionResponse> findQuestionsByDiseaseId(QuestionRequest questionRequest) {
        List<Question> questions = questionDao.findQuestionsByDiseaseId(questionRequest.getDiseaseId());
        List<QuestionResponse> questionResponses = new LinkedList<>();
        for (Question question : questions) {
            questionResponses.add(QuestionResponse.fromQuestion(question));
        }
        return questionResponses;
    }

    @Override
    public List<QuestionResponse> findQuestionsByKeyword(QuestionRequest questionRequest) {
        List<Question> questions = questionDao.findQuestionsByKeyword("%" + questionRequest.getKeyword() + "%");
        List<QuestionResponse> questionResponses = new LinkedList<>();
        for (Question question : questions) {
            questionResponses.add(QuestionResponse.fromQuestion(question));
        }
        return questionResponses;
    }

    @Override
    public QuestionResponse findQuestionById(QuestionRequest questionRequest) {
        Question question = questionDao.findQuestionById(questionRequest.getQuestionId());
        return QuestionResponse.fromQuestion(question);
    }

    @Override
    public List<QuestionResponse> findAll() {
        List<Question> questions = questionDao.findAll();
        List<QuestionResponse> questionResponses = new LinkedList<>();
        for (Question question : questions) {
            questionResponses.add(QuestionResponse.fromQuestion(question));
        }
        return questionResponses;
    }

    @Override
    public boolean updateQuestion(QuestionRequest questionRequest) {
        Question question = Question.builder()
                .questionId(questionRequest.getQuestionId())
                .admId(questionRequest.getAdmId())
                .diseaseId(questionRequest.getDiseaseId())
                .stem(questionRequest.getStem())
                .choice(questionRequest.getChoice())
                .answer(questionRequest.getAnswer())
                .questionUpdatedTime(LocalDateTime.now())
                .build();
        return questionDao.updateQuestion(question) == 1;
    }
}
