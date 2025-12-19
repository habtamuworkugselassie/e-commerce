# E-Commerce Microservices Analysis

## Overview
This repository contains two Spring Boot microservices that form part of an e-commerce platform:
1. **Loan Service** - Handles invoice-based loan applications and management
2. **Order Service** - Manages cart pricing, promotions, and order confirmation

---

## 1. Loan Service (`backend/loan-service`)

### Purpose
Manages invoice-based loan applications, approvals, EMI calculations, and repayment schedules.

### Technology Stack
- **Spring Boot**: 4.0.2-SNAPSHOT
- **Java**: 21
- **Database**: H2 (in-memory)
- **Port**: 8081
- **Key Dependencies**: Spring Data JPA, Spring Web, Spring Validation, Actuator

### Core Functionality

#### 1.1 Loan Application
- **Endpoint**: `POST /api/loans`
- **Request**: `LoanApplicationRequest` containing:
  - `customerId` (UUID)
  - `invoiceId` (UUID)
  - `loanAmount` (BigDecimal)
  - `invoiceAmount` (BigDecimal)
  - `interestRate` (BigDecimal - annual %)
  - `tenureInMonths` (Integer)

- **Business Rules**:
  - Loan amount cannot exceed 80% of invoice amount (invoice-based financing rule)
  - Validates all required fields
  - Creates loan with status `PENDING`

- **Response**: `LoanApplicationResponse` with loan ID, status, and success message

#### 1.2 Loan Approval
- **Endpoint**: `POST /api/loans/{loanId}/approve`
- **Functionality**:
  - Calculates EMI using standard amortization formula
  - Generates complete repayment schedule (monthly installments)
  - Updates loan status to `APPROVED`
  - Sets approval date

#### 1.3 EMI Calculation
- **Component**: `EmiCalculator`
- **Formula**: Standard amortization formula
  ```
  EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)
  ```
  Where:
  - P = Principal amount
  - r = Monthly interest rate (annual rate / 12 / 100)
  - n = Number of months

#### 1.4 Repayment Schedule Generation
- **Component**: `RepaymentScheduleGenerator`
- **Functionality**:
  - Creates monthly installment schedule
  - Calculates principal and interest components for each installment
  - Tracks outstanding balance
  - Sets due dates (monthly intervals from approval date)

### Data Models

#### Loan Entity
- `id` (UUID)
- `customerId` (UUID)
- `invoiceId` (UUID)
- `invoiceAmount` (BigDecimal)
- `loanAmount` (BigDecimal)
- `interestRate` (BigDecimal - annual %)
- `tenureInMonths` (Integer)
- `status` (LoanStatus enum: PENDING, APPROVED, REJECTED, DISBURSED, CLOSED)
- `applicationDate` (LocalDate)
- `approvalDate` (LocalDate)

#### LoanRepaymentSchedule Entity
- `id` (UUID)
- `loan` (ManyToOne relationship)
- `installmentNumber` (Integer)
- `dueDate` (LocalDate)
- `principal` (BigDecimal)
- `interest` (BigDecimal)
- `totalEmi` (BigDecimal)
- `paid` (Boolean, default: false)

### Issues Found
⚠️ **Duplicate Method**: `LoanController` has duplicate `approve()` method (lines 38-41 and 43-46). This will cause compilation/runtime issues.

---

## 2. Order Service (`backend/order-service`)

### Purpose
Manages product catalog, cart pricing with promotions, inventory reservation, and order confirmation with idempotency support.

### Technology Stack
- **Spring Boot**: 3.5.7
- **Java**: 17
- **Database**: H2 (in-memory)
- **Port**: 8080
- **Key Dependencies**: Spring Data JPA, Spring Web, Spring Validation, Actuator

### Core Functionality

#### 2.1 Product Management
- **Endpoints**:
  - `GET /products` - List all products
  - `POST /products` - Create new product
- **Product Model**:
  - `id` (UUID)
  - `name` (String)
  - `category` (ProductCategory enum)
  - `price` (BigDecimal)
  - `stock` (Integer)
  - `version` (Integer - for optimistic locking)

#### 2.2 Promotion Management
- **Endpoints**:
  - `GET /promotions` - List all promotions
  - `POST /promotions` - Create new promotion
- **Promotion Types**:
  1. **PERCENT_OFF_CATEGORY**: Percentage discount on specific product category
     - Config: `{"category": "STATIONERY", "percentage": 10}`
  2. **BUY_X_GET_Y**: Buy X items, get Y items free
     - Config: `{"productId": "uuid", "buy": 2, "get": 1}`

- **Promotion Features**:
  - Target customer segments (REGULAR, PREMIUM)
  - JSON-based configuration for flexibility
  - Chain of Responsibility pattern for applying multiple promotions

#### 2.3 Cart Quote
- **Endpoint**: `POST /order/quote`
- **Request**: `CartQuoteRequest` containing:
  - `items` (List of CartItemDto: productId, qty)
  - `customerSegment` (String: REGULAR or PREMIUM)

