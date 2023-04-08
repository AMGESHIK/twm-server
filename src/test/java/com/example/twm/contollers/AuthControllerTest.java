package com.example.twm.contollers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registration() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "isad@yandex.ru");
        jsonObject.put("username", "Lolo");
        jsonObject.put("password", "qwerty123");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/registration")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }
}
