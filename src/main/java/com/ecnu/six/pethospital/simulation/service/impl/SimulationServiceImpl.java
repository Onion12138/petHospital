package com.ecnu.six.pethospital.simulation.service.impl;

import com.ecnu.six.pethospital.simulation.dao.ProcessDao;
import com.ecnu.six.pethospital.guide.dao.RoleDao;
import com.ecnu.six.pethospital.simulation.dao.StepDao;
import com.ecnu.six.pethospital.simulation.domain.Process;
import com.ecnu.six.pethospital.guide.domain.Role;
import com.ecnu.six.pethospital.simulation.domain.Step;
import com.ecnu.six.pethospital.simulation.dto.ProcessRequest;
import com.ecnu.six.pethospital.simulation.dto.StepRequest;
import com.ecnu.six.pethospital.simulation.service.SimulationService;
import com.ecnu.six.pethospital.simulation.vo.ProcessResponse;
import com.ecnu.six.pethospital.simulation.vo.RoleResponse;
import com.ecnu.six.pethospital.simulation.vo.StepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:22
 */
@Service
@Slf4j
public class SimulationServiceImpl implements SimulationService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private StepDao stepDao;

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roles = roleDao.findAll();
        List<RoleResponse> roleResponses = new LinkedList<>();
        for(Role role : roles) {
            roleResponses.add(RoleResponse.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .picture(role.getPicture())
                    .build());
        }
        return roleResponses;
    }

    @Override
    public List<ProcessResponse> findProcessesByRoleId(ProcessRequest processRequest) {
        List<Process> processes = processDao.findProcessByRoleId(processRequest.getRoleId());
        List<ProcessResponse> processResponses = new LinkedList<>();
        for(Process process : processes) {
            processResponses.add(ProcessResponse.builder()
                    .id(process.getId())
                    .name(process.getName())
                    .build());
        }
        return processResponses;
    }

    @Override
    public List<StepResponse> findStepsByProcessId(StepRequest stepRequest) {
        List<Step> steps = stepDao.findStepsByProcessId(stepRequest.getProcessId());
        List<StepResponse> stepResponses = new LinkedList<>();
        for(Step step : steps) {
            stepResponses.add(StepResponse.builder()
                    .name(step.getName())
                    .message(step.getMessage())
                    .picture(step.getPicture())
                    .video(step.getVideo())
                    .build());
        }
        return stepResponses;
    }
}
