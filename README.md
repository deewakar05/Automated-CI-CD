# Full Stack DevOps Infrastructure: Node.js & Java Spring Boot with Docker, Docker Compose & GitHub Actions

## 🌟 Project Overview
This repository presents a complete, production-ready DevOps demonstration showcasing a **lightweight dual-service infrastructure**. It integrates **Node.js Express** (running on the host root) and a **Java Spring Boot microservice** (built via Maven in a subdirectory). Both services are coordinated seamlessly via **Docker Compose**, utilizing custom bridge networks, named bind mounts, container healthchecks, and environment variables. The entire pipeline is fully automated using **GitHub Actions CI/CD workflows** with package caching and secure Docker Hub deployment.

This project is tailored specifically to align with university **DevOps Infrastructure and Containerization** syllabus guidelines, making it the perfect submission-ready and viva-ready project.

---

## 📐 Infrastructure & Architecture

Below is the architectural blueprint of our containerized topology. The services communicate securely inside an isolated virtual bridge network, mapping specific ports to the host machine for external access.

```
                  +----------------------------------------------+
                  |                 HOST MACHINE                 |
                  |                                              |
                  |     +----------------------------------+     |
                  |     |       Web Client / Browser       |     |
                  |     +---------+--------------+---------+     |
                  |               |              |               |
                  |      Port 3000|              |Port 8080      |
                  |               v              v               |
                  |     +---------+---+      +---+---------+     |
                  |     |  Node.js    |      |  Java       |     |
                  |     |  Express    |      |  Spring Boot|     |
                  |     |  Backend    |      |  Service    |     |
                  |     |  (Port 3000)|      |  (Port 8080)|     |
                  |     +------+------+      +------+------+     |
                  |            |                    |            |
                  |            |                    |            |
                  |            +---------+----------+            |
                  |                      |                       |
                  |                      v                       |
                  |            [ devops-network ]                |
                  |         (Isolated Bridge Network)            |
                  |                      +                       |
                  |                      |                       |
                  |                      | Bind Mount            |
                  |                      v                       |
                  |       +--------------+--------------+        |
                  |       |       ./logs/ directory     |        |
                  |       | - node-access.log           |        |
                  |       | - java-access.log           |        |
                  |       +-----------------------------+        |
                  +----------------------------------------------+
```

---

## 📕 Core DevOps & Docker Concepts (Syllabus Guide)

Here are simple, beginner-friendly explanations of the DevOps concepts showcased in this project, perfect for answering questions during a viva or presentation:

### 1. Introduction to Containers
A **container** is a lightweight, standalone, and executable package of software that includes everything needed to run an application: code, runtime, system tools, system libraries, and settings. Unlike Virtual Machines (VMs), which virtualize the physical hardware and require a full guest OS, containers virtualize the host Operating System kernel. This makes them extremely lightweight, starting up in milliseconds and using minimal RAM.

### 2. Docker Architecture
Docker operates on a **client-server architecture**:
*   **Docker Daemon (`dockerd`)**: A background service running on the host machine that manages Docker objects like images, containers, networks, and volumes.
*   **Docker CLI (`docker`)**: The command-line interface utility that allows developers to interact with the Docker Daemon via REST API commands (e.g. `docker build`, `docker run`).
*   **Registry**: A SaaS catalog that stores and distributes container images (e.g., public Docker Hub, GitHub Container Registry).

### 3. Container Images & Layer Optimization
A **Docker Image** is a read-only template with instructions for creating a Docker container. Images are constructed as a series of **read-only layers**. 
Each command in a Dockerfile (like `RUN`, `COPY`, `ADD`) creates a new layer.
*   **Layer Caching**: When rebuilding an image, Docker checks if the instruction and the files copied have changed. If not, it reuses the cached layer, saving enormous build time.
*   **Optimization**: By copying dependency files (like `package.json` or `pom.xml`) and installing packages *before* copying the rest of the application source code, we optimize caching. The heavy packages are cached and only re-installed when the dependency lists themselves are edited.

### 4. Storage: Volumes vs. Bind Mounts
*   **Volumes**: Managed completely by Docker in an isolated directory on the host system. They are ideal for persistent storage (like databases) because Docker handles backup, migration, and encryption.
*   **Bind Mounts**: Maps a specific file or directory on the host machine directly to a path inside the container (e.g. `./logs:/app/logs`). This is highly useful for sharing files between the host and containers in real-time, such as synchronizing application logs or code during local development.

