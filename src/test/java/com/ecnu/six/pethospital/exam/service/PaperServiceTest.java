package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.dao.AdmDao;
import com.ecnu.six.pethospital.exam.dao.PaperDao;
import com.ecnu.six.pethospital.exam.dao.QuestionScoreDao;
import com.ecnu.six.pethospital.exam.domain.Paper;
import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.service.impl.PaperServiceImpl;
import com.ecnu.six.pethospital.exam.vo.PaperQuestionResponse;
import com.ecnu.six.pethospital.exam.vo.PaperResponse;
import com.ecnu.six.pethospital.oauth.entity.Adm;
import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper;
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
    private LocalUserMapper localUserDao;

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

    @Test
    @DisplayName("正确修改试卷")
    void shouldUpdateCorrectPaper() {
        List<QuestionScore> questionScores = Arrays
                .asList(QuestionScore.builder().questionId(1).score(50).build(),
                        QuestionScore.builder().questionId(2).score(50).build());
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(2)
                .paperName("期末试卷")
                .admId(1)
                .duration(60)
                .questionScores(questionScores).build();
        ArgumentCaptor<Paper> paperCaptor = ArgumentCaptor.forClass(Paper.class);
        ArgumentCaptor<Integer> paperIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<List<QuestionScore>> questionScoreCaptor =
                ArgumentCaptor.forClass((Class)List.class);
        paperService.updatePaper(paperRequest);
        verify(paperDao, times(1))
                .updatePaper(paperCaptor.capture());
        verify(questionScoreDao, times(1))
                .deleteQuestionScoreByPaperId(paperIdCaptor.capture());
        verify(questionScoreDao, times(1))
                .addQuestionScores(questionScoreCaptor.capture());
        Paper paper = paperCaptor.getValue();
        int paperId = paperIdCaptor.getValue();
        List<QuestionScore> actualQuestionScores = questionScoreCaptor.getValue();
        assertAll(
                () -> assertEquals(2, paper.getPaperId()),
                () -> assertEquals("期末试卷", paper.getPaperName()),
                () -> assertEquals(1, paper.getAdmId()),
                () -> assertEquals(60, paper.getDuration()),
                () -> assertNotNull(paper.getPaperUpdatedTime()),
                () -> assertEquals(2, paperId),
                () -> assertEquals(2, actualQuestionScores.size()),
                () -> assertEquals(1, actualQuestionScores.get(0).getQuestionId()),
                () -> assertEquals(2, actualQuestionScores.get(0).getPaperId()),
                () -> assertEquals(50, actualQuestionScores.get(0).getScore()),
                () -> assertEquals(2, actualQuestionScores.get(1).getQuestionId()),
                () -> assertEquals(2, actualQuestionScores.get(1).getPaperId()),
                () -> assertEquals(50, actualQuestionScores.get(1).getScore())
        );
    }

    @Test
    @DisplayName("正确删除试卷")
    void shouldDeleteCorrectPaper() {
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1).build();
        ArgumentCaptor<Integer> paperIdCaptor = ArgumentCaptor.forClass(Integer.class);
        paperService.deletePaper(paperRequest);
        verify(paperDao, times(1))
                .deletePaperById(paperIdCaptor.capture());
        assertEquals(1, paperIdCaptor.getValue());
    }

    @Test
    @DisplayName("不存在的试卷应当返回null")
    void shouldFindNullWhenPaperNotExist() {
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1).build();
        List<QuestionScore> questionScores = new ArrayList<>();
        when(questionScoreDao.findPaperAndQuestionById(1))
                .thenReturn(questionScores);
        PaperResponse paperResponse = paperService.findPaperById(paperRequest);
        assertNull(paperResponse.getPaperId());
    }

    @Test
    @DisplayName("返回正确的试卷内容")
    void shouldFindCorrectPaper() {
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1).build();
        Paper paper = Paper.builder()
                .paperId(1)
                .paperName("期末考试")
                .admId(1)
                .duration(60).build();
        LocalUser adm = new LocalUser();
        adm.setId(1);
        adm.setNickName("haven");
        Question question0 = Question.builder()
                .questionId(2)
                .stem("单选题0")
                .choice("选A|选B|选C|选D")
                .answer("A")
                .build();
        Question question1 = Question.builder()
                .questionId(3)
                .stem("单选题1")
                .choice("选A|选B|选C|选D")
                .answer("B")
                .build();
        List<QuestionScore> questionScores = new ArrayList<>(
                Arrays.asList(
                        QuestionScore.builder().paper(paper).question(question0).score(50).build(),
                        QuestionScore.builder().paper(paper).question(question1).score(50).build()
                )
        );
        when(questionScoreDao.findPaperAndQuestionById(1))
                .thenReturn(questionScores);
        when(localUserDao.selectByPrimaryKey(1))
                .thenReturn(adm);
        PaperResponse paperResponse = paperService.findPaperById(paperRequest);
        List<PaperQuestionResponse> responses = paperResponse.getQuestions();
        assertAll(
                () -> assertEquals(1, paperResponse.getPaperId()),
                () -> assertEquals("期末考试", paperResponse.getPaperName()),
                () -> assertEquals(1, paperResponse.getAdmId()),
                () -> assertEquals("haven", paperResponse.getAdmName()),
                () -> assertEquals(60, paperResponse.getDuration()),
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(2, responses.get(0).getQuestionId()),
                () -> assertEquals("单选题0", responses.get(0).getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, responses.get(0).getChoices()),
                () -> assertEquals("A", responses.get(0).getAnswer()),
                () -> assertEquals(50, responses.get(0).getScore()),
                () -> assertEquals(3, responses.get(1).getQuestionId()),
                () -> assertEquals("单选题1", responses.get(1).getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, responses.get(1).getChoices()),
                () -> assertEquals("B", responses.get(1).getAnswer()),
                () -> assertEquals(50, responses.get(1).getScore())
        );
    }

    @Test
    @DisplayName("获取正确的试卷列表")
    void shouldFindAllCorrectPapers() {
        LocalUser adm0 = new LocalUser();
        adm0.setId(1);
        adm0.setNickName("haven");
        LocalUser adm1 = new LocalUser();
        adm1.setId(2);
        adm1.setNickName("onion");
        Paper paper0 = Paper.builder()
                .paperId(1)
                .paperName("第一张试卷")
                .duration(60)
                .paperCreatedTime(LocalDateTime.of(2021, 3, 29, 10, 0, 0))
                .paperUpdatedTime(LocalDateTime.of(2021, 3, 30, 10, 20, 0))
                .adm(adm0)
                .build();
        Paper paper1 = Paper.builder()
                .paperId(2)
                .paperName("第二张试卷")
                .duration(120)
                .paperCreatedTime(LocalDateTime.of(2021, 4, 3, 8, 0, 0))
                .paperUpdatedTime(LocalDateTime.of(2021, 4, 4, 10, 0, 0))
                .adm(adm1)
                .build();
        List<Paper> papers = new ArrayList<>(Arrays.asList(paper0, paper1));
        when(paperDao.findAll()).thenReturn(papers);
        List<PaperResponse> responses = paperService.findAll();
        assertAll(
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(1, responses.get(0).getPaperId()),
                () -> assertEquals("第一张试卷", responses.get(0).getPaperName()),
                () -> assertEquals(1, responses.get(0).getAdmId()),
                () -> assertEquals("haven", responses.get(0).getAdmName()),
                () -> assertEquals(60, responses.get(0).getDuration()),
                () -> assertEquals("2021-03-29 10:00", responses.get(0).getPaperCreatedTime()),
                () -> assertEquals("2021-03-30 10:20", responses.get(0).getPaperUpdatedTime()),
                () -> assertEquals(2, responses.get(1).getPaperId()),
                () -> assertEquals("第二张试卷", responses.get(1).getPaperName()),
                () -> assertEquals(2, responses.get(1).getAdmId()),
                () -> assertEquals("onion", responses.get(1).getAdmName()),
                () -> assertEquals(120, responses.get(1).getDuration()),
                () -> assertEquals("2021-04-03 08:00", responses.get(1).getPaperCreatedTime()),
                () -> assertEquals("2021-04-04 10:00", responses.get(1).getPaperUpdatedTime())
        );
    }
}