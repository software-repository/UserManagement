package com.jas.unittests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jas.entity.Department;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createDepartmentTest_success() throws Exception {

        Department department = Department.builder()
                .departmentId(5)
                .departmentName("Fitness")
                .build();
        String jsonRequest = om.writeValueAsString(department);
        MvcResult result = mockMvc.perform(post("/api/v1/department/create").content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void getDepartmentTest_success() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/department/all")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
    }

}
