FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/gateway-example-0.0.1-SNAPSHOT.jar GatewayServer.jar
ENTRYPOINT ["java", "-jar", "GatewayServer.jar"]