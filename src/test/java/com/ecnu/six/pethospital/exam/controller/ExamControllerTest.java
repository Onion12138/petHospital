package com.ecnu.six.pethospital.exam.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.exam.dto.ExamScoreRequest;
import com.ecnu.six.pethospital.exam.service.impl.ExamServiceImpl;
import com.ecnu.six.pethospital.exam.vo.ExamResponse;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author HavenTong
 * @date 2021/3/30 上午10:53
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ExamControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamServiceImpl examService;

    @Test
    @DisplayName("成功添加考试")
    void shouldAddCorrectExam() throws Exception {
        ExamRequest examRequest = ExamRequest.builder()
                .paperId(1)
                .examName("期末考试")
                .creatorId(1)
                .startTime("2021-04-03 08:00").build();
        ResultActions perform = mockMvc.perform(
                post("/exam/addExam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(examRequest))
        );
        verify(examService, times(1))
                .addExam(examRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("成功获得可用考试")
    void shouldFindAvailableExams() throws Exception {
        ExamRequest examRequest = ExamRequest.builder().usrId(1).build();
        ExamResponse response0 = ExamResponse.builder()
                .examId(1)
                .paperId(2)
                .examName("期末考试A")
                .admId(1)
                .admName("haven")
                .startTime("2021-03-29 10:00:00")
                .endTime("2021-03-29 11:30:00")
                .totalScore(100)
                .questionNums(5)
                .finished(true)
                .build();
        ExamResponse response1 = ExamResponse.builder()
                .examId(3)
                .paperId(4)
                .examName("期末考试B")
                .admId(2)
                .admName("onion")
                .startTime("2021-04-03 08:00:00")
                .endTime("2021-04-04 10:00:00")
                .totalScore(120)
                .questionNums(6)
                .finished(false)
                .build();
        List<ExamResponse> responses = new ArrayList<>(Arrays.asList(response0, response1));
        when(examService.findAvailableExams(examRequest)).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/exam/findAvailableExams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(examRequest))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].examId").value(1))
                .andExpect(jsonPath("$.data[0].paperId").value(2))
                .andExpect(jsonPath("$.data[0].examName").value("期末考试A"))
                .andExpect(jsonPath("$.data[0].admId").value(1))
                .andExpect(jsonPath("$.data[0].admName").value("haven"))
                .andExpect(jsonPath("$.data[0].startTime").value("2021-03-29 10:00:00"))
                .andExpect(jsonPath("$.data[0].endTime").value("2021-03-29 11:30:00"))
                .andExpect(jsonPath("$.data[0].totalScore").value(100))
                .andExpect(jsonPath("$.data[0].questionNums").value(5))
                .andExpect(jsonPath("$.data[0].finished").value(true))
                .andExpect(jsonPath("$.data[1].examId").value(3))
                .andExpect(jsonPath("$.data[1].paperId").value(4))
                .andExpect(jsonPath("$.data[1].examName").value("期末考试B"))
                .andExpect(jsonPath("$.data[1].admId").value(2))
                .andExpect(jsonPath("$.data[1].admName").value("onion"))
                .andExpect(jsonPath("$.data[1].startTime").value("2021-04-03 08:00:00"))
                .andExpect(jsonPath("$.data[1].endTime").value("2021-04-04 10:00:00"))
                .andExpect(jsonPath("$.data[1].totalScore").value(120))
                .andExpect(jsonPath("$.data[1].questionNums").value(6))
                .andExpect(jsonPath("$.data[1].finished").value(false));

    }

    @Test
    @DisplayName("成功记录考试分数")
    void shouldSaveCorrectScore() throws Exception {
        ExamScoreRequest examScoreRequest = ExamScoreRequest.builder()
                .usrId(1)
                .examId(2)
                .score(75)
                .build();
        ResultActions perform = mockMvc.perform(
                post("/exam/saveScore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(examScoreRequest))
        );
        verify(examService, times(1))
                .saveScore(examScoreRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }
}