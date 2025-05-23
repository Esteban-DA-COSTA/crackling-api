# Cracking API

A RESTful API for team and task management built with Kotlin and Ktor.

## Overview

Cracking API is a backend service that provides endpoints for managing teams, members, and tasks. It follows RESTful principles with HATEOAS (Hypermedia as the Engine of Application State) implementation for better API navigation and discoverability.

## Features

- **Authentication**: Secure JWT-based authentication
- **Team Management**: Create, read, update, and delete teams
- **Member Management**: Add members to teams with different roles
- **Task Management**: Create and manage tasks within teams
- **HATEOAS**: Hypermedia links for API navigation
- **CORS Support**: Cross-Origin Resource Sharing enabled
- **Swagger Documentation**: API documentation with Swagger

## Technologies

- **Kotlin**: Programming language
- **Ktor**: Asynchronous web framework
- **Exposed**: SQL library for Kotlin
- **JWT**: JSON Web Tokens for authentication
- **MSSQL**: Microsoft SQL Server for data storage
- **Gradle**: Build system

## Getting Started

### Prerequisites

- JDK 11 or higher
- Microsoft SQL Server
- Gradle

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-organization/crackling.git
   cd crackling/Cracking-api
   ```

2. Configure the database connection in `src/main/resources/application.conf` or `application.yaml`

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Run the application:
   ```bash
   ./gradlew run
   ```

The API will be available at `http://localhost:8080` by default.

## API Endpoints

### Authentication

- `POST /auth/login`: Authenticate a user and get a JWT token
- `POST /auth/register`: Register a new user

### Teams (Authenticated)

- `GET /teams`: Get all teams
- `GET /teams/{id}`: Get a team by ID
- `POST /teams`: Create a new team
- `PUT /teams/{id}`: Update a team
- `DELETE /teams/{id}`: Delete a team

### Members (Authenticated)

- `GET /teams/{teamId}/members`: Get all members of a team
- `POST /teams/{teamId}/members`: Add a member to a team
- `DELETE /teams/{teamId}/members/{email}`: Remove a member from a team
- `PATCH /teams/{teamId}/members/{email}/role`: Change a member's role

### Tasks (Authenticated)

- `GET /teams/{teamId}/tasks`: Get all tasks of a team
- `GET /teams/{teamId}/tasks/{id}`: Get a task by ID
- `POST /teams/{teamId}/tasks`: Create a new task
- `PUT /teams/{teamId}/tasks/{id}`: Update a task
- `DELETE /teams/{teamId}/tasks/{id}`: Delete a task

## Development

### Project Structure

- `src/main/kotlin/com/crackling/api/plugins`: Configuration for Ktor plugins
- `src/main/kotlin/com/crackling/api/routing`: API route definitions
- `src/main/kotlin/com/crackling/domain`: Domain models and entities
- `src/main/kotlin/com/crackling/services`: Business logic services

### Running Tests

```bash
./gradlew test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.