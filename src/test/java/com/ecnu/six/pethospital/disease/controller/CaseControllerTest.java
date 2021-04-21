package com.ecnu.six.pethospital.disease.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.disease.dto.CaseDTO;
import com.ecnu.six.pethospital.disease.service.CaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author onion
 * @date 2021/4/14 -1:19 下午
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

//    @GetMapping("/findOne")
//    public ResponseData findOne(@RequestParam Long caseId) {
//        Caze caze = caseService.findOne(caseId);
//        return ResponseData.success(caze);
//    }
//    @GetMapping("/findAll")
//    public ResponseData findAll(@RequestParam(defaultValue = "") String name,
//                                @RequestParam(defaultValue = "1") Integer page,
//                                @RequestParam(defaultValue = "5") Integer size) {
//        Page<Caze> casePage = null;
//        if ("".equals(name)) {
//            casePage = caseService.findAll(page, size);
//        }else {
//            casePage = caseService.findByDisease(name, page, size);
//        }
//
//        return ResponseData.success(casePage);
//    }
//    @PostMapping("/deleteOne")
//    public ResponseData deleteOne(@RequestParam Long caseId) {
//        caseService.deleteOne(caseId);
//        return ResponseData.success();
//    }

//    @PostMapping("/updateOne")
//    public ResponseData updateOne(@RequestBody CaseDTO caseDTO) {
//        caseService.updateOne(caseDTO);
//        return ResponseData.success();
//    }
//    @GetMapping("findByDisease")
//    public ResponseData findByDisease(@RequestParam String name,
//                                      @RequestParam(defaultValue = "1") Integer page,
//                                      @RequestParam(defaultValue = "5") Integer size) {
//        return ResponseData.success(caseService.findByDisease(name, page, size));
//    }


    //    @PostMapping("/addOne")
//    public ResponseData addOne(@RequestBody CaseDTO caseDTO) {
//        caseService.addOne(caseDTO);
//        return ResponseData.success();
//    }
//    @Test
//    @DisplayName("成功添加病例")
//    void shouldAddCorrectCase() throws Exception {
//        CaseDTO caseDTO = new CaseDTO(100L, "肺部感染","新冠肺炎","确诊新冠肺炎","服毒",
//                true, LocalDateTime.now().toString(),"http://www.baidu.com","");
//        ResultActions perform = mockMvc.perform(
//                post("/case/addOne")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JSON.toJSONString(caseDTO))
//        );
//        verify(caseService, times(1))
//                .addOne(caseDTO);
//        perform.andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.msg").value("请求成功"));
//    }


}
