package com.ecnu.six.pethospital.simulation.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.simulation.dto.ProcessRequest;
import com.ecnu.six.pethospital.simulation.dto.StepRequest;
import com.ecnu.six.pethospital.simulation.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:16
 */
@RestController
@RequestMapping("/simulation")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/getRoles")
    public ResponseData getRoles() {
        return ResponseData.success(simulationService.getRoles());
    }

    @PostMapping("/findProcessByRoleId")
    public ResponseData findProcessByRoleId(@RequestBody ProcessRequest processRequest) {
        return ResponseData.success(simulationService.findProcessesByRoleId(processRequest));
    }

    @PostMapping("/findStepsByProcessId")
    public ResponseData findStepsByProcessId(@RequestBody StepRequest stepRequest) {
        return ResponseData.success(simulationService.findStepsByProcessId(stepRequest));
    }

    @PostMapping("/findAllProcess")
    public ResponseData findAllProcess() {
        return ResponseData.success(simulationService.findAllProcess());
    }

    @PostMapping("/deleteProcessById")
    public ResponseData deleteProcessById(@RequestBody ProcessRequest processRequest) {
        simulationService.deleteProcessById(processRequest);
        return ResponseData.success();
    }

    @PostMapping("/addProcessWithSteps")
    public ResponseData addProcessWithSteps(@RequestBody ProcessRequest processRequest) {
        simulationService.addProcessWithSteps(processRequest);
        return ResponseData.success();
    }

    @PostMapping("/updateProcess")
    public ResponseData updateProcess(@RequestBody ProcessRequest processRequest) {
        simulationService.updateProcess(processRequest);
        return ResponseData.success();
    }

    @PostMapping("/updateStep")
    public ResponseData updateStep(@RequestBody StepRequest stepRequest) {
        simulationService.updateStep(stepRequest);
        return ResponseData.success();
    }
}
