FROM gradle:6.3.0-jdk11 as builder
COPY . /usr/src/acme-services/
WORKDIR /usr/src/acme-services
RUN gradle :acme-accounts:clean :acme-accounts:build :acme-accounts:bootJar -i --stacktrace

FROM openjdk:11-jre-slim
EXPOSE 3002
RUN mkdir /app
COPY --from=builder /usr/src/acme-services/acme-accounts/build/libs/acme-accounts-*.jar /app/acme-accounts.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/acme-accounts.jar"]