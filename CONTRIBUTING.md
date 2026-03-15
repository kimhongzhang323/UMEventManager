# Contributing to UMEventManagement

Welcome! We are excited to have you contribute to our platform.

## Getting Started Locally

1. **Fork and clone validation:**
   Ensure you have cloned the repository and have Docker and Docker Compose installed.

2. **Bootstrapping the environment:**
   Use the provided Docker Compose configuration to start all required servers (Kafka, Redis, Postgres).
   ```bash
   docker-compose up -d
   ```
   *Note: This will spin up the `backend` and `frontend` in containers. To run them locally on your machine for development (with live reload), you can just spin up the infrastructure:*
   ```bash
   docker-compose up -d postgres redis zookeeper kafka
   ```

3. **Running the Backend:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

4. **Running the Frontend:**
   ```bash
   cd frontend
   npm install
   npm start
   ```

## Creating a Pull Request
1. Branch from `main`: `git checkout -b feature/your-feature-name`
2. Ensure your code follows the structure outlined in `backend/STRUCTURE.md`.
3. Add tests if applicable.
4. Push your branch and open a PR using our Pull Request template.

## Architectural Notes
Please refer to the `backend/STRUCTURE.md` for backend API conventions (Bound Contexts: controller, service, repo, dto).
