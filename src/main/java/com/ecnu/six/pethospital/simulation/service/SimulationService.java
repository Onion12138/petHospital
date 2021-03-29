package com.ecnu.six.pethospital.simulation.service;

import com.ecnu.six.pethospital.guide.domain.Role;
import com.ecnu.six.pethospital.simulation.dto.ProcessRequest;
import com.ecnu.six.pethospital.simulation.dto.StepRequest;
import com.ecnu.six.pethospital.simulation.vo.ProcessResponse;
import com.ecnu.six.pethospital.simulation.vo.RoleResponse;
import com.ecnu.six.pethospital.simulation.vo.StepResponse;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:21
 */
public interface SimulationService {
    List<RoleResponse> getRoles();
    List<ProcessResponse> findProcessesByRoleId(ProcessRequest processRequest);
    List<StepResponse> findStepsByProcessId(StepRequest stepRequest);

}
