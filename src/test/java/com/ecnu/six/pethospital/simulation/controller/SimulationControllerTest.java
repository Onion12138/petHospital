package com.ecnu.six.pethospital.simulation.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.simulation.domain.Process;
import com.ecnu.six.pethospital.simulation.domain.Step;
import com.ecnu.six.pethospital.simulation.dto.ProcessRequest;
import com.ecnu.six.pethospital.simulation.dto.StepRequest;
import com.ecnu.six.pethospital.simulation.service.impl.SimulationServiceImpl;
import com.ecnu.six.pethospital.simulation.vo.ProcessResponse;
import com.ecnu.six.pethospital.simulation.vo.RoleResponse;
import com.ecnu.six.pethospital.simulation.vo.StepResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/4/14 0:17
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class SimulationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulationServiceImpl simulationService;

    @Test
    @DisplayName("正确获取角色")
    void shouldGetRoles() throws Exception {
        RoleResponse role1Response = RoleResponse.builder()
                .id(1)
                .name("角色1")
                .picture("www.pic1.link")
                .message("信息1")
                .build();
        RoleResponse role2Response = RoleResponse.builder()
                .id(2)
                .name("角色2")
                .picture("www.pic2.link")
                .message("信息2")
                .build();
        List<RoleResponse> responses = new LinkedList<>(Arrays.asList(role1Response, role2Response));
        when(simulationService.getRoles()).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/simulation/getRoles")
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("角色1"))
                .andExpect(jsonPath("$.data[0].picture").value("www.pic1.link"))
                .andExpect(jsonPath("$.data[0].message").value("信息1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("角色2"))
                .andExpect(jsonPath("$.data[1].picture").value("www.pic2.link"))
                .andExpect(jsonPath("$.data[1].message").value("信息2"));
    }

    @Test
    @DisplayName("根据流程ID正确返回步骤")
    void shouldFindProcessByRoleId() throws Exception {
        ProcessResponse process1Response = ProcessResponse.builder()
                .id(1)
                .name("流程1")
                .build();
        ProcessResponse process2Response = ProcessResponse.builder()
                .id(2)
                .name("流程2")
                .build();
        List<ProcessResponse> responses = new LinkedList<>(Arrays.asList(process1Response, process2Response));
        ProcessRequest request = ProcessRequest.builder()
                .roleId(1)
                .build();
        when(simulationService.findProcessesByRoleId(request)).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/simulation/findProcessByRoleId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(request))
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("流程1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("流程2"));
    }

    @Test
    @DisplayName("正确返回所有流程")
    void shouldFindAllProcess() throws Exception {
        ProcessResponse process1Response = ProcessResponse.builder()
                .id(1)
                .name("流程1")
                .build();
        ProcessResponse process2Response = ProcessResponse.builder()
                .id(2)
                .name("流程2")
                .build();
        List<ProcessResponse> responses = new LinkedList<>(Arrays.asList(process1Response, process2Response));
        when(simulationService.findAllProcess()).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/simulation/findAllProcess")
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("流程1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("流程2"));
    }

    @Test
    @DisplayName("正确通过流程ID删除流程")
    void shouldDeleteProcessById() throws Exception {
        ProcessRequest processRequest = ProcessRequest.builder()
                .id(1)
                .build();

        ResultActions perform = mockMvc.perform(
                post("/simulation/deleteProcessById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(processRequest))
        );
        verify(simulationService, times(1)).deleteProcessById(processRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("添加带步骤的流程")
    void shouldAddProcessWithSteps() throws Exception {
        StepRequest step1Request = StepRequest.builder()
                .name("步骤1")
                .message("信息1")
                .picture("www.pic1.link")
                .video("www.vid1.link")
                .build();
        StepRequest step2Request = StepRequest.builder()
                .name("步骤2")
                .message("信息2")
                .picture("www.pic2.link")
                .video("www.vid2.link")
                .build();
        ProcessRequest processRequest = ProcessRequest.builder()
                .name("流程1")
                .roleId(1)
                .steps(new ArrayList<>(Arrays.asList(step1Request, step2Request)))
                .build();

        ResultActions perform = mockMvc.perform(
                post("/simulation/addProcessWithSteps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(processRequest))
        );
        verify(simulationService, times(1)).addProcessWithSteps(processRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("正确更新流程")
    void shouldUpdateProcess() throws Exception {
        ProcessRequest processRequest = ProcessRequest.builder()
                .id(1)
                .name("流程1")
                .roleId(2)
                .build();
        ResultActions perform = mockMvc.perform(
                post("/simulation/updateProcess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(processRequest))
        );
        verify(simulationService, times(1)).updateProcess(processRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

    @Test
    @DisplayName("正确更新步骤")
    void shouldUpdateStep() throws Exception {
        StepRequest stepRequest = StepRequest.builder()
                .id(1)
                .name("步骤1")
                .message("信息1")
                .picture("www.pic1.link")
                .video("www.vid1.link")
                .build();
        ResultActions perform = mockMvc.perform(
                post("/simulation/updateStep")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(stepRequest))
        );
        verify(simulationService, times(1)).updateStep(stepRequest);
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }

}
