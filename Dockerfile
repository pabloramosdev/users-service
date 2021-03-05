FROM openjdk:11
WORKDIR /home/app
COPY build/libs/users-service-0.1-all.jar /home/app/application.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "application.jar"]