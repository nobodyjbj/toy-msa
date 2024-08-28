package org.toy.catalogservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerTest {
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
                get("/catalog-service/health-check")
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllCatalogs() throws Exception {
        mockMvc.perform(
                get("/catalog-service/catalogs")
                        .contentType(contentType)
                        .accept(contentType)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}