package com.example.twm.contollers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DiaryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void programs() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/diary")
        ).andExpect(status().is4xxClientError());
    }
    @Test
    public void exercises() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/diary/exercises")
        ).andExpect(status().is4xxClientError());
    }
    @Test
    public void getProgram() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/diary/program")
        ).andExpect(status().is4xxClientError());
    }


}
