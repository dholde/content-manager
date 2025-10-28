# Content Manager Service

A Spring Boot service that helps create, manage, validate and moderate content. This service provides RESTful APIs for managing various types of content including users, blog posts, customer support requests, and support responses.

## Features

- **Content Management**: Create, read, update, and delete various content types
- **Validation Pipeline**: Configurable validation system with support for length checks and custom validators
- **Database Support**: H2 for testing, MySQL for production
- **Profile-based Configuration**: Separate configurations for test and production environments

## Technology Stack

- **Framework**: Spring Boot 3.5.6
- **Database**: H2 (test), MySQL (production)
- **Java Version**: 24
- **Build Tool**: Maven
- **Testing**: JUnit 5, Spring Boot Test

## Getting Started

### Prerequisites

- Java 24 or higher
- Maven 3.6+
- MySQL (for production)

### Running the Application

#### Development Mode (H2 Database)
```bash
./mvnw spring-boot:run
```

#### Production Mode (MySQL Database)
```bash
./mvnw spring-boot:run --spring.profiles.active=prod
```

#### Running Tests
```bash
./mvnw test
```

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update existing user |
| DELETE | `/api/users/{id}` | Delete user |
| GET | `/api/users/{id}/blogposts` | Retrieve blog posts owned by a specific user |

**User Model:**
```json
{
  "id": "uuid",
  "alias": "string",
  "email": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Blog Posts

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/blog-posts/{id}` | Get blog post by ID |
| POST | `/api/blog-posts` | Create new blog post |
| PUT | `/api/blog-posts/{id}` | Update existing blog post |
| DELETE | `/api/blog-posts/{id}` | Delete blog post |
| GET | `/api/blogposts?userId={userId}` | Filter blog posts by user (optional query parameter) |

> **Note:** The endpoint `/api/blogposts/user/{userId}` is now deprecated. Use `/api/blogposts?userId={userId}` instead.

**Blog Post Model:**
```json
{
  "id": "uuid",
  "title": "string",
  "content": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp",
  "userId": "uuid"
}
```

### Customer Support Requests

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customer-requests` | Get all customer requests |
| GET | `/api/customer-requests/{id}` | Get customer request by ID |
| POST | `/api/customer-requests` | Create new customer request |
| PUT | `/api/customer-requests/{id}` | Update existing customer request |
| DELETE | `/api/customer-requests/{id}` | Delete customer request |

**Customer Request Model:**
```json
{
  "id": "uuid",
  "text": "string",
  "supportResponse": "uuid",
  "customerId": "uuid",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Support Responses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/support-responses/{id}` | Get support response by ID |
| POST | `/api/support-responses` | Create new support response |
| PUT | `/api/support-responses/{id}` | Update existing support response |
| DELETE | `/api/support-responses/{id}` | Delete support response |

**Support Response Model:**
```json
{
  "id": "uuid",
  "text": "string",
  "supportRequest": "uuid",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Validation

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/validate/blogpost` | Validate a blog post |

**Validation Request Model:**
```json
{
  "titleMinLength": "integer",
  "titleMaxLength": "integer",
  "contentMinLength": "integer",
  "contentMaxLength": "integer",
  "blogPost": {
    "id": "uuid",
    "title": "string",
    "content": "string",
    "createdAt": "timestamp",
    "updatedAt": "timestamp",
    "userId": "uuid"
  }
}
```

**Validation Response Model:**
```json
{
  "contentType": "string",
  "validationResult": {
    "valid": "boolean",
    "errors": [
      {
        "code": "string",
        "message": "string"
      }
    ]
  }
}
```

## Validation System

The service includes a flexible validation pipeline system:

### Available Validators

- **LengthValidator**: Validates string length against min/max bounds

### Usage Example

```java
ValidationPipeline pipeline = new BlockPostValidationPipeline();
List<ValidationStep> steps = List.of(new LengthValidator(5, 100));
boolean isValid = pipeline.runPipeline(content, steps);
```

## Configuration

### Application Profiles

- **Default/Test Profile**: Uses H2 in-memory database
- **Production Profile**: Uses MySQL database

### Database Configuration

The application automatically sets up the required database schema on startup. Schema files are located in `src/main/resources/schema.sql`.

### Example Configuration (application.yml)

```yaml
spring:
  profiles:
    active: test
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
```

## Project Structure

```
src/
├── main/java/com/dehold/contentmanager/
│   ├── user/                      # User management
│   ├── content/
│   │   ├── blogpost/              # Blog post management
│   │   └── customersupport/       # Customer support system
│   └── validation/                # Validation framework
└── test/                          # Test classes
```

## Development

### Database Schema

The service uses the following main tables:
- `user` - User information
- `blog_post` - Blog posts
- `customer_request` - Customer support requests
- `support_response` - Support team responses

### Adding New Content Types

1. Create model class in appropriate package
2. Implement repository with JDBC operations
3. Create service layer
4. Add REST controller
5. Write integration tests

## Testing

The project includes comprehensive test coverage:
- Unit tests for service layers
- Integration tests for REST endpoints
- Validation pipeline tests

Run specific test suites:
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw -Dtest=CustomerRequestControllerIntegrationTest test
```
