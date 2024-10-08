package org.toy.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.toy.userservice.vo.RequestUser;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8
    );

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(
                get("/health_check")
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void welcome() throws Exception {
        mockMvc.perform(
                get("/welcome")
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createUser() throws Exception {
        RequestUser user = new RequestUser();
        user.setEmail("test1@toy.com");
        user.setName("test1");
        user.setPassword("test1234");

        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}