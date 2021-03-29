package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.dao.AdmDao;
import com.ecnu.six.pethospital.exam.dao.PaperDao;
import com.ecnu.six.pethospital.exam.dao.QuestionScoreDao;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.service.impl.PaperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author HavenTong
 * @date 2021/3/29 下午4:13
 */
@SpringBootTest
@Slf4j
class PaperServiceTest {
    @Mock
    private PaperDao paperDao;

    @Mock
    private QuestionScoreDao questionScoreDao;

    @Mock
    private AdmDao admDao;

    @InjectMocks
    private PaperServiceImpl paperService;

    @Test
    @DisplayName("正确增加试卷")
    void shouldAddCorrectPaper() {
        List<QuestionScore> questionScores = Arrays
                .asList(QuestionScore.builder().questionId(1).score(50).build(),
                        QuestionScore.builder().questionId(2).score(50).build());
        PaperRequest paperRequest = PaperRequest.builder()
                .paperName("期末试卷")
                .admId(1)
                .duration(60)
                .questionScores(questionScores).build();
        ArgumentCaptor<Paper> paperCaptor = ArgumentCaptor.forClass(Paper.class);
        ArgumentCaptor<List<QuestionScore>> questionScoreCaptor =
                ArgumentCaptor.forClass((Class)List.class);
        when(paperDao.addPaper(any())).thenReturn(1);
        paperService.addPaper(paperRequest);
        verify(paperDao, times(1))
                .addPaper(paperCaptor.capture());
        verify(questionScoreDao, times(1))
                .addQuestionScores(questionScoreCaptor.capture());
        Paper paper = paperCaptor.getValue();
        List<QuestionScore> actualQuestionScore = questionScoreCaptor.getValue();
        assertAll(
                () -> assertEquals("期末试卷", paper.getPaperName()),
                () -> assertEquals(1, paper.getAdmId()),
                () -> assertEquals(60, paper.getDuration()),
                () -> assertNotNull(paper.getPaperCreatedTime()),
                () -> assertNotNull(paper.getPaperUpdatedTime()),
                () -> assertEquals(2, actualQuestionScore.size()),
                () -> assertEquals(1, actualQuestionScore.get(0).getQuestionId()),
                () -> assertEquals(50, actualQuestionScore.get(0).getScore()),
                () -> assertEquals(2, actualQuestionScore.get(1).getQuestionId()),
                () -> assertEquals(50, actualQuestionScore.get(1).getScore())
        );
    }
}