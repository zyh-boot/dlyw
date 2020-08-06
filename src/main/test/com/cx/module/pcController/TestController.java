package com.cx.module.pcController;

import com.cx.AppApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
@AutoConfigureMockMvc
public class TestController {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PcAccountController pcAccountController;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(pcAccountController).build();
    }

    @Test
    public void test() {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("mobile/equipment/pageList").param("name", "tom"))
                    .andDo(MockMvcResultHandlers.print()).andReturn();
            int status = mvcResult.getResponse().getStatus();
            String content = mvcResult.getResponse().getContentAsString();
            System.out.println(status);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
