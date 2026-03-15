# Microservices Configuration Scaffold

This folder contains baseline runtime configuration for the target microservices split:
- api-gateway
- identity-service
- event-service
- minutes-service
- planning-service
- integration-service
- notification-service
- analytics-service

## Local Middleware Stack
Create env file first:

```bash
cp .env.microservices.example .env.microservices
```

Run middleware only:

```bash
docker compose -f docker-compose.microservices.yml up -d
```

Run middleware + service containers (when images exist):

```bash
docker compose -f docker-compose.microservices.yml --profile apps up -d
```

## Ports
- API Gateway: 8080
- Identity: 8081
- Event: 8082
- Minutes: 8083
- Planning: 8084
- Integration: 8086
- Notification: 8087
- Analytics: 8088
- Kafka UI: 8085
- Prometheus: 9090
- Grafana: 3000
- Jaeger: 16686

## Notes
- These files are config scaffolds and can be copied into each service repo when extracted.
- Keep secrets in environment variables, not committed files.
- Route all frontend traffic through API Gateway when microservices mode is enabled.
- Start order: middleware first, then API gateway, then domain services, then frontend.
