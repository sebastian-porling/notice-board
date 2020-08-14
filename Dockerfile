FROM openjdk:14
ADD target/session-1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]