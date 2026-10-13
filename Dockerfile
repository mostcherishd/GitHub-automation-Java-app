# ---- Stage 1: build the JAR ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- Stage 2: build a minimal Java runtime ----
FROM eclipse-temurin:17-jdk-alpine AS jre

RUN jlink \
    --add-modules java.base,java.logging,java.xml,java.naming,java.desktop,java.management,java.sql,java.instrument,java.security.jgss,jdk.unsupported,jdk.crypto.ec \
    --strip-debug --no-man-pages --no-header-files --compress=2 \
    --output /javaruntime

# ---- Stage 3: final image ----
FROM alpine:3.20
WORKDIR /app

ENV JAVA_HOME=/opt/java
ENV PATH="$JAVA_HOME/bin:$PATH"

COPY --from=jre /javaruntime $JAVA_HOME
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]