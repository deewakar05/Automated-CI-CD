package com.devops.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Standard Servlet Filter that logs incoming requests to logs/java-access.log
 * and prints them to stdout. Satisfies bind mount logs sync requirements.
 */
@Component
public class LoggingFilter implements Filter {

    private final File logFile;

    public LoggingFilter() {
        // Automatically check/create the logs directory in the application root
        File logsDir = new File("../logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        this.logFile = new File(logsDir, "java-access.log");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No special initialization required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            
            // Format log parameters
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
            String method = httpRequest.getMethod();
            String uri = httpRequest.getRequestURI();
            String remoteAddr = httpRequest.getRemoteAddr();
            String userAgent = httpRequest.getHeader("User-Agent");
            
            String logLine = String.format("[%s] %s %s - IP: %s - Agent: %s\n", 
                    timestamp, method, uri, remoteAddr, userAgent);
            
            // Output to console stdout for standard container logging (docker logs)
            System.out.print(logLine);
            
            // Append log line to file logs/java-access.log
            try (FileWriter fw = new FileWriter(logFile, true)) {
                fw.write(logLine);
            } catch (IOException e) {
                System.err.println("Failed to write log to java-access.log: " + e.getMessage());
            }
        }
        
        // Pass filter chain down to process actual request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup resources if necessary
    }
}
