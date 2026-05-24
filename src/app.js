const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();

// Ensure logs directory exists automatically
const logsDir = path.join(__dirname, '../logs');
if (!fs.existsSync(logsDir)) {
  fs.mkdirSync(logsDir, { recursive: true });
}

// Request logging middleware to write access logs to logs/node-access.log
app.use((req, res, next) => {
  const timestamp = new Date().toISOString();
  const logMessage = `[${timestamp}] ${req.method} ${req.url} - IP: ${req.ip} - Agent: ${req.headers['user-agent']}\n`;
  
  // Also print to console for stdout/docker logging
  console.log(logMessage.trim());

  // Append access log to node-access.log
  fs.appendFile(path.join(logsDir, 'node-access.log'), logMessage, (err) => {
    if (err) {
      console.error('Error writing to node-access.log:', err);
    }
  });
  
  next();
});

app.get('/', (_req, res) => {
  res.json({ message: 'I am making change to show ci/cd working' });
});

/**
 * Health check endpoint
 * Used by CI/CD, Docker, load balancers, and monitoring tools
 */
app.get('/health', (_req, res) => {
  res.status(200).json({
    status: 'UP',
    uptime: process.uptime(),
    timestamp: new Date().toISOString(),
  });
});

/**
 * Get sample users endpoint (new DevOps route)
 */
app.get('/api/users', (_req, res) => {
  res.status(200).json([
    {
      "id": 1,
      "name": "DevOps Admin"
    },
    {
      "id": 2,
      "name": "Docker Service User"
    }
  ]);
});

module.exports = app;
