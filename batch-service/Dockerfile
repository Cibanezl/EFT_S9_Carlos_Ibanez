FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/batch-service-0.0.1-SNAPSHOT.jar batch-service.jar
ENTRYPOINT ["java","-jar","/batch-service.jar"]