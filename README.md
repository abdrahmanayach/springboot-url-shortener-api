# URL Shortener API

Simple REST API service built with Spring Boot, Java, and Hibernate for creating, managing, and redirecting shortened URLs with click tracking.

## Features

- Built with Spring Boot, Spring Data JPA, and PostgreSQL
- RESTful endpoints with proper HTTP status codes
- URL shortening with unique codes and click tracking

## Installation

Clone the repository and install dependencies:

```bash
mvn clean install
```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Documentation

Interactive API documentation available at:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs

## Usage

```bash
mvn spring-boot:run     # Development
mvn package             # Production
```

The server runs on http://localhost:8080

## API Endpoints

### Create Short URL

POST /api/shorten

Request Body:

```json
{ "originalUrl": "https://www.github.com/spring-projects/spring-boot" }
```

Response:

```json
{
  "shortCode": "x7k2m9",
  "shortUrl": "http://localhost:8080/x7k2m9"
}
```

### Redirect to Original URL

GET /x7k2m9

Response: Redirects to the original URL and increments the click count.

### Get URL Details

GET /api/x7k2m9

Response:

```json
{
  "originalUrl": "https://www.github.com/spring-projects/spring-boot",
  "clickCount": 5,
  "createdAt": "2026-03-10T10:30:00Z"
}
```

### Delete Short URL

DELETE /api/x7k2m9

Response: 204 No Content