- **Functionality**:
  1. Validates product existence and stock availability
  2. Applies promotions based on customer segment
  3. Calculates price breakdown per item
  4. Returns total original price, final price, and discount amount

- **Response**: `CartQuoteResponse` with:
  - Price breakdown per item (original, final, discount, applied promotions)
  - Total original price
  - Total final price
  - Total discount

#### 2.4 Order Confirmation
- **Endpoint**: `POST /order/confirm`
- **Headers**: `Idempotency-Key` (required)
- **Functionality**:
  1. **Idempotency**: Checks if order already exists for the idempotency key
  2. **Stock Reservation**: Atomically reserves inventory using optimistic locking
  3. **Transaction Isolation**: Uses `SERIALIZABLE` isolation level for data consistency
  4. **Concurrency Handling**: Detects concurrent updates via `OptimisticLockingFailureException`

- **Response**: `CartConfirmResponse` with:
  - `orderId` (UUID)
  - `totalPrice` (BigDecimal)
  - `message` (String)

### Promotion Engine Architecture

#### Design Pattern: Chain of Responsibility
- Multiple promotion rules can be applied sequentially
- Each rule type has its own implementation:
  - `PercentOffCategoryPromotionRuleServiceImpl`
  - `BuyXGetYPromotionServiceImpl`
- Promotions are filtered by customer segment
- Promotions can be composed (multiple discounts applied)

#### Promotion Application Flow
1. Load all promotions from database
2. Filter by customer segment
3. Group by promotion type
4. Apply each promotion rule in order
5. Each rule modifies `LineItem.currentPrice`
6. Track applied promotions for reporting

### Data Models

#### Product Entity
- `id` (UUID)
- `name` (String)
- `category` (ProductCategory enum)
- `price` (BigDecimal)
- `stock` (Integer)
- `version` (Integer - optimistic locking)

#### Promotion Entity
- `id` (UUID)
- `name` (String)
- `type` (PromotionType enum)
- `config` (TEXT - JSON configuration)
- `targetSegments` (TEXT - comma-separated segments)

#### IdempotencyKeyRecord Entity
- `keyId` (String - idempotency key)
- `orderId` (UUID - associated order)

#### Order Entity
- Referenced but implementation not fully visible in scanned files

### Key Features

#### 1. Idempotency
- Prevents duplicate orders from retries
- Uses `Idempotency-Key` header
- Returns existing order if key already processed

#### 2. Optimistic Locking
- Uses `@Version` annotation on Product entity
- Prevents lost updates during concurrent stock reservations
- Throws `OptimisticLockingFailureException` on conflicts

#### 3. Transaction Management
- `SERIALIZABLE` isolation level for order confirmation
- Ensures atomic stock reservation and idempotency record creation

#### 4. Customer Segmentation
- Promotions can target specific customer segments
- Segments: REGULAR, PREMIUM
- Promotions without target segments apply to all customers

### Integration Points

#### Loan Service Webhook
- **Endpoint**: `/confirm-order` (empty implementation)
- **Purpose**: Likely intended for order confirmation callbacks to loan service

---

## Architecture Patterns

### Loan Service
- **Layered Architecture**: Controller → Service → Repository
- **Domain-Driven Design**: Separate domain entities
- **Service Components**: EMI Calculator, Schedule Generator

### Order Service
- **Layered Architecture**: Controller → Service → Repository
- **Strategy Pattern**: Promotion rules as pluggable strategies
- **Chain of Responsibility**: Sequential promotion application
- **Idempotency Pattern**: Prevents duplicate operations

---

## Database Configuration

Both services use:
- **H2 In-Memory Database**
- **JPA/Hibernate** with `ddl-auto: create-drop`
- **H2 Console** enabled for development
- Separate databases: `loan_db` and `order_db`

---

## Recommendations

### Loan Service
1. ⚠️ **Fix duplicate method** in `LoanController.approve()`
2. Add loan rejection endpoint
3. Add loan status query endpoints
4. Add repayment tracking endpoints
5. Consider adding loan disbursement functionality

### Order Service
1. Implement the loan service webhook endpoint
2. Add order history/query endpoints
3. Add order cancellation functionality
4. Consider adding payment processing integration
5. Add comprehensive error handling for promotion config parsing

### General
1. Add API documentation (Swagger/OpenAPI)
2. Add comprehensive unit and integration tests
3. Consider adding distributed tracing (e.g., Spring Cloud Sleuth)
4. Add health check endpoints (Actuator already included)
5. Consider migrating from H2 to production database (PostgreSQL/MySQL)
6. Add logging framework configuration
7. Add API versioning strategy

---

## API Summary

### Loan Service APIs
- `POST /api/loans` - Apply for loan
- `POST /api/loans/{loanId}/approve` - Approve loan

### Order Service APIs
- `GET /products` - List products
- `POST /products` - Create product
- `GET /promotions` - List promotions
- `POST /promotions` - Create promotion
- `POST /order/quote` - Get cart quote
- `POST /order/confirm` - Confirm order

---

## Testing Status
Both services have basic test structure but implementation not visible in scanned files.

