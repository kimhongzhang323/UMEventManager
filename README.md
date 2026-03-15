# UM Event Management Platform

![Backend CI](https://github.com/kimhongzhang323/UMEventManager/actions/workflows/backend-ci.yml/badge.svg)
![Frontend CI](https://github.com/kimhongzhang323/UMEventManager/actions/workflows/frontend-ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-18-DD0031?logo=angular&logoColor=white)

An industry-grade, full-stack web application designed to streamline event planning, task tracking, and performance analytics for student event teams and university chapters. 

## 🚀 Key Features

*   **🧠 AI Task Tracking from Minutes:** Automatically analyzes raw meeting notes to extract event names, departments, meeting dates, and actionable items.
*   **📚 RAG-style Historical Memory:** Evaluates past event patterns and acts as an "annual memory bank" to generate context-aware, month-by-month future event plans.
*   **🔗 Google Workspace Integration:** Infrastructure set up for Google Drive and Gmail data retrieval.
*   **📊 Performance Analytics:** Tracks and evaluates member performance, department metrics, and holistic organizational rankings.

---

## 🛠 Architecture & Tech Stack

This project is orchestrated as a containerized **Modular Monolith**.

*   **Frontend:** Angular 18 (Standalone components)
*   **Backend:** Java 21 + Spring Boot 3
*   **Database:** PostgreSQL 15
*   **Cache & Messaging:** Redis 7 + Apache Kafka (Confluent 7.4)
*   **Containerization:** Docker & Docker Compose
*   **CI/CD:** GitHub Actions

---

## 🏗 Getting Started

### Prerequisites
*   Node.js 20+
*   JDK 17 or 21
*   Docker & Docker Compose

### 1. Bootstrapping via Docker (Recommended)
You can spin up the entire pre-configured ecosystem (Postgres, Redis, Kafka, Zookeeper) using Docker Compose.

```bash
# Clone the repository
git clone https://github.com/kimhongzhang323/UMEventManager.git
cd UMEventManager

# Start the required infrastructure (Database, Cache, Message Broker)
docker-compose up -d postgres redis zookeeper kafka

# To run the ENTIRE stack including frontend & backend containers:
# docker-compose up -d
```

### 2. Local Development

**Backend:**
```bash
cd backend
mvn spring-boot:run
```
*(Runs on `http://localhost:8080`)*

**Frontend:**
```bash
cd frontend
npm install
npm start
```
*(Runs on `http://localhost:4200`)*

---

## 📁 Project Structure

The project strictly follows a Domain-Driven modular architecture.

```text
UMEventManagement/
├── backend/                  # Spring Boot System
│   └── src/main/java/.../modules/ # Bounded Contexts (analytics, auth, event, integration, minutes, user)
├── frontend/                 # Angular SPA
├── docker-compose.yml        # Infrastructure Orchestration
├── .github/workflows/        # CI/CD Pipelines
└── docs/                     # Project specs and ADRs
```
*For detailed backend enterprise structure, see `backend/STRUCTURE.md`.*

---

## 🔌 Core API Endpoints

| Domain | Endpoint | Method | Description |
|---|---|---|---|
| **AI Minutes** | `/api/ai/minutes/analyze` | `POST` | Extract tasks/details from meeting text |
| **Event RAG** | `/api/events/rag/plan` | `POST` | Generate event plan from past patterns |
| **Google** | `/api/google/drive-items` | `GET` | Retrieve structured Drive inventory |
| **Analytics** | `/v1/analytics/departments/ranking` | `GET` | Fetch organizational KPIs & rankings |

---

## 🔮 Roadmap to Production

- [ ] **Real LLM Integration:** Swap heuristic parser out for Gemini/GPT via `GeminiClientTemplate`.
- [ ] **Vector Search Setup:** Implement `pgvector` or similar to support semantic chunk matching for RAG plans.
- [ ] **OAuth2 Consent Flows:** Tie Google Integration API strictly to authenticated identity tokens rather than dummy data.
- [ ] **Database Repositories:** Wire active JPA/Hibernate repositories to the new modular packages (`modules/*/repo`).

---

## 🤝 Contributing & Security
*   Read our [Contributing Guidelines](CONTRIBUTING.md) to understand the local workflow.
*   Please check our [Security Policy](SECURITY.md) before reporting vulnerabilities.
*   Licensed under the [MIT License](LICENSE).
