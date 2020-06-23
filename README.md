# Posts Explorer

This is a small Java 11 [Spring-Boot](https://spring.io/projects/spring-boot) and [Angular](https://angular.io/)
based project that fetch posts from [this URL](https://jsonplaceholder.typicode.com/posts), stores them in a MongoDB 
instance, and serves the first 50 of them (in ascending order based on their title) in a 
[Material table](https://material.angular.io/components/table/overview).

This project was developed following [TDD](https://en.wikipedia.org/wiki/Test-driven_development) (see `git log`) and
the [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship-ebook/dp/B001GSTOAM) and 
[Clean Architecture](https://www.amazon.com/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164) 
guidelines from Robert C. Martin.

## Pre-requisites

- Java 11+
- Maven 3.6+
- [Docker](https://www.docker.com/)
- [docker-compose](https://docs.docker.com/compose/install/)

Simplest install method for Java and Maven in UNIX environments is via [SDKMAN](https://sdkman.io/)

For local frontend development :
- [node.js](https://nodejs.org/en/)
- [yarn](https://yarnpkg.com/)

## Build

In the root directory of this project, simply run :
```bash
mvn clean install
```
This will compile, run tests, package and build frontend and backend docker images (this may take a few minutes,
especially for frontend on first build)

## Run

Once the project is built, run :
```bash
docker-compose -f docker-compose.yml -f mongodb.yml up
```
Wait a few seconds for the components (mongo, backend and fronted) to start. When `Posts sync job done.` is shown
 is the logs, you can open your browser at http://localhost:8000

## Development

### MongoDB

Start a local instance with :
```bash
docker-compose -f mongodb.yml up
```

### Backend

For backend development, run this command from the root directory :
```bash
mvn clean spring-boot:run
```

### Frontend
For frontend development, `cd` to `frontend`, then run :
```bash
yarn start
```

Then open your browser at http://localhost:4200