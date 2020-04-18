FROM gradle:6.3.0-jdk11 as builder
COPY . /usr/src/acme-services/
WORKDIR /usr/src/acme-services
RUN gradle :acme-api-gateway:clean :acme-api-gateway:build :acme-api-gateway:bootJar -i --stacktrace

FROM openjdk:11-jre-slim
EXPOSE 3000
RUN mkdir /app
COPY --from=builder /usr/src/acme-services/acme-api-gateway/build/libs/acme-api-gateway-*.jar /app/acme-api-gateway.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/acme-api-gateway.jar"]