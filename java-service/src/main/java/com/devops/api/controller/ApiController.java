package com.devops.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

/**
 * Spring Boot REST Controller defining the endpoints for the DevOps syllabus Java microservice.
 */
@RestController
public class ApiController {

    /**
     * Endpoint: GET /java/health
     * Returns the service status as a JSON object.
     */
    @GetMapping("/java/health")
    public HealthResponse getHealth() {
        return new HealthResponse("UP", "java-service");
    }

    /**
     * Endpoint: GET /api/products
     * Returns a mockup catalog list of products.
     */
    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return Arrays.asList(
            new Product(1, "Docker Handbook"),
            new Product(2, "Maven Essentials")
        );
    }

    // Helper class representing health check JSON response structure
    public static class HealthResponse {
        private String status;
        private String service;

        public HealthResponse(String status, String service) {
            this.status = status;
            this.service = service;
        }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getService() { return service; }
        public void setService(String service) { this.service = service; }
    }

    // Helper class representing product JSON catalog items
    public static class Product {
        private int id;
        private String name;

        public Product(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
