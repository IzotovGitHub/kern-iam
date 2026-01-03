FROM eclipse-temurin:17-jre
ARG JAR_FILE="/target/*-exec.jar"
COPY ${JAR_FILE} kern-iam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kern-iam.jar"]