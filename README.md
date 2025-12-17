# 🚀 Automated CI/CD Pipeline for a Dockerized Node.js Application

This project showcases a **complete, real-world CI/CD pipeline** built using **GitHub Actions** and **Docker** for a Node.js (Express) application.  
Every code change is automatically **tested, built into a Docker image, and pushed to Docker Hub**, ensuring fast, reliable, and consistent deployments.

---

## 📌 Project Overview
In modern software development, manual testing and deployment slow down delivery and introduce errors.  
This project demonstrates how **CI/CD automation** can eliminate these issues by integrating version control, automated testing, and containerization into a single workflow.

---

## ❗ Problem Statement
Traditional deployment approaches suffer from:
- Manual and error-prone testing
- Inconsistent environments across systems
- Delayed releases
- Lack of automated verification

These challenges make applications harder to scale and maintain.

---

## 💡 Solution
An automated CI/CD pipeline was implemented with the following features:

- **Git & GitHub** for version control
- **GitHub Actions** to automatically run tests on every push
- **Docker** to package the application into a portable container
- **Docker Hub** to store and distribute verified images
- Optional automated deployment via SSH

Only **tested and verified code** is built and deployed.

---

## ⚙️ Application Details
### Endpoints
- **GET /**  
  ```json
  { "message": "Hello, DevOps! CI/CD Pipeline Working Successfully" }
  ```

- **GET /health**  
  ```json
  {
    "status": "UP",
    "uptime": 32.5,
    "timestamp": "2025-12-17T20:24:08.980Z"
  }
  ```

- Source code: `src/`  
- Test cases: `__tests__/`

---

## 🛠️ Tech Stack
- **Git** – Version control  
- **GitHub** – Remote repository  
- **GitHub Actions** – CI/CD automation  
- **Node.js & Express** – Backend application  
- **Jest & Supertest** – Testing  
- **Docker** – Containerization  
- **Docker Hub** – Image registry  

---

## ▶️ Run Locally (Without Docker)
```bash
npm install
npm test
npm start
```
Access the app at:
```
http://localhost:3000
```

---

## 🐳 Run Using Docker
```bash
docker build -t ci-cd-pipeline .
docker run -d -p 3000:3000 --name ci-cd-pipeline ci-cd-pipeline
```

### Verify
```bash
curl http://localhost:3000
curl http://localhost:3000/health
```

---

## 🔄 CI/CD Pipeline (GitHub Actions)
Workflow file:
```
.github/workflows/ci.yml
```

### Pipeline Flow
```
Code Push → Automated Tests → Docker Build → Docker Hub → Deployment
```

### Required GitHub Secrets
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

### Docker Image
```bash
docker pull whydeewakar/ci-cd-pipeline:latest
```

---

## 🧱 Architecture (High-Level)
```
Developer Push
      |
GitHub Actions (Test → Build → Push)
      |
Docker Hub (Image Registry)
      |
Docker Container Deployment
```

---

## 🔗 GitHub Repository
👉 **Source Code:**  
https://github.com/deewakar05/Automated-CI-CD

---

## 💬 Feedback & Engagement
Any feedback, suggestions, and engagement are **highly appreciated** and help improve the project further. Feel free to open an issue or reach out.

---

## ✅ Conclusion
This project demonstrates a **production-style CI/CD workflow** following DevOps best practices.  
By automating testing, building, and containerization, it ensures faster releases, improved reliability, and consistent deployments across environments.

---

## 👤 Author
**Deewakar Kumar**  
Automated CI/CD Project
