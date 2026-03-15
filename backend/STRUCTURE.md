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
   |  |- web/                 # REST controllers
   |  |- application/         # Use-case/application services
   |  `- dto/                 # Request/response DTOs
   |- event/
   |- minutes/
   |- auth/
   `- user/
```

## Layering Rules

1. `modules/*/web` depends on `modules/*/application` and `shared/*` only.
2. `modules/*/application` can depend on `infrastructure/*` abstractions/adapters.
3. `shared/*` never depends on `modules/*`.
4. `infrastructure/*` never depends on specific module web controllers.

## Migration Status

- Completed:
  - App bootstrap moved to `bootstrap`
  - Cross-cutting packages moved to `shared`
  - Runtime configs moved to `infrastructure/config`
  - Analytics module migrated to `modules/analytics`
-  - Event module migrated to `modules/event`
-  - Minutes module migrated to `modules/minutes`
- Pending (next step):
  - Move `infrastructure/google/api` endpoint into `modules/integration` web layer
