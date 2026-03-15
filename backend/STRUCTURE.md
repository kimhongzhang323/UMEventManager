# Backend Production Structure

## Package Layout

```text
com.umevent.management
|- bootstrap/                 # App entrypoint and startup wiring
|- shared/                    # Cross-cutting concerns shared by all modules
|  |- api/                    # Standard API response and error wrappers
|  |- exception/              # Global exception handlers
|  |- logging/                # Request/trace logging filters
|  `- util/                   # Shared utility factories
|- infrastructure/            # Technical integrations and framework config
|  |- config/                 # Spring configuration classes
|  |  `- properties/          # Typed config-properties bindings
|  |- google/                 # External provider integration (Google, Gemini)
|  `- kafka/                  # Messaging adapters (reserved)
`- modules/                   # Business modules (bounded contexts)
   |- analytics/
  |  |- controller/          # REST controllers
  |  |- service/             # Use-case/business services
  |  |- repo/                # Persistence contracts/adapters
   |  `- dto/                 # Request/response DTOs
   |- event/
   |- minutes/
   |- auth/
   `- user/
```

## Layering Rules

1. `modules/*/controller` depends on `modules/*/service` and `shared/*` only.
2. `modules/*/service` can depend on `modules/*/repo` and `infrastructure/*` abstractions/adapters.
3. `shared/*` never depends on `modules/*`.
4. `infrastructure/*` never depends on specific module web controllers.

## Migration Status

- Completed:
  - App bootstrap moved to `bootstrap`
  - Cross-cutting packages moved to `shared`
  - Runtime configs moved to `infrastructure/config`
  - Analytics module migrated to `modules/analytics` (`controller/service/repo/dto`)
  - Event module migrated to `modules/event` (`controller/service/repo/dto`)
  - Minutes module migrated to `modules/minutes` (`controller/service/repo/dto`)
- Pending (next step):
  - Move `infrastructure/google/api` endpoint into `modules/integration/controller` layer
