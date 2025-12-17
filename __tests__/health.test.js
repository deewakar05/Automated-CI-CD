const request = require('supertest');
const app = require('../src/app');

describe('GET /health', () => {
  it('returns application health status', async () => {
    const response = await request(app).get('/health');

    expect(response.status).toBe(200);
    expect(response.headers['content-type']).toMatch(/json/);

    expect(response.body).toHaveProperty('status', 'UP');
    expect(response.body).toHaveProperty('uptime');
    expect(response.body).toHaveProperty('timestamp');
  });
});
