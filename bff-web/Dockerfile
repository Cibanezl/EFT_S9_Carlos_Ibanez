FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/bff-web-0.0.1-SNAPSHOT.jar bff-web.jar
ENTRYPOINT ["java","-jar","/bff-web.jar"]