package com.ecnu.six.pethospital.exam.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.exam.dto.QuestionRequest;
import com.ecnu.six.pethospital.exam.service.impl.QuestionServiceImpl;
import com.ecnu.six.pethospital.exam.vo.QuestionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author HavenTong
 * @date 2021/3/29 下午9:27
 */
@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceImpl questionService;

    @Test
    @DisplayName("成功新增问题")
    void shouldAddCorrectQuestion() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .admId(1)
                .diseaseId(2)
                .stem("选择题")
                .choice("A|B|C|D")
                .answer("AB")
                .build();
        ResultActions perform = mockMvc.perform(
                post("/question/addQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(questionRequest))
        );
        verify(questionService, times(1))
                .addQuestion(questionRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("成功删除考题")
    void shouldDeleteCorrectQuestion() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(3).build();
        ResultActions perform = mockMvc.perform(
                post("/question/deleteQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(questionRequest))
        );
        verify(questionService, times(1))
                .deleteQuestionById(questionRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("成功通过疾病获得考题")
    void shouldFindCorrectQuestionsByDiseaseId() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .diseaseId(1).build();
        QuestionResponse response0 = QuestionResponse.builder()
                .questionId(4)
                .admId(1)
                .admName("haven")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题1")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("A")
                .questionCreatedTime("2021-03-29 10:00")
                .questionUpdatedTime("2021-03-30 10:20").build();
        QuestionResponse response1 = QuestionResponse.builder()
                .questionId(5)
                .admId(2)
                .admName("onion")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题2")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("B")
                .questionCreatedTime("2021-04-03 08:00")
                .questionUpdatedTime("2021-04-04 10:00").build();
        List<QuestionResponse> responses = new ArrayList<>(Arrays.asList(response0, response1));
        when(questionService.findQuestionsByDiseaseId(questionRequest)).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/question/findQuestionByDiseaseId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(questionRequest))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].questionId").value(4))
                .andExpect(jsonPath("$.data[0].admId").value(1))
                .andExpect(jsonPath("$.data[0].admName").value("haven"))
                .andExpect(jsonPath("$.data[0].diseaseId").value(1))
                .andExpect(jsonPath("$.data[0].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[0].stem").value("传染病选择题1"))
                .andExpect(jsonPath("$.data[0].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[0].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[0].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[0].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[0].answer").value("A"))
                .andExpect(jsonPath("$.data[0].questionCreatedTime").value("2021-03-29 10:00"))
                .andExpect(jsonPath("$.data[0].questionUpdatedTime").value("2021-03-30 10:20"))
                .andExpect(jsonPath("$.data[1].questionId").value(5))
                .andExpect(jsonPath("$.data[1].admId").value(2))
                .andExpect(jsonPath("$.data[1].admName").value("onion"))
                .andExpect(jsonPath("$.data[1].diseaseId").value(1))
                .andExpect(jsonPath("$.data[1].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[1].stem").value("传染病选择题2"))
                .andExpect(jsonPath("$.data[1].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[1].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[1].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[1].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[1].answer").value("B"))
                .andExpect(jsonPath("$.data[1].questionCreatedTime").value("2021-04-03 08:00"))
                .andExpect(jsonPath("$.data[1].questionUpdatedTime").value("2021-04-04 10:00"));
    }

    @Test
    @DisplayName("成功通过关键字获得考题")
    void shouldFindCorrectQuestionsByKeyword() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .keyword("传染病").build();
        QuestionResponse response0 = QuestionResponse.builder()
                .questionId(4)
                .admId(1)
                .admName("haven")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题1")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("A")
                .questionCreatedTime("2021-03-29 10:00")
                .questionUpdatedTime("2021-03-30 10:20").build();
        QuestionResponse response1 = QuestionResponse.builder()
                .questionId(5)
                .admId(2)
                .admName("onion")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题2")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("B")
                .questionCreatedTime("2021-04-03 08:00")
                .questionUpdatedTime("2021-04-04 10:00").build();
        List<QuestionResponse> responses = new ArrayList<>(Arrays.asList(response0, response1));
        when(questionService.findQuestionsByKeyword(questionRequest)).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/question/findQuestionByKeyword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(questionRequest))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].questionId").value(4))
                .andExpect(jsonPath("$.data[0].admId").value(1))
                .andExpect(jsonPath("$.data[0].admName").value("haven"))
                .andExpect(jsonPath("$.data[0].diseaseId").value(1))
                .andExpect(jsonPath("$.data[0].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[0].stem").value("传染病选择题1"))
                .andExpect(jsonPath("$.data[0].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[0].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[0].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[0].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[0].answer").value("A"))
                .andExpect(jsonPath("$.data[0].questionCreatedTime").value("2021-03-29 10:00"))
                .andExpect(jsonPath("$.data[0].questionUpdatedTime").value("2021-03-30 10:20"))
                .andExpect(jsonPath("$.data[1].questionId").value(5))
                .andExpect(jsonPath("$.data[1].admId").value(2))
                .andExpect(jsonPath("$.data[1].admName").value("onion"))
                .andExpect(jsonPath("$.data[1].diseaseId").value(1))
                .andExpect(jsonPath("$.data[1].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[1].stem").value("传染病选择题2"))
                .andExpect(jsonPath("$.data[1].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[1].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[1].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[1].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[1].answer").value("B"))
                .andExpect(jsonPath("$.data[1].questionCreatedTime").value("2021-04-03 08:00"))
                .andExpect(jsonPath("$.data[1].questionUpdatedTime").value("2021-04-04 10:00"));
    }

    @Test
    @DisplayName("成功通过考题id获得考题")
    void shouldFindCorrectQuestionsById() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(1).build();
        QuestionResponse response = QuestionResponse.builder()
                .questionId(1)
                .admId(1)
                .admName("haven")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题1")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("A")
                .questionCreatedTime("2021-03-29 10:00")
                .questionUpdatedTime("2021-03-30 10:20").build();
        when(questionService.findQuestionById(questionRequest)).thenReturn(response);
        ResultActions perform = mockMvc.perform(
                post("/question/findQuestionById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(questionRequest))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(1))
                .andExpect(jsonPath("$.data.admId").value(1))
                .andExpect(jsonPath("$.data.admName").value("haven"))
                .andExpect(jsonPath("$.data.diseaseId").value(1))
                .andExpect(jsonPath("$.data.diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data.stem").value("传染病选择题1"))
                .andExpect(jsonPath("$.data.choices[0]").value("选A"))
                .andExpect(jsonPath("$.data.choices[1]").value("选B"))
                .andExpect(jsonPath("$.data.choices[2]").value("选C"))
                .andExpect(jsonPath("$.data.choices[3]").value("选D"))
                .andExpect(jsonPath("$.data.answer").value("A"))
                .andExpect(jsonPath("$.data.questionCreatedTime").value("2021-03-29 10:00"))
                .andExpect(jsonPath("$.data.questionUpdatedTime").value("2021-03-30 10:20"));
    }

    @Test
    @DisplayName("成功获得考题列表")
    void shouldFindAllCorrectQuestions() throws Exception {
        QuestionResponse response0 = QuestionResponse.builder()
                .questionId(4)
                .admId(1)
                .admName("haven")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题1")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("A")
                .questionCreatedTime("2021-03-29 10:00")
                .questionUpdatedTime("2021-03-30 10:20").build();
        QuestionResponse response1 = QuestionResponse.builder()
                .questionId(5)
                .admId(2)
                .admName("onion")
                .diseaseId(1L)
                .diseaseName("传染病")
                .stem("传染病选择题2")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("B")
                .questionCreatedTime("2021-04-03 08:00")
                .questionUpdatedTime("2021-04-04 10:00").build();
        List<QuestionResponse> responses = new ArrayList<>(Arrays.asList(response0, response1));
        when(questionService.findAll()).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/question/findAll")
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].questionId").value(4))
                .andExpect(jsonPath("$.data[0].admId").value(1))
                .andExpect(jsonPath("$.data[0].admName").value("haven"))
                .andExpect(jsonPath("$.data[0].diseaseId").value(1))
                .andExpect(jsonPath("$.data[0].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[0].stem").value("传染病选择题1"))
                .andExpect(jsonPath("$.data[0].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[0].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[0].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[0].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[0].answer").value("A"))
                .andExpect(jsonPath("$.data[0].questionCreatedTime").value("2021-03-29 10:00"))
                .andExpect(jsonPath("$.data[0].questionUpdatedTime").value("2021-03-30 10:20"))
                .andExpect(jsonPath("$.data[1].questionId").value(5))
                .andExpect(jsonPath("$.data[1].admId").value(2))
                .andExpect(jsonPath("$.data[1].admName").value("onion"))
                .andExpect(jsonPath("$.data[1].diseaseId").value(1))
                .andExpect(jsonPath("$.data[1].diseaseName").value("传染病"))
                .andExpect(jsonPath("$.data[1].stem").value("传染病选择题2"))
                .andExpect(jsonPath("$.data[1].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data[1].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data[1].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data[1].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data[1].answer").value("B"))
                .andExpect(jsonPath("$.data[1].questionCreatedTime").value("2021-04-03 08:00"))
                .andExpect(jsonPath("$.data[1].questionUpdatedTime").value("2021-04-04 10:00"));
    }

    @Test
    @DisplayName("成功更新问题")
    void shouldUpdateCorrectQuestion() throws Exception {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .questionId(1)
                .admId(1)
                .diseaseId(2)
                .stem("选择题")
                .choice("A|B|C|D")
                .answer("AB")
                .build();
        ResultActions perform = mockMvc.perform(
                post("/question/updateQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(questionRequest))
        );
        verify(questionService, times(1))
                .updateQuestion(questionRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

}