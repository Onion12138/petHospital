package com.ecnu.six.pethospital.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.oauth.entity.LocalUserExample;
import com.ecnu.six.pethospital.oauth.mapper.LocalUserMapper;
import com.ecnu.six.pethospital.oauth.service.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UsrInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OauthService oauthService;

    @Mock
    private LocalUserMapper userMapper;


    @Test
    @DisplayName("加载所有用户")
    public void loadAllUsrs() throws Exception {
        ResultActions perform = mockMvc.perform(
                get("/usr/info/loadByStuId")
        );

        when(userMapper.selectByExample(any())).thenReturn(new ArrayList<>());

        LocalUserExample example = new LocalUserExample();
        example.createCriteria().andIdGreaterThan(0);


        verify(userMapper, times(1))
                .selectByExample(example);


        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("请求成功"));
    }
}
