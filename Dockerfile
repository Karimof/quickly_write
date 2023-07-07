FROM openjdk:17
EXPOSE 9090
ADD target/quickly_write_html.jar quickly_write_html.jar
ENTRYPOINT ["java","-jar","/quickly_write_html.jar"]


#WORKDIR /app
#COPY . /app/
#
#FROM openjdk:17
#WORKDIR /app
#COPY ./target app.jar
