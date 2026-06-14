package com.mrtkyr.classqroom.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrtkyr.classqroom.jwt.AuthRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(classes = { CqrBackendApplication.class })
class CqrBackendApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReturn403WhenAccessingProtectedResourceWithoutToken() throws Exception {
        mockMvc.perform(get("/rest/api/faculty/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnTokenWhenLoginWithValidCredentials() throws Exception {
        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setEmail("test@classqroom.edu");
        loginRequest.setPassword("Test1234");
        
        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void shouldReturn400WhenRegisteringWithEmptyFields() throws Exception {
        String emptyRegisterRequest = "{}";
        
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyRegisterRequest))
                .andExpect(status().isBadRequest());
    }
}
