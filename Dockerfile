FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /build
COPY src/ /build/src/
COPY pom.xml /build/
RUN mvn clean package spring-boot:repackage

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /build/target/*.jar /app/magicvolley.jar
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
EXPOSE 8081
ENTRYPOINT exec java $JAVA_OPTS -jar magicvolley.jar