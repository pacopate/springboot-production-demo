FROM maven:3-jdk-8-slim

COPY pom.xml /car-pooling-challenge/pom.xml
COPY mvnw /car-pooling-challenge/mvnw
COPY .mvn/ /car-pooling-challenge/.mvn
COPY src/ /car-pooling-challenge/src

WORKDIR /car-pooling-challenge

RUN mvn install -Pprod

EXPOSE 8090
EXPOSE 9091

ENTRYPOINT ["java","-jar","/car-pooling-challenge/target/pooling.car-0.0.1.jar"]
