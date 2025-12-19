const request = require('supertest');
const app = require('../src/app');

describe('GET /', () => {
  it('returns success message', async () => {
    const response = await request(app).get('/');

    expect(response.status).toBe(200);
    expect(response.headers['content-type']).toMatch(/json/);
    expect(response.body).toEqual({
      message: 'I am making change to show ci/cd working',
    });
  });
});
