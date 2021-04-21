package com.ecnu.six.pethospital.simulation.service;

import com.ecnu.six.pethospital.guide.dao.RoleDao;
import com.ecnu.six.pethospital.guide.domain.Role;
import com.ecnu.six.pethospital.simulation.dao.ProcessDao;
import com.ecnu.six.pethospital.simulation.dao.StepDao;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/4/13 16:17
 */
@SpringBootTest
@Slf4j
public class SimulationServiceTest {
    @Mock
    private RoleDao roleDao;

    @Mock
    private ProcessDao processDao;

    @Mock
    private StepDao stepDao;

    @InjectMocks
    private SimulationServiceImpl simulationService;

    @Test
    @DisplayName("正确返回所有的角色")
    void shouldGetRoles() {
        Role role1 = Role.builder()
                .id(1)
                .name("医生")
                .picture("www.pic1.link")
                .message("信息1")
                .build();
        Role role2 = Role.builder()
                .id(2)
                .name("助手")
                .picture("www.pic2.link")
                .message("信息2")
                .build();
        List<Role> roles = new ArrayList<>(Arrays.asList(role1, role2));
        when(roleDao.findAll()).thenReturn(roles);
        List<RoleResponse> responses = simulationService.getRoles();
        assertAll(
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(1, responses.get(0).getId()),
                () -> assertEquals("医生", responses.get(0).getName()),
                () -> assertEquals("www.pic1.link", responses.get(0).getPicture()),
                () -> assertEquals("信息1", responses.get(0).getMessage()),
                () -> assertEquals(2, responses.get(1).getId()),
                () -> assertEquals("助手", responses.get(1).getName()),
                () -> assertEquals("www.pic2.link", responses.get(1).getPicture()),
                () -> assertEquals("信息2", responses.get(1).getMessage())
        );
    }

    @Test
    @DisplayName("根据角色 ID 正确获得学习流程")
    void shouldFindProcessByRoleId() {
        ProcessRequest processRequest = ProcessRequest.builder()
                .roleId(1)
                .build();
        Process process1 = Process.builder()
                .id(1)
                .name("流程1")
                .build();
        Process process2 = Process.builder()
                .id(2)
                .name("流程2")
                .build();
        List<Process> processes = new ArrayList<>(Arrays.asList(process1, process2));
        when(processDao.findProcessByRoleId(1)).thenReturn(processes);
        List<ProcessResponse> responses = simulationService.findProcessesByRoleId(processRequest);
        assertAll(
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(1, responses.get(0).getId()),
                () -> assertEquals("流程1", responses.get(0).getName()),
                () -> assertEquals(2, responses.get(1).getId()),
                () -> assertEquals("流程2", responses.get(1).getName())
        );
    }

    @Test
    @DisplayName("正确返回所有的流程")
    void shouldFindAllProcess() {
        Process process1 = Process.builder()
                .id(1)
                .name("流程1")
                .roleId(1)
                .build();
        Process process2 = Process.builder()
                .id(2)
                .name("流程2")
                .roleId(2)
                .build();
        List<Process> processes = new ArrayList<>(Arrays.asList(process1, process2));
        when(processDao.findAllProcess()).thenReturn(processes);
        List<ProcessResponse> responses = simulationService.findAllProcess();
        assertAll(
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(1, responses.get(0).getId()),
                () -> assertEquals("流程1", responses.get(0).getName()),
                () -> assertEquals(1, responses.get(0).getRoleId()),
                () -> assertEquals(2, responses.get(1).getId()),
                () -> assertEquals("流程2", responses.get(1).getName()),
                () -> assertEquals(2, responses.get(1).getRoleId())
        );
    }

    @Test
    @DisplayName("通过流程ID正确删除流程")
    void shouldDeleteProcessById() {
        ProcessRequest processRequest = ProcessRequest.builder()
                .id(1)
                .build();
        ArgumentCaptor<Integer> processIdCaptor = ArgumentCaptor.forClass(Integer.class);
        simulationService.deleteProcessById(processRequest);
        verify(processDao, times(1)).deleteProcessById(processIdCaptor.capture());
        assertEquals(1, processIdCaptor.getValue());
    }

