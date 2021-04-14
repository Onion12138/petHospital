package com.ecnu.six.pethospital.guide.controller;


import com.ecnu.six.pethospital.guide.service.impl.GuideServiceImpl;
import com.ecnu.six.pethospital.guide.vo.DepartmentResponse;
import com.ecnu.six.pethospital.guide.vo.EquipmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/4/12 10:55
 */

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class GuideControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuideServiceImpl guideService;

    @Test
    @DisplayName("正确返回各科室信息")
    void shouldGetAllDepartments() throws Exception {
        EquipmentResponse equipmentResponse1 = EquipmentResponse.builder()
                .name("设备1")
                .message("信息1")
                .position(new Integer[]{1, 2})
                .picture("www.pic1.link")
                .build();
        EquipmentResponse equipmentResponse2 = EquipmentResponse.builder()
                .name("设备2")
                .message("信息2")
                .position(new Integer[]{2, 3})
                .picture("www.pic2.link")
                .build();
        DepartmentResponse response1 = DepartmentResponse.builder()
                .id(1)
                .name("科室1")
                .message("信息3")
                .picture("www.pic3.link")
                .roleName("医师")
                .position(new Integer[]{1, 2, 3, 4})
                .equipments(new ArrayList<>(Arrays.asList(equipmentResponse1)))
                .build();
        DepartmentResponse response2 = DepartmentResponse.builder()
                .id(2)
                .name("科室2")
                .message("信息4")
                .picture("www.pic4.link")
                .roleName("医师")
                .position(new Integer[]{1, 2, 3, 6})
                .equipments(new ArrayList<>(Arrays.asList(equipmentResponse2)))
                .build();
        List<DepartmentResponse> responses = new ArrayList<>(Arrays.asList(response1, response2));
        when(guideService.getDepartments()).thenReturn(responses);
        ResultActions perform = mockMvc.perform(
                post("/guide/getDepartments")
        );
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("科室1"))
                .andExpect(jsonPath("$.data[0].message").value("信息3"))
                .andExpect(jsonPath("$.data[0].picture").value("www.pic3.link"))
                .andExpect(jsonPath("$.data[0].roleName").value("医师"))
                .andExpect(jsonPath("$.data[0].position[0]").value(1))
                .andExpect(jsonPath("$.data[0].position[1]").value(2))
                .andExpect(jsonPath("$.data[0].position[2]").value(3))
                .andExpect(jsonPath("$.data[0].position[3]").value(4))
                .andExpect(jsonPath("$.data[0].equipments[0].name").value("设备1"))
                .andExpect(jsonPath("$.data[0].equipments[0].message").value("信息1"))
                .andExpect(jsonPath("$.data[0].equipments[0].position[0]").value(1))
                .andExpect(jsonPath("$.data[0].equipments[0].position[1]").value(2))
                .andExpect(jsonPath("$.data[0].equipments[0].picture").value("www.pic1.link"))

                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("科室2"))
                .andExpect(jsonPath("$.data[1].message").value("信息4"))
                .andExpect(jsonPath("$.data[1].picture").value("www.pic4.link"))
                .andExpect(jsonPath("$.data[1].roleName").value("医师"))
                .andExpect(jsonPath("$.data[1].position[0]").value(1))
                .andExpect(jsonPath("$.data[1].position[1]").value(2))
                .andExpect(jsonPath("$.data[1].position[2]").value(3))
                .andExpect(jsonPath("$.data[1].position[3]").value(6))
                .andExpect(jsonPath("$.data[1].equipments[0].name").value("设备2"))
                .andExpect(jsonPath("$.data[1].equipments[0].message").value("信息2"))
                .andExpect(jsonPath("$.data[1].equipments[0].position[0]").value(2))
                .andExpect(jsonPath("$.data[1].equipments[0].position[1]").value(3))
                .andExpect(jsonPath("$.data[1].equipments[0].picture").value("www.pic2.link"));
    }
}
