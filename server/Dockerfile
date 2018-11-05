FROM maven:3.6.0-jdk-8-alpine

ENV APP_DIR=/usr/src/app

RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR

ADD pom.xml pom.xml
ADD ./src ./src

EXPOSE 8080
CMD mvn spring-boot:run