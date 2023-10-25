FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/eureka-example-0.0.1-SNAPSHOT.jar EurekaServer.jar
ENTRYPOINT ["java", "-jar", "EurekaServer.jar"]