### 5. Docker Networking: Bridge Network & Container DNS
Docker Compose creates an isolated **Bridge Network** (`devops-network`) for our services by default.
*   **Bridge Network**: A software-defined network that connects multiple containers running on the same host daemon, isolating them from external networks while providing a gateway.
*   **Container DNS**: Inside the `devops-network`, containers can talk to each other directly using their service names (like `http://node-backend:3000` or `http://java-service:8080`) instead of tracking volatile container IP addresses. Docker's internal DNS server handles this resolution automatically.

### 6. Build Context & `.dockerignore`
The **Build Context** is the set of files located at the path specified in your `docker build` command. These files are compressed and sent to the Docker daemon.
*   Using a `.dockerignore` file prevents bulky directories (like `node_modules` or local `target/` files) or sensitive files (like `.env`) from being sent to the daemon. This makes builds extremely fast and keeps the final image secure.

---

## 🛠️ Microservice Details

### 🟢 Node.js Express Application (Root)
The primary entry point represents an event-driven Express.js server:
*   **Entrypoint**: `src/server.js` starts the listener.
*   **Routing Logic**: `src/app.js` maps endpoints and provisions the logging middleware.
*   **Endpoints**:
    *   `GET /`: Returns a success message proving the pipeline status.
    *   `GET /health`: Used by Docker Compose to run routine health status checks.
    *   `GET /api/users`: Returns a mock user JSON catalog.
*   **Logging**: A custom request logging middleware intercepts requests, printing metadata to console `stdout` and appending access records inside `./logs/node-access.log`.

### 🔵 Java Spring Boot Service (`java-service/`)
A Java-based class-compiled microservice using Maven:
*   **Entrypoint**: `com.devops.api.JavaApplication.java`.
*   **Build Lifecycle (Maven)**: The `pom.xml` configuration handles building, package compilation (`clean package`), and JUnit testing.
*   **Endpoints**:
    *   `GET /java/health`: Returns a JSON status object.
    *   `GET /api/products`: Returns a mock catalog list of products.
*   **Logging**: Implements a servlet `LoggingFilter` which intercepts all requests, printing metadata to standard output and writing access entries to `./logs/java-access.log`.

---

## 🗂️ Project Directory Structure

```
.
├── .github/
│   └── workflows/
│       ├── ci.yml                 # Node.js Jest testing, tagging & Docker Hub publishing
│       └── maven-ci.yml           # Java Maven building & JUnit testing
├── java-service/
│   ├── .mvn/                      # Maven wrapper properties config
│   ├── src/
│   │   ├── main/java/com/devops/api/
│   │   │   ├── JavaApplication.java     # Spring Boot Launcher
│   │   │   ├── controller/
│   │   │   │   └── ApiController.java   # Spring REST Endpoints
│   │   │   └── filter/
│   │   │       └── LoggingFilter.java   # Request Bind-Mount logging
│   │   └── test/java/com/devops/api/
│   │       └── JavaApplicationTests.java # JUnit Endpoint Tests
│   ├── Dockerfile                 # Java Multi-Stage compile/runtime build
│   ├── .dockerignore              # Java context exclusions
│   ├── mvnw                       # Linux Maven wrapper script
│   ├── mvnw.cmd                   # Windows Maven wrapper script
│   └── pom.xml                    # Maven dependency management
├── src/
│   ├── app.js                     # Express setup & node logging middleware
│   └── server.js                  # Express listener bootstrap
├── __tests__/
│   ├── app.test.js                # Jest API tests (GET / and GET /api/users)
│   └── health.test.js             # Jest health status tests
├── .dockerignore                  # Root Node context exclusions
├── .env.example                   # Environment variable template
├── Dockerfile                     # Optimized Node.js Docker image build
├── docker-compose.yml             # Dual-service coordination, networking & bind-mount
└── README.md                      # Complete Project Documentation
```

---

## 🖥️ Image Management & Command Examples

These commands represent key Docker procedures that you should memorize or refer to for live demonstrations:

```bash
# 1. Build a local Docker image using the Dockerfile in the current directory
docker build -t ci-cd-pipeline .

# 2. Tag your local image with a version prefix matching your Docker Hub repository
docker tag ci-cd-pipeline whydeewakar/ci-cd-pipeline:v1

# 3. Inspect image building history to view layers, sizing, and cache hits
docker history ci-cd-pipeline

# 4. Inspect detailed configuration metadata of the image (ports, envs, commands) in JSON format
docker inspect ci-cd-pipeline

# 5. Push the tagged container image to your Docker Hub Registry repository
docker push whydeewakar/ci-cd-pipeline:v1
```

