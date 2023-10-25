FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/demo-0.0.1-SNAPSHOT.jar ApiServer.jar
ENTRYPOINT ["java", "-jar", "ApiServer.jar"]