FROM eclipse-temurin:17
COPY build/libs/gitByCity-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081:8081
ENTRYPOINT ["java", "-jar", "app.jar"]
