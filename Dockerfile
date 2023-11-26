FROM openjdk:17
ADD target/springsecurity-0.0.1-SNAPSHOT.jar testapp.jar
ENTRYPOINT ["java", "-jar","testapp.jar"]