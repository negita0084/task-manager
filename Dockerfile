FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

CMD ["java", "-jar", "build/libs/*SNAPSHOT.jar"]