package com.ecnu.six.pethospital.exam.service.impl;

import com.ecnu.six.pethospital.exam.dao.PaperDao;
import com.ecnu.six.pethospital.exam.dao.AdmDao;
import com.ecnu.six.pethospital.exam.dao.QuestionScoreDao;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.service.PaperService;
import com.ecnu.six.pethospital.exam.vo.PaperQuestionResponse;
import com.ecnu.six.pethospital.exam.vo.PaperResponse;
import com.ecnu.six.pethospital.oauth.entity.Adm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:58
 */
@Service
@Slf4j
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperDao paperDao;

    @Autowired
    private QuestionScoreDao questionScoreDao;

    @Autowired
    private AdmDao admDao;

    @Override
    public void addPaper(PaperRequest paperRequest) {
        Paper paper = Paper.builder()
                .paperName(paperRequest.getPaperName())
                .admId(paperRequest.getAdmId())
                .duration(paperRequest.getDuration())
                .paperCreatedTime(LocalDateTime.now())
                .paperUpdatedTime(LocalDateTime.now())
                .build();
        paperDao.addPaper(paper);
        List<QuestionScore> questionScores = paperRequest.getQuestionScores();
        for (QuestionScore questionScore : questionScores) {
            questionScore.setPaperId(paper.getPaperId());
        }
        questionScoreDao.addQuestionScores(questionScores);
    }

    @Override
    public void updatePaper(PaperRequest paperRequest) {
        Paper paper = Paper.builder()
                .paperId(paperRequest.getPaperId())
                .paperName(paperRequest.getPaperName())
                .admId(paperRequest.getAdmId())
                .duration(paperRequest.getDuration())
                .paperUpdatedTime(LocalDateTime.now())
                .build();
        paperDao.updatePaper(paper);
        questionScoreDao.deleteQuestionScoreByPaperId(paper.getPaperId());
        List<QuestionScore> questionScores = paperRequest.getQuestionScores();
        for (QuestionScore questionScore : questionScores) {
            questionScore.setPaperId(paper.getPaperId());
        }
        questionScoreDao.addQuestionScores(questionScores);
    }

    @Override
    public void deletePaper(PaperRequest paperRequest) {
        paperDao.deletePaperById(paperRequest.getPaperId());
    }

    @Override
    public PaperResponse findPaperById(PaperRequest paperRequest) {
        List<QuestionScore> questionScores = questionScoreDao.findPaperAndQuestionById(paperRequest.getPaperId());
        if (questionScores.isEmpty()) {
            return new PaperResponse();
        }
        Paper paper= questionScores.get(0).getPaper();
        Adm adm = admDao.findAdmById(paper.getAdmId());
        List<PaperQuestionResponse> paperQuestionResponses = new LinkedList<>();
        for (QuestionScore questionScore : questionScores) {
            Question question = questionScore.getQuestion();
            paperQuestionResponses.add(PaperQuestionResponse.builder()
                .questionId(question.getQuestionId())
                .stem(question.getStem())
                .choices(question.getChoice().split("\\|"))
                .answer(question.getAnswer())
                .score(questionScore.getScore())
                .build());
        }
        return PaperResponse.builder()
                .paperId(paper.getPaperId())
                .paperName(paper.getPaperName())
                .admId(adm.getAdmId())
                .admName(adm.getAdmName())
                .duration(paper.getDuration())
                .questions(paperQuestionResponses)
                .build();
    }

    @Override
    public List<PaperResponse> findAll() {
        List<Paper> papers = paperDao.findAll();
        List<PaperResponse> paperResponses = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Paper paper : papers) {
            paperResponses.add(PaperResponse.builder()
                .paperId(paper.getPaperId())
                .paperName(paper.getPaperName())
                .admId(paper.getAdm().getAdmId())
                .admName(paper.getAdm().getAdmName())
                .duration(paper.getDuration())
                .paperCreatedTime(formatter.format(paper.getPaperCreatedTime()))
                .paperUpdatedTime(formatter.format(paper.getPaperUpdatedTime()))
                .build());
        }
        return paperResponses;
    }
}
