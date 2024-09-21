# Build a JAR File
FROM maven:3.8.1-openjdk-17-slim AS stage1
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Create an Image
FROM openjdk:17-jdk-slim
EXPOSE 5000
COPY --from=stage1 /home/app/target/cms-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

