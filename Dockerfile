# syntax=docker/dockerfile:1
FROM openjdk:17-alpine

WORKDIR /fetch

COPY src/main/java/Fetch.java ./Fetch.java

RUN javac ./Fetch.java

CMD ["java", "Fetch", "--metadata", "http://www.google.com", "https://autify.com"]
