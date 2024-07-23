FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8070
ARG JAR_FILE=target/pharma-connect-auth-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
