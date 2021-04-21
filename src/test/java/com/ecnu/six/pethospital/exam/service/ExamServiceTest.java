package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.dao.ExamDao;
import com.ecnu.six.pethospital.exam.dao.ExamScoreDao;
import com.ecnu.six.pethospital.exam.domain.Exam;
import com.ecnu.six.pethospital.exam.domain.ExamScore;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.exam.dto.ExamScoreRequest;
import com.ecnu.six.pethospital.exam.service.impl.ExamServiceImpl;
import com.ecnu.six.pethospital.exam.vo.ExamResponse;
import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author HavenTong
 * @date 2021/3/29 下午7:46
 */
@SpringBootTest
@Slf4j
class ExamServiceTest {
    @Mock
    private ExamDao examDao;

    @Mock
    private ExamScoreDao examScoreDao;

    @InjectMocks
    private ExamServiceImpl examService;

    @Test
    @DisplayName("添加正确的考试")
    void shouldAddCorrectExam() {
        ExamRequest examRequest = ExamRequest.builder()
                .paperId(1)
                .examName("期末考试")
                .creatorId(1)
                .startTime("2021-05-12 10:00:00").build();
        ArgumentCaptor<Exam> examCaptor = ArgumentCaptor.forClass(Exam.class);
        examService.addExam(examRequest);
        verify(examDao, times(1))
                .addExam(examCaptor.capture());
        Exam exam = examCaptor.getValue();
        assertAll(
                () -> assertEquals(1, exam.getPaperId()),
                () -> assertEquals(1, exam.getCreatorId()),
                () -> assertEquals("期末考试", exam.getExamName()),
                () -> assertEquals(LocalDateTime.of(2021, 5, 12, 10, 0, 0), exam.getStartTime())
        );
    }

    @Test
    @DisplayName("获取正确的可用考试")
    void shouldFindCorrectAvailableExams() {
        ExamRequest examRequest = ExamRequest.builder().usrId(1).build();
        LocalUser adm0 = new LocalUser();
        adm0.setId(1);
        adm0.setNickName("haven");
        LocalUser adm1 = new LocalUser();
        adm1.setId(2);
        adm1.setNickName("onion");
        QuestionScore questionScore0 = QuestionScore.builder().score(60).build();
        QuestionScore questionScore1 = QuestionScore.builder().score(40).build();
        QuestionScore questionScore2 = QuestionScore.builder().score(75).build();
        QuestionScore questionScore3 = QuestionScore.builder().score(45).build();
        List<QuestionScore> questionScores0 = new ArrayList<>(Arrays.asList(questionScore0, questionScore1));
        List<QuestionScore> questionScores1 = new ArrayList<>(Arrays.asList(questionScore2, questionScore3));
        Paper paper0 = Paper.builder()
                .paperId(2)
                .paperName("第一张试卷")
                .duration(60)
                .build();
        Paper paper1 = Paper.builder()
                .paperId(4)
                .paperName("第二张试卷")
                .duration(120)
                .build();
        Exam exam0 = Exam.builder()
                .examId(1)
                .examName("期末考试一")
                .adm(adm0)
                .paper(paper0)
                .startTime(LocalDateTime.of(2021, 5, 12, 10, 0, 0))
                .questionScores(questionScores0)
                .build();
        Exam exam1 = Exam.builder()
                .examId(3)
                .examName("期末考试二")
                .adm(adm1)
                .paper(paper1)
                .startTime(LocalDateTime.of(2021, 6, 21, 8, 30, 0))
                .questionScores(questionScores1)
                .build();
        List<Exam> exams = new ArrayList<>(Arrays.asList(exam0, exam1));
        when(examDao.findAvailableExams(any()))
                .thenReturn(exams);
        when(examScoreDao.findByExamIdAndUsrId(1, 1)).thenReturn(null);
        when(examScoreDao.findByExamIdAndUsrId(3, 1)).thenReturn(100);
        List<ExamResponse> examResponses = examService.findAvailableExams(examRequest);
        assertAll(
                () -> assertEquals(2, examResponses.size()),
                () -> assertEquals(1, examResponses.get(0).getExamId()),
                () -> assertEquals(2, examResponses.get(0).getPaperId()),
                () -> assertEquals("期末考试一", examResponses.get(0).getExamName()),
                () -> assertEquals(1, examResponses.get(0).getAdmId()),
                () -> assertEquals("haven", examResponses.get(0).getAdmName()),
                () -> assertEquals("2021-05-12 10:00:00", examResponses.get(0).getStartTime()),
                () -> assertEquals("2021-05-12 11:00:00", examResponses.get(0).getEndTime()),
                () -> assertEquals(2, examResponses.get(0).getQuestionNums()),
                () -> assertEquals(100, examResponses.get(0).getTotalScore()),
                () -> assertEquals(false, examResponses.get(0).getFinished()),
                () -> assertEquals(3, examResponses.get(1).getExamId()),
                () -> assertEquals(4, examResponses.get(1).getPaperId()),
                () -> assertEquals("期末考试二", examResponses.get(1).getExamName()),
                () -> assertEquals(2, examResponses.get(1).getAdmId()),
                () -> assertEquals("onion", examResponses.get(1).getAdmName()),
                () -> assertEquals("2021-06-21 08:30:00", examResponses.get(1).getStartTime()),
                () -> assertEquals("2021-06-21 10:30:00", examResponses.get(1).getEndTime()),
                () -> assertEquals(2, examResponses.get(1).getQuestionNums()),
                () -> assertEquals(120, examResponses.get(1).getTotalScore()),
                () -> assertEquals(true, examResponses.get(1).getFinished())

        );
    }

    @Test
    @DisplayName("正确记录用户分数")
    void shouldSaveCorrectScore() {
        ExamScoreRequest examScoreRequest = ExamScoreRequest.builder()
                .examId(1)
                .usrId(2)
                .score(75).build();
        ArgumentCaptor<ExamScore> examScoreCaptor = ArgumentCaptor.forClass(ExamScore.class);
        examService.saveScore(examScoreRequest);
        verify(examScoreDao, times(1))
                .saveScore(examScoreCaptor.capture());
        ExamScore examScore = examScoreCaptor.getValue();
        assertAll(
                () -> assertEquals(1, examScore.getExamId()),
                () -> assertEquals(2, examScore.getUsrId()),
                () -> assertEquals(75, examScore.getScore())
        );
    }
}