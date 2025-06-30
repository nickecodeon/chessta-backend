# Chessta-Backend ♟️

The nice little **Java** backend for the mobile chess application **chessta**. It is built with **Spring Boot** and uses **Hibernate (JPA)** for data persistence. The backend provides a RESTful API to manage chess games, handle moves, and store user and game state information in a relational database.

## Features 📦

- Create and manage chess games
- Store and export game states (FEN)
- Handle moves and player turns
- Database integration with Hibernate (JPA)
- RESTful API ready for mobile apps

## Getting Started 🚀

### Prerequisites

- **Java 17+**
- **Gradle**
- **PostgreSQL** running locally (or adapt to your own DB config)

### Clone the Project

```bash
git clone https://github.com/nickecodeon/chessta-backend.git
cd chessta-backend
```

 ### Configure the Database

 Create an application.properties file in src/main/resources/ (if it doesn’t already exist) and configure your database connection. An example configuration file is also provided in the same directory.

 ```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/chessta
spring.datasource.username=CHANGEME
spring.datasource.password=CHANGEME
spring.datasource.driver-class-name= org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
💡 You can switch to MySQL by changing the driver and dialect accordingly.

### Run the Application ▶️

Start the backend using the Gradle wrapper:

```bash
./gradlew bootRun
```
This will automatically run the main() method inside the entry point class:
```java
org.example.chessta.ChesstaApp
```
The server will start at:
```
http://localhost:8080
```

## Test the API 🧪

You can test the backend using curl, Postman, or connect your mobile frontend.

Example:
```bash
curl -X POST "http://localhost:8080/api/games/new?whitePlayerName=Alice&blackPlayerName=Bob"
```
---
