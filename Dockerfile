FROM openjdk:17 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY ./target/0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar"]