    @Test
    @DisplayName("正确添加带步骤的流程")
    void shouldAddProcessWithSteps() {
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
        ArgumentCaptor<Process> processArgumentCaptor = ArgumentCaptor.forClass(Process.class);
        ArgumentCaptor<Step> stepArgumentCaptor = ArgumentCaptor.forClass(Step.class);
        simulationService.addProcessWithSteps(processRequest);
        when(processDao.addProcess(any())).thenReturn(1);
        verify(processDao, times(1)).addProcess(processArgumentCaptor.capture());
        verify(stepDao, times(2)).addStep(stepArgumentCaptor.capture());
        Process process = processArgumentCaptor.getValue();
        List<Step> steps = stepArgumentCaptor.getAllValues();
        assertAll(
                () -> assertEquals("流程1", process.getName()),
                () -> assertEquals(1, process.getRoleId()),
                () -> assertEquals("步骤1", steps.get(0).getName()),
                () -> assertEquals("信息1", steps.get(0).getMessage()),
                () -> assertEquals("www.pic1.link", steps.get(0).getPicture()),
                () -> assertEquals("www.vid1.link", steps.get(0).getVideo()),
                () -> assertEquals("步骤2", steps.get(1).getName()),
                () -> assertEquals("信息2", steps.get(1).getMessage()),
                () -> assertEquals("www.pic2.link", steps.get(1).getPicture()),
                () -> assertEquals("www.vid2.link", steps.get(1).getVideo())
        );
    }

    @Test
    @DisplayName("正确更新流程")
    void shouldUpdateProcess() {
        ProcessRequest processRequest = ProcessRequest.builder()
                .id(1)
                .name("流程1")
                .roleId(2)
                .build();
        ArgumentCaptor<Process> processArgumentCaptor = ArgumentCaptor.forClass(Process.class);
        simulationService.updateProcess(processRequest);
        verify(processDao, times(1)).updateProcess(processArgumentCaptor.capture());
        Process process = processArgumentCaptor.getValue();
        assertAll(
                () -> assertEquals(1, process.getId()),
                () -> assertEquals("流程1", process.getName()),
                () -> assertEquals(2, process.getRoleId())
        );
    }

    @Test
    @DisplayName("正确更新步骤")
    void shouldUpdateStep() {
        StepRequest stepRequest = StepRequest.builder()
                .id(1)
                .name("步骤1")
                .message("信息1")
                .picture("www.pic1.link")
                .video("www.vid1.link")
                .build();
        ArgumentCaptor<Step> stepArgumentCaptor = ArgumentCaptor.forClass(Step.class);
        simulationService.updateStep(stepRequest);
        verify(stepDao, times(1)).updateStep(stepArgumentCaptor.capture());
        Step step = stepArgumentCaptor.getValue();
        assertAll(
                () -> assertEquals(1, step.getId()),
                () -> assertEquals("步骤1", step.getName()),
                () -> assertEquals("信息1", step.getMessage()),
                () -> assertEquals("www.pic1.link", step.getPicture()),
                () -> assertEquals("www.vid1.link", step.getVideo())
        );
    }

    @Test
    @DisplayName("根据流程ID正确获取步骤")
    void shouldFindStepsByProcessId() {
        StepRequest stepRequest = StepRequest.builder()
                .processId(1)
                .build();
        Step step1 = Step.builder()
                .id(1)
                .name("步骤1")
                .message("信息1")
                .picture("www.pic1.link")
                .video("www.vid1.link")
                .build();

        Step step2 = Step.builder()
                .id(2)
                .name("步骤2")
                .message("信息2")
                .picture("www.pic2.link")
                .video("www.vid2.link")
                .build();

        List<Step> steps = new LinkedList<>(Arrays.asList(step1, step2));
        when(stepDao.findStepsByProcessId(1)).thenReturn(steps);
        List<StepResponse> responses = simulationService.findStepsByProcessId(stepRequest);
        assertAll(
                () -> assertEquals(1, responses.get(0).getId()),
                () -> assertEquals("步骤1", responses.get(0).getName()),
                () -> assertEquals("信息1", responses.get(0).getMessage()),
                () -> assertEquals("www.pic1.link", responses.get(0).getPicture()),
                () -> assertEquals("www.vid1.link", responses.get(0).getVideo()),
                () -> assertEquals(2, responses.get(1).getId()),
                () -> assertEquals("步骤2", responses.get(1).getName()),
                () -> assertEquals("信息2", responses.get(1).getMessage()),
                () -> assertEquals("www.pic2.link", responses.get(1).getPicture()),
                () -> assertEquals("www.vid2.link", responses.get(1).getVideo())
        );
    }
}



