FROM adoptopenjdk/openjdk11:alpine

VOLUME /tmp

COPY build/libs/products-microservice-*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]