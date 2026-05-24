package com.devops.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Java Spring Boot microservice.
 * Bootstraps the application context and starts the embedded Tomcat server on port 8888 or 8080.
 */
@SpringBootApplication
public class JavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }
}
