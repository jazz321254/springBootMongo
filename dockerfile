FROM openjdk:8-jdk-alpine
COPY ./target/jazz321254.com.mongo-0.0.1-SNAPSHOT.jar /usr/src/jazz321254/
WORKDIR /usr/src/jazz321254
EXPOSE 8080
CMD ["java", "-jar", "jazz321254.com.mongo-0.0.1-SNAPSHOT.jar"]