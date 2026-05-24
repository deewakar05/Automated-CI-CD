package com.devops.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Standard JUnit Integration/Unit test suite for the Spring Boot application.
 * Utilizes MockMvc to perform request assertion without spinning up a live network socket.
 */
@SpringBootTest
@AutoConfigureMockMvc
class JavaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies that the Spring Boot Application Context loads successfully.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Tests the GET /java/health endpoint.
     * Asserts status is 200 OK and returns correct service name and status.
     */
    @Test
    void testJavaHealthEndpoint() throws Exception {
        mockMvc.perform(get("/java/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("java-service"));
    }

    /**
     * Tests the GET /api/products endpoint.
     * Asserts status is 200 OK and returns a JSON list of products with the exact mock elements.
     */
    @Test
    void testGetProductsEndpoint() throws Exception {
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Docker Handbook"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Maven Essentials"));
    }
}
