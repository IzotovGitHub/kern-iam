FROM eclipse-temurin:17-jre
ARG JAR_FILE="/target/kern-iam-1.0-SNAPSHOT-exec.jar"
COPY ${JAR_FILE} kern-iam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kern-iam.jar"]