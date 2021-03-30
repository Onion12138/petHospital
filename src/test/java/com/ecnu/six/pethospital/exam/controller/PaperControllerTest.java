package com.ecnu.six.pethospital.exam.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.exam.domain.QuestionScore;
import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.service.impl.PaperServiceImpl;
import com.ecnu.six.pethospital.exam.vo.PaperQuestionResponse;
import com.ecnu.six.pethospital.exam.vo.PaperResponse;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2021/3/30 上午9:58
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class PaperControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaperServiceImpl paperService;

    @Test
    @DisplayName("正确添加试卷")
    void shouldAddCorrectPaper() throws Exception {
        List<QuestionScore> questionScores = Arrays
                .asList(QuestionScore.builder().questionId(1).score(50).build(),
                        QuestionScore.builder().questionId(2).score(50).build());
        PaperRequest paperRequest = PaperRequest.builder()
                .paperName("期末试卷")
                .admId(1)
                .duration(60)
                .questionScores(questionScores).build();
        ResultActions perform = mockMvc.perform(
            post("/paper/addPaper")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(paperRequest))
        );
        verify(paperService, times(1))
                .addPaper(paperRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("正确修改试卷")
    void shouldUpdateCorrectPaper() throws Exception {
        List<QuestionScore> questionScores = Arrays
                .asList(QuestionScore.builder().questionId(1).score(50).build(),
                        QuestionScore.builder().questionId(2).score(50).build());
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1)
                .paperName("期末试卷")
                .admId(1)
                .duration(90)
                .questionScores(questionScores).build();
        ResultActions perform = mockMvc.perform(
                post("/paper/updatePaper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(paperRequest))
        );
        verify(paperService, times(1))
                .updatePaper(paperRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("正确删除试卷")
    void shouldDeleteCorrectPaper() throws Exception {
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1)
                .build();
        ResultActions perform = mockMvc.perform(
                post("/paper/deletePaper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(paperRequest))
        );
        verify(paperService, times(1))
                .deletePaper(paperRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("获取正确的试卷")
    void shouldFindCorrectPaper() throws Exception {
        PaperRequest paperRequest = PaperRequest.builder()
                .paperId(1)
                .build();
        PaperQuestionResponse questionResponse0 = PaperQuestionResponse.builder()
                .questionId(1)
                .stem("单选题0")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("A")
                .score(50).build();
        PaperQuestionResponse questionResponse1 = PaperQuestionResponse.builder()
                .questionId(2)
                .stem("单选题1")
                .choices(new String[]{"选A", "选B", "选C", "选D"})
                .answer("B")
                .score(50).build();
        List<PaperQuestionResponse> questionResponses = new ArrayList<>(Arrays.asList(questionResponse0, questionResponse1));
        PaperResponse paperResponse = PaperResponse.builder()
                .paperId(1)
                .paperName("期末试卷")
                .admId(1)
                .admName("haven")
                .duration(120)
                .questions(questionResponses).build();
        when(paperService.findPaperById(paperRequest)).thenReturn(paperResponse);
        ResultActions perform = mockMvc.perform(
                post("/paper/findPaperById")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(paperRequest))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.paperId").value(1))
                .andExpect(jsonPath("$.data.paperName").value("期末试卷"))
                .andExpect(jsonPath("$.data.admId").value(1))
                .andExpect(jsonPath("$.data.admName").value("haven"))
                .andExpect(jsonPath("$.data.duration").value(120))
                .andExpect(jsonPath("$.data.questions[0].questionId").value(1))
                .andExpect(jsonPath("$.data.questions[0].stem").value("单选题0"))
                .andExpect(jsonPath("$.data.questions[0].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data.questions[0].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data.questions[0].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data.questions[0].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data.questions[0].answer").value("A"))
                .andExpect(jsonPath("$.data.questions[0].score").value(50))
                .andExpect(jsonPath("$.data.questions[1].questionId").value(2))
                .andExpect(jsonPath("$.data.questions[1].stem").value("单选题1"))
                .andExpect(jsonPath("$.data.questions[1].choices[0]").value("选A"))
                .andExpect(jsonPath("$.data.questions[1].choices[1]").value("选B"))
                .andExpect(jsonPath("$.data.questions[1].choices[2]").value("选C"))
                .andExpect(jsonPath("$.data.questions[1].choices[3]").value("选D"))
                .andExpect(jsonPath("$.data.questions[1].answer").value("B"))
                .andExpect(jsonPath("$.data.questions[1].score").value(50));
    }

    @Test
    @DisplayName("获取正确的试卷列表")
    void shouldFindAllCorrectPaper() throws Exception {
        PaperResponse response0 = PaperResponse.builder()
                .paperId(1)
                .paperName("期末试卷A")
                .admId(1)
                .admName("haven")
                .duration(90)
                .paperCreatedTime("2021-03-29 10:00")
                .paperUpdatedTime("2021-03-30 10:20")
                .build();
        PaperResponse response1 = PaperResponse.builder()
                .paperId(2)
                .paperName("期末试卷B")
                .admId(2)
                .admName("onion")
                .duration(120)
                .paperCreatedTime("2021-04-03 08:00")
                .paperUpdatedTime("2021-04-04 10:00")
                .build();
        List<PaperResponse> responses = new ArrayList<>(Arrays.asList(response0, response1));
        when(paperService.findAll()).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/paper/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].paperId").value(1))
                .andExpect(jsonPath("$.data[0].paperName").value("期末试卷A"))
                .andExpect(jsonPath("$.data[0].admId").value(1))
                .andExpect(jsonPath("$.data[0].admName").value("haven"))
                .andExpect(jsonPath("$.data[0].duration").value(90))
                .andExpect(jsonPath("$.data[0].paperCreatedTime").value("2021-03-29 10:00"))
                .andExpect(jsonPath("$.data[0].paperUpdatedTime").value("2021-03-30 10:20"))
                .andExpect(jsonPath("$.data[1].paperId").value(2))
                .andExpect(jsonPath("$.data[1].paperName").value("期末试卷B"))
                .andExpect(jsonPath("$.data[1].admId").value(2))
                .andExpect(jsonPath("$.data[1].admName").value("onion"))
                .andExpect(jsonPath("$.data[1].duration").value(120))
                .andExpect(jsonPath("$.data[1].paperCreatedTime").value("2021-04-03 08:00"))
                .andExpect(jsonPath("$.data[1].paperUpdatedTime").value("2021-04-04 10:00"));
    }
}