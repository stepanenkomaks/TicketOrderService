FROM openjdk:17
ADD /target/*.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]