FROM gradle:8.5.0-jdk17 as BUILD

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon -x test

FROM amazoncorretto:21-alpine
WORKDIR /app

COPY --from=BUILD /home/gradle/src/build/libs/EtaRoutingGateway-0.0.1-SNAPSHOT.jar ./app.jar


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]
