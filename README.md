# Watch application
> Marek Ciz, July 2020

## Architecture

I am using [Spring Data Rest](https://spring.io/projects/spring-data-rest) library with
[Spring Data JPA](https://spring.io/projects/spring-data-jpa) repositories. For versioning the database schema I am 
using [Flyway](https://flywaydb.org/) but I am not generating any dummy data. For storing backend data 
is integrated [PostgreSQL](https://www.postgresql.org/docs/) database.

# Prerequisites

1. Installed last `JDK11`, and its path specified in the `$JAVA_HOME` environment variable
2. Running Docker

## Start-up
1. Go to root folder and execute docker command to run the DB: `docker-compose up -d`.
2. Use embedded maven to build the project: `./mvnw clean package`.
3. Run the JAR file: `java -jar target/watches-0.0.1-SNAPSHOT.jar`.
4. FOR API examples go to `watches/http/rest-client.queries.http` file, in which you can execute *POST, GET* command with 
prepared JSON and XML requests.

## What could be improved
* add integration tests (Cucumber) + database for testing
* Dummy data in Flyway
* add custom validation annotation tests
* add mapper decorator Test
* OpenApi documentation
* add checkstyle
* gitignore improvements