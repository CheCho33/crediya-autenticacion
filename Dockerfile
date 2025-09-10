# Usar java 21 como imagen base
FROM amazoncorretto:21-alpine AS builder
WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY main.gradle .

# Hacer gradlew ejecutable
RUN chmod +x ./gradlew


RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew bootJar --no-daemon -x validateStructure

FROM amazoncorretto:21-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/applications/app-service/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8081

# Variables de entorno para JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Comando de ejecución (corregido)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]