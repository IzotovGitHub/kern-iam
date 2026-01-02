FROM eclipse-temurin:17-jdk-noble
ARG JAR_FILE=target/kern-iam-*.jar
COPY ${JAR_FILE} opt/kern-iam.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "opt/kern-iam.jar"]