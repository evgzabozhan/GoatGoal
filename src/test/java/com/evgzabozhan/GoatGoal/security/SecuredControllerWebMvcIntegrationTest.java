package com.evgzabozhan.GoatGoal.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecuredControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser
    @Test
    public void givenManagerUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/home")
        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user")));
    }
}
