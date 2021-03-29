package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.disease.domain.Disease;
import com.ecnu.six.pethospital.exam.dao.QuestionDao;
import com.ecnu.six.pethospital.exam.domain.Question;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import com.ecnu.six.pethospital.exam.service.impl.QuestionServiceImpl;
import com.ecnu.six.pethospital.exam.vo.QuestionResponse;
import com.ecnu.six.pethospital.oauth.entity.Adm;
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
 * @date 2021/3/29 上午8:44
 */
@SpringBootTest
class QuestionServiceTest {
    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    @DisplayName("正确增加考题")
    void shouldAddCorrectQuestion() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .admId(1)
                .diseaseId(2)
                .stem("选择题")
                .choice("A|B|C|D")
                .answer("AB")
                .build();
        questionService.addQuestion(questionRequest);
        ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
        verify(questionDao, times(1))
                .addQuestion(questionCaptor.capture());
        Question question = questionCaptor.getValue();
        assertAll(
                () -> assertEquals(1, question.getAdmId()),
                () -> assertEquals(2, question.getDiseaseId()),
                () -> assertEquals("选择题", question.getStem()),
                () -> assertEquals("A|B|C|D", question.getChoice()),
                () -> assertEquals("AB", question.getAnswer()),
                () -> assertNotNull(question.getQuestionCreatedTime()),
                () -> assertNotNull(question.getQuestionUpdatedTime())
        );
    }

    @Test
    @DisplayName("正确删除考题")
    void shouldDeleteCorrectQuestion() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(3).build();
        questionService.deleteQuestionById(questionRequest);
        ArgumentCaptor<Integer> questionIdCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(questionDao, times(1))
                .deleteQuestionById(questionIdCaptor.capture());
        assertEquals(3, questionIdCaptor.getValue());
    }

    @Test
    @DisplayName("根据病种正确查询考题")
    void shouldFindQuestionsByDiseaseId() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .diseaseId(1).build();
        Adm adm = new Adm();
        adm.setAdmId(1);
        adm.setAdmName("haven");
        Disease disease = new Disease();
        disease.setDiseaseId(1L);
        disease.setName("传染病");
        Question question0 = Question.builder()
                .questionId(3)
                .stem("单选题0")
                .choice("选A|选B|选C|选D")
                .answer("A")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 3, 29, 10, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 3, 30, 10, 20, 0))
                .build();
        Question question1 = Question.builder()
                .questionId(4)
                .stem("单选题1")
                .choice("选A|选B|选C|选D")
                .answer("B")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 4, 3, 8, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 4, 4, 10, 0, 0))
                .build();
        List<Question> questions = new ArrayList<>(Arrays.asList(question0, question1));
        when(questionDao.findQuestionsByDiseaseId(1))
                .thenReturn(questions);
        List<QuestionResponse> questionResponses =
                questionService.findQuestionsByDiseaseId(questionRequest);
        QuestionResponse response0 = questionResponses.get(0);
        QuestionResponse response1 = questionResponses.get(1);
        assertAll(
                () -> assertEquals(2, questionResponses.size()),
                () -> assertEquals(3, response0.getQuestionId()),
                () -> assertEquals(1, response0.getAdmId()),
                () -> assertEquals("haven", response0.getAdmName()),
                () -> assertEquals(1, response0.getDiseaseId()),
                () -> assertEquals("传染病", response0.getDiseaseName()),
                () -> assertEquals("单选题0", response0.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response0.getChoices()),
                () -> assertEquals("A", response0.getAnswer()),
                () -> assertEquals("2021-03-29 10:00", response0.getQuestionCreatedTime()),
                () -> assertEquals("2021-03-30 10:20", response0.getQuestionUpdatedTime()),
                () -> assertEquals(4, response1.getQuestionId()),
                () -> assertEquals(1, response1.getAdmId()),
                () -> assertEquals("haven", response1.getAdmName()),
                () -> assertEquals(1, response1.getDiseaseId()),
                () -> assertEquals("传染病", response1.getDiseaseName()),
                () -> assertEquals("单选题1", response1.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response1.getChoices()),
                () -> assertEquals("B", response1.getAnswer()),
                () -> assertEquals("2021-04-03 08:00", response1.getQuestionCreatedTime()),
                () -> assertEquals("2021-04-04 10:00", response1.getQuestionUpdatedTime())
        );
    }

    @Test
    @DisplayName("根据关键词正确查询考题")
    void shouldFindCorrectQuestionsByKeyword() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .keyword("传染病").build();
        Adm adm = new Adm();
        adm.setAdmId(1);
        adm.setAdmName("haven");
        Disease disease = new Disease();
        disease.setDiseaseId(1L);
        disease.setName("传染病");
        Question question0 = Question.builder()
                .questionId(3)
                .stem("传染病单选题0")
                .choice("选A|选B|选C|选D")
                .answer("A")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 3, 29, 10, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 3, 30, 10, 20, 0))
                .build();
        Question question1 = Question.builder()
                .questionId(4)
                .stem("传染病单选题1")
                .choice("选A|选B|选C|选D")
                .answer("B")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 4, 3, 8, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 4, 4, 10, 0, 0))
                .build();
        List<Question> questions = new ArrayList<>(Arrays.asList(question0, question1));
        when(questionDao.findQuestionsByKeyword("%传染病%"))
                .thenReturn(questions);
        List<QuestionResponse> questionResponses =
                questionService.findQuestionsByKeyword(questionRequest);
        QuestionResponse response0 = questionResponses.get(0);
        QuestionResponse response1 = questionResponses.get(1);
        assertAll(
                () -> assertEquals(2, questionResponses.size()),
                () -> assertEquals(3, response0.getQuestionId()),
                () -> assertEquals(1, response0.getAdmId()),
                () -> assertEquals("haven", response0.getAdmName()),
                () -> assertEquals(1, response0.getDiseaseId()),
                () -> assertEquals("传染病", response0.getDiseaseName()),
                () -> assertEquals("传染病单选题0", response0.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response0.getChoices()),
                () -> assertEquals("A", response0.getAnswer()),
                () -> assertEquals("2021-03-29 10:00", response0.getQuestionCreatedTime()),
                () -> assertEquals("2021-03-30 10:20", response0.getQuestionUpdatedTime()),
                () -> assertEquals(4, response1.getQuestionId()),
                () -> assertEquals(1, response1.getAdmId()),
                () -> assertEquals("haven", response1.getAdmName()),
                () -> assertEquals(1, response1.getDiseaseId()),
                () -> assertEquals("传染病", response1.getDiseaseName()),
                () -> assertEquals("传染病单选题1", response1.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response1.getChoices()),
                () -> assertEquals("B", response1.getAnswer()),
                () -> assertEquals("2021-04-03 08:00", response1.getQuestionCreatedTime()),
                () -> assertEquals("2021-04-04 10:00", response1.getQuestionUpdatedTime())
        );
    }

    @Test
    @DisplayName("根据id查询正确考题")
    void shouldFindCorrectQuestionById() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(1).build();
        Adm adm = new Adm();
        adm.setAdmId(1);
        adm.setAdmName("haven");
        Disease disease = new Disease();
        disease.setDiseaseId(1L);
        disease.setName("传染病");
        Question question = Question.builder()
                .questionId(1)
                .stem("传染病单选题0")
                .choice("选A|选B|选C|选D")
                .answer("A")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 3, 29, 10, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 3, 30, 10, 20, 0))
                .build();
        when(questionDao.findQuestionById(1)).thenReturn(question);
        QuestionResponse response = questionService.findQuestionById(questionRequest);
        assertAll(
                () -> assertEquals(1, response.getQuestionId()),
                () -> assertEquals(1, response.getAdmId()),
                () -> assertEquals("haven", response.getAdmName()),
                () -> assertEquals(1, response.getDiseaseId()),
                () -> assertEquals("传染病", response.getDiseaseName()),
                () -> assertEquals("传染病单选题0", response.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response.getChoices()),
                () -> assertEquals("A", response.getAnswer()),
                () -> assertEquals("2021-03-29 10:00", response.getQuestionCreatedTime()),
                () -> assertEquals("2021-03-30 10:20", response.getQuestionUpdatedTime())
        );
    }

    @Test
    @DisplayName("获取全部考题")
    void shouldFindAllQuestions() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .diseaseId(1).build();
        Adm adm = new Adm();
        adm.setAdmId(1);
        adm.setAdmName("haven");
        Disease disease = new Disease();
        disease.setDiseaseId(1L);
        disease.setName("传染病");
        Question question0 = Question.builder()
                .questionId(3)
                .stem("单选题0")
                .choice("选A|选B|选C|选D")
                .answer("A")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 3, 29, 10, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 3, 30, 10, 20, 0))
                .build();
        Question question1 = Question.builder()
                .questionId(4)
                .stem("单选题1")
                .choice("选A|选B|选C|选D")
                .answer("B")
                .adm(adm)
                .disease(disease)
                .questionCreatedTime(LocalDateTime.of(2021, 4, 3, 8, 0, 0))
                .questionUpdatedTime(LocalDateTime.of(2021, 4, 4, 10, 0, 0))
                .build();
        List<Question> questions = new ArrayList<>(Arrays.asList(question0, question1));
        when(questionDao.findAll())
                .thenReturn(questions);
        List<QuestionResponse> questionResponses =
                questionService.findAll();
        QuestionResponse response0 = questionResponses.get(0);
        QuestionResponse response1 = questionResponses.get(1);
        assertAll(
                () -> assertEquals(2, questionResponses.size()),
                () -> assertEquals(3, response0.getQuestionId()),
                () -> assertEquals(1, response0.getAdmId()),
                () -> assertEquals("haven", response0.getAdmName()),
                () -> assertEquals(1, response0.getDiseaseId()),
                () -> assertEquals("传染病", response0.getDiseaseName()),
                () -> assertEquals("单选题0", response0.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response0.getChoices()),
                () -> assertEquals("A", response0.getAnswer()),
                () -> assertEquals("2021-03-29 10:00", response0.getQuestionCreatedTime()),
                () -> assertEquals("2021-03-30 10:20", response0.getQuestionUpdatedTime()),
                () -> assertEquals(4, response1.getQuestionId()),
                () -> assertEquals(1, response1.getAdmId()),
                () -> assertEquals("haven", response1.getAdmName()),
                () -> assertEquals(1, response1.getDiseaseId()),
                () -> assertEquals("传染病", response1.getDiseaseName()),
                () -> assertEquals("单选题1", response1.getStem()),
                () -> assertArrayEquals(new String[]{"选A", "选B", "选C", "选D"}, response1.getChoices()),
                () -> assertEquals("B", response1.getAnswer()),
                () -> assertEquals("2021-04-03 08:00", response1.getQuestionCreatedTime()),
                () -> assertEquals("2021-04-04 10:00", response1.getQuestionUpdatedTime())
        );
    }

    @Test
    @DisplayName("正确修改考题")
    void shouldUpdateCorrectQuestion() {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(1)
                .admId(1)
                .diseaseId(2)
                .stem("选择题")
                .choice("A|B|C|D")
                .answer("AB")
                .build();
        questionService.updateQuestion(questionRequest);
        ArgumentCaptor<Question> questionCaptor = ArgumentCaptor.forClass(Question.class);
        verify(questionDao, times(1))
                .updateQuestion(questionCaptor.capture());
        Question question = questionCaptor.getValue();
        assertAll(
                () -> assertEquals(1, question.getQuestionId()),
                () -> assertEquals(1, question.getAdmId()),
                () -> assertEquals(2, question.getDiseaseId()),
                () -> assertEquals("选择题", question.getStem()),
                () -> assertEquals("A|B|C|D", question.getChoice()),
                () -> assertEquals("AB", question.getAnswer()),
                () -> assertNotNull(question.getQuestionUpdatedTime())
        );
    }
}