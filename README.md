# backend-example-spring

An example of a backend application built with [Spring Boot](https://spring.io/projects/spring-boot). Most of the source code is written in [Kotlin](https://kotlinlang.org/).

This example project is an update for [spring-boot-rest-api](https://github.com/unhurried/spring-boot-rest-api), that is written in Java.

## Libraries (Dependencies)

Following are the libraries used in this project.

* Language

  * Kotlin/JVM

* Build Tool

  - [Gradle](https://gradle.org/) with [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

* JAX-RS

  * [Jersey](https://jersey.github.io/)（spring-boot-starter-jersey）

* Aspect Oriented Programming

  * [Spring AOP](https://docs.spring.io/spring/docs/2.5.x/reference/aop.html) (AspectJ annotations)

* Connection Pooling

  * [HikariCP](https://github.com/brettwooldridge/HikariCP)

* Database Access

  * [Spring Data JPA](https://projects.spring.io/spring-data-jpa/)（spring-boot-starter-data-jpa）

* Transaction Management

  * [Transaction Management Feature of Spring Framework](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/transaction.html)

* Security

  * [Spring Security](https://spring.io/projects/spring-security) (spring-boot-starter-oauth2-resource-server)

* Configuration

  * [spring-boot-configuration-processor](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)

* Logging

  * [SLF4J](https://www.slf4j.org/) + [Logback](http://logback.qos.ch/)

* Testing & Dev Tools

  * [spring-boot-devtools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html)
  * JUnit5
  * [spring-boot-test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
  * [spring-security-test](https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/test.html)

  * [H2 Database](https://www.h2database.com/html/main.html)

## How to Start Development

### With IntelliJ IDEA Community or Ultimate

Although you can start the development a text editor and gradle(w) commands, using [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community or Ultimate) is recommended as source code is written in Kotlin.

```shell
# Run the application in test mode.
# (For integration testing with https://github.com/unhurried/example-test-restassured)
./gradlew bootRun --args='--spring.profiles.active=test'

# Run the application in development mode.
# (To work with https://github.com/unhurried/frontend-example-next)
./gradlew bootRun --args='--spring.profiles.active=development'

# Build a jar file that can be used in a different project.
# (Generated jar file will be stored in "backend/build/libs".)
./gradlew build
# The jar file also can be install in a local maven repository.
./gradlew publishToMavenLocal

# Build an executable jar file.
# (Generated jar file will be stored in "backend/build/libs".)
./gradlew bootJar
```

## API Requests and Responses

APIs for CRUD operations of ToDo resource are implemented.

```shell
# Create a ToDo item.
$ curl --request POST \
>  --url http://localhost:8080/todos \
>  --header 'Authorization: MyAccessToken' \
>  --header 'Content-Type: application/json' \
>  --data '{"title":"Test","category": "one",content":"This is a test item."}' \
>  --include

HTTP/1.1 200
Content-Type: application/json
Content-Length: 71

{"id": "9f39fc1c-3350-49ff-9a1c-922262a124c7","title":"Test","category":"one","content":"This is a test item."}
```



