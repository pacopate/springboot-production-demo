FROM openjdk:8-jdk-slim
EXPOSE 9091

COPY . /car-pooling-challenge
 
ENTRYPOINT [ "/car-pooling-challenge" ]
