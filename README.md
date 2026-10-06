# 📦 Courier and Parcel Tracking System

An enterprise event-driven microservices platform built with **Spring Boot 3**, **RabbitMQ**, **Event Sourcing**, and **Docker**.

---

## 🏛️ Architecture Overview

The system models the courier logistics domain where **every physical movement is recorded as an immutable domain event** and the current parcel state is **reconstructed dynamically through Event Sourcing replay**.

```
                           ┌────────────────────────┐
                           │   Client / Postman     │
                           └───────────┬────────────┘
                                       │
                           ┌───────────▼────────────┐
                           │   API Gateway Service  │ (:8080)
                           └───────────┬────────────┘
                                       │
     ┌──────────────────┬──────────────┴───────┬──────────────────┐
     │                  │                      │                  │
┌────▼──────────┐ ┌─────▼──────────┐    ┌──────▼─────────┐ ┌──────▼─────────┐
│CustomerService│ │ShipmentService │    │HubDelivery Svc │ │Tracking Service│
│    (:8081)    │ │    (:8082)     │    │    (:8083)     │ │    (:8084)     │
└───────────────┘ └───────┬────────┘    └──────┬─────────┘ └──────▲─────────┘
                          │                    │                  │
                          │ Emits Events       │ Emits Events     │ Consumes & Appends
                          └──────────┬─────────┘                  │ to Event Store
                                     │                            │
                         ┌───────────▼────────────────────────────┴─┐
                         │      RabbitMQ Topic Exchange (AMQP)      │
                         │    (Retry, DLQ & Idempotent Handling)    │
                         └───────────┬──────────────────────────────┘
                                     │ Consumes & Dispatches
                         ┌───────────▼────────────┐
                         │  Notification Service  │
                         │        (:8085)         │
                         └────────────────────────┘
```

---

## 🛠️ Microservices & Port Mapping

| Service | Port | Database | Primary Responsibility |
| :--- | :--- | :--- | :--- |
| **api-gateway** | `8080` | None | Unified routing & gateway filters |
| **customer-service** | `8081` | `customer_db` | Customer registrations & profiles |
| **shipment-service** | `8082` | `shipment_db` | Order creation & tracking ID generation |
| **hub-delivery-service** | `8083` | `hub_db` | Hub scans, dispatching & delivery completion |
| **tracking-service** | `8084` | `tracking_eventstore_db` | **Event Sourcing engine** & state reconstruction |
| **notification-service**| `8085` | In-memory / Logs | Async SMS/Email alert simulations |

---

## 🚀 How to Run

### Option 1: Docker Compose (All-in-one)
```bash
# Build all JAR files
mvn clean package -DskipTests

# Start all databases, RabbitMQ, and microservices
docker-compose up --build
```
* **RabbitMQ Dashboard:** `http://localhost:15672` (Username: `guest` / Password: `guest`)
* **API Gateway Base URL:** `http://localhost:8080`

### Option 2: Local Development (H2 in-memory databases)
Each service includes embedded H2 fallback databases so team members can develop and run microservices individually without installing external DBs.

---

## 🧪 Testing with Postman
Import the provided Postman collection:
* [`Courier_Parcel_Tracking_Postman_Collection.json`](file:///d:/projects/scalable-miniproject/Courier_Parcel_Tracking_Postman_Collection.json)
This tests the full end-to-end lifecycle:
1. `POST /api/v1/customers`
2. `POST /api/v1/shipments` (emits `ShipmentCreated`)
3. `POST /api/v1/deliveries/collect` (emits `ParcelCollected`)
4. `POST /api/v1/hubs/scan-in` (emits `ReachedHub`)
5. `POST /api/v1/hubs/scan-out` (emits `LeftHub`)
6. `POST /api/v1/deliveries/out-for-delivery` (emits `OutForDelivery`)
7. `POST /api/v1/deliveries/complete` (emits `Delivered`)
8. `GET /api/v1/tracking/{trackingId}` (reconstructs history via **Event Sourcing**)
