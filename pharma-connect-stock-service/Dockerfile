FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/pharma-connect-stock-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
