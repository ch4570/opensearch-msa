FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/service-0.0.1-SNAPSHOT.jar AnalyticsServer.jar
ENTRYPOINT ["java", "-jar", "AnalyticsServer.jar"]