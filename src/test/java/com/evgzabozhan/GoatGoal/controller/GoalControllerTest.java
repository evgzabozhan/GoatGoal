package com.evgzabozhan.GoatGoal.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(GoalController.class)
class GoalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void goal() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/goal");
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        assertEquals("",result.getResponse().getContentAsString());
    }

}