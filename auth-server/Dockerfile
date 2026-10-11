FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/auth-server-0.0.1-SNAPSHOT.jar auth-server.jar
ENTRYPOINT ["java","-jar","/auth-server.jar"]