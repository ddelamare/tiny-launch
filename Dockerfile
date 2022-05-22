FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/tiny-launch-0.0.1-SNAPSHOT-standalone.jar /tiny-launch/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/tiny-launch/app.jar"]
