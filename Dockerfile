FROM openjdk:17-jdk-alpine
VOLUME /main-app
COPY demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]