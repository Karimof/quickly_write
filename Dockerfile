FROM openjdk:17
EXPOSE 9090
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]

#FROM openjdk:11-ea-17
#MAINTAINER baeldung.com
#COPY target/quickly_write_html-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#WORKDIR /app
#COPY . /app/
#
#FROM openjdk:17
#WORKDIR /app
#COPY ./target app.jar
