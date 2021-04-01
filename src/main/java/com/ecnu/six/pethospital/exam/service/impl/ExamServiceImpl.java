package com.ecnu.six.pethospital.exam.service.impl;

import com.ecnu.six.pethospital.exam.dao.ExamDao;
import com.ecnu.six.pethospital.exam.dao.ExamScoreDao;
import com.ecnu.six.pethospital.exam.domain.Exam;
import com.ecnu.six.pethospital.exam.domain.ExamScore;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.exam.dto.ExamScoreRequest;
import com.ecnu.six.pethospital.exam.service.ExamService;
import com.ecnu.six.pethospital.exam.vo.ExamResponse;
import com.ecnu.six.pethospital.oauth.entity.Adm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:16
 */
@Slf4j
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamDao examDao;

    @Autowired
    private ExamScoreDao examScoreDao;

    @Override
    public void addExam(ExamRequest examRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Exam exam = Exam.builder()
                .paperId(examRequest.getPaperId())
                .examName(examRequest.getExamName())
                .creatorId(examRequest.getCreatorId())
                .startTime(LocalDateTime.parse(examRequest.getStartTime(), formatter))
                .build();
        examDao.addExam(exam);
    }

    @Override
    public List<ExamResponse> findAvailableExams(ExamRequest examRequest) {
        List<Exam> exams = examDao.findAvailableExams(LocalDateTime.now());
        List<ExamResponse> examResponses = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Exam exam : exams) {
            Paper paper = exam.getPaper();
            Adm adm = exam.getAdm();
            List<QuestionScore> questionScores = exam.getQuestionScores();
            int totalScore = 0;
            for (QuestionScore questionScore : questionScores) {
                totalScore += questionScore.getScore();
            }
            log.info(exam.getExamId().toString(), "usrId: ", examRequest.getUsrId());
            Integer examScore = examScoreDao.findByExamIdAndUsrId(exam.getExamId(), examRequest.getUsrId());
            examResponses.add(ExamResponse.builder()
                    .examId(exam.getExamId())
                    .paperId(paper.getPaperId())
                    .examName(exam.getExamName())
                    .admId(adm.getAdmId())
                    .admName(adm.getAdmName())
                    .startTime(formatter.format(exam.getStartTime()))
                    .endTime(formatter.format(exam.getStartTime().plusMinutes(paper.getDuration())))
                    .totalScore(totalScore)
                    .questionNums(questionScores.size())
                    .finished(Objects.nonNull(examScore))
                    .build());
        }
        return examResponses;
    }

    @Override
    public void saveScore(ExamScoreRequest examScoreRequest) {
        ExamScore examScore = ExamScore.builder()
                .examId(examScoreRequest.getExamId())
                .usrId(examScoreRequest.getUsrId())
                .score(examScoreRequest.getScore())
                .build();
        examScoreDao.saveScore(examScore);
    }
}
