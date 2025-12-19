const express = require('express');

const app = express();

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



module.exports = app;
