#FROM openjdk:17
#EXPOSE 9090
#ADD /target/spring-boot-docker.jar spring-boot-docker.jar
#ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]

FROM java:17
EXPOSE 9090
VOLUME /tmp
WORKDIR /quickly_write_html

RUN apt-get update
RUN apt-get install -y maven

ADD pom.xml /quickly_write_html/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD src /quickly_write_html/src
RUN ["mvn", "package"]

ADD target/spring-boot-docker.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/app.jar"]

#FROM openjdk:17
#MAINTAINER baeldung.com
#COPY /target/spring-boot-docker.jar spring-boot-docker.jar
#ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]

#WORKDIR /app
#COPY . /app/
#
#FROM openjdk:17
#WORKDIR /app
#COPY ./target app.jar
