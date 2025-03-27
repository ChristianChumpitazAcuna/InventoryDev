FROM openjdk:21

WORKDIR /app

COPY target/*.jar /app/app.jar

ENV DB_URI=default

EXPOSE 8090


CMD ["java", "-jar", "/app/app.jar"]