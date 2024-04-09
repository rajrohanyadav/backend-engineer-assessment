FROM gradle:8.7-jdk AS builder

WORKDIR /home/gradle

COPY ./src/ src/

COPY settings.gradle build.gradle ./

RUN gradle clean build

FROM openjdk:22-jdk-slim AS runner

RUN adduser --system --group app-user

COPY --from=builder --chown=app-user:app-user /home/gradle/build/libs/app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

USER app-user

CMD ["java", "-jar", "app.jar"]