---

## 🚀 Execution & Running Instructions

### 1. Running Locally (No Docker)

Ensure you have **Node.js (v18+)** and **Java (JDK 17)** installed locally.

#### Node.js Express App
```bash
# Install dependencies
npm install

# Run Jest tests
npm test

# Start the application
npm start
```
*   Node application will start on: `http://localhost:3000`

#### Java Spring Boot Service
```bash
cd java-service

# Compile and execute JUnit tests
./mvnw clean test

# Run the Spring Boot application
./mvnw spring-boot:run
```
*   Java application will start on: `http://localhost:8080`

---

### 2. Running with Docker (Isolated Containers)

```bash
# Build and run the Node container individually
docker build -t node-app-local .
docker run -d -p 3000:3000 --name node-container node-app-local

# Build and run the Java container individually
cd java-service
docker build -t java-app-local .
docker run -d -p 8080:8080 --name java-container java-app-local
```

---

### 3. Running with Docker Compose (Dual-Service Topology)

This is the recommended approach for testing the complete infrastructure setup. It compiles both services, sets up the bridge network, and maps logs in a single command.

```bash
# 1. Launch both services in detached (background) mode and force build compilation
docker-compose up --build -d

# 2. Inspect active services and their health status
docker-compose ps

# 3. View real-time container log outputs
docker-compose logs -f

# 4. Stop the services and remove containers/networks
docker-compose down
```

---

## 📈 Endpoint Verification Dashboard

Once running via **Docker Compose**, visit the following routes on your browser or via curl to verify that both microservices are functional:

| Service | Protocol / Route | Expected JSON Content / Action |
|---|---|---|
| **Node.js** | `http://localhost:3000/` | Main application status response |
| **Node.js** | `http://localhost:3000/health` | Container health check metrics |
| **Node.js** | `http://localhost:3000/api/users` | List of system administrators and users |
| **Java** | `http://localhost:8080/java/health` | Java service health status |
| **Java** | `http://localhost:8080/api/products` | Mock product catalog |

### 📂 Bind-Mount Log Validation
Once you make a few API requests to the endpoints, inspect your host project folder. You will find a new directory `./logs/` created automatically, containing:
*   `./logs/node-access.log`: Logs appended by the Node container.
*   `./logs/java-access.log`: Logs appended by the Java container.

Verify the sync by running:
```bash
cat logs/node-access.log
cat logs/java-access.log
```

---

## ⚙️ CI/CD Workflow (GitHub Actions)

The repository includes two separate pipeline workflows:
1.  **Node.js CI/CD Pipeline (`ci.yml`)**:
    *   **Triggers**: Commits pushed to `main`/`demo` or Pull Requests.
    *   **Jobs**: Automatically provisions runner, caches node packages (`~/.npm`), installs dependencies, runs Jest tests, logs in to Docker Hub, generates branch-based image tags, builds the image, and publishes it globally.
2.  **Java Maven CI Pipeline (`maven-ci.yml`)**:
    *   **Triggers**: Commits pushed to `main`/`demo` or Pull Requests.
    *   **Jobs**: Provisions Java JDK 17 runner, caches Maven dependency repository (`~/.m2`), compiles the code, and executes the JUnit tests via `mvnw`.

---

## 🎓 Learning Outcomes
By building and reviewing this project, you will master the following core DevOps competencies:
1.  **Microservices Polyglot Design**: Understanding how Node.js and Java Spring Boot handle backend endpoints differently.
2.  **Containerization Best Practices**: Designing optimized multi-stage build scripts to drastically minimize container sizes.
3.  **Docker Orchestration**: Coordinating multi-container environments using bridge networks and DNS service names instead of static IPs.
4.  **Persistent Syncing**: Implementing named bind mounts to export real-time container log files onto the host machine.
5.  **CI/CD Automation**: Leveraging GitHub Actions with package caching to establish robust test-then-deploy loops.

---

## 🏁 Conclusion
This dual-service application represents a complete, production-grade microservice architecture. It demonstrates compilation pipelines, optimized container builds, local orchestration, and remote deployment in a clear, educational, and clean repository format. It is a highly professional showcase ready for academic submission!
