FROM gradle:6.3.0-jdk11 as builder
COPY . /usr/src/acme-services/
WORKDIR /usr/src/acme-services
RUN gradle :acme-payments:clean :acme-payments:build :acme-payments:bootJar -i --stacktrace

FROM openjdk:11-jre-slim
EXPOSE 3001
RUN mkdir /app
COPY --from=builder /usr/src/acme-services/acme-payments/build/libs/acme-payments-*.jar /app/acme-payments.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/acme-payments.jar"]