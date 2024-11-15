# 1. 빌드 스테이지
FROM bellsoft/liberica-openjdk-alpine:17 as builder

WORKDIR /app

# 프로젝트 소스와 Gradle Wrapper 복사
COPY . .

# Gradle 빌드 실행
RUN ./gradlew clean build --no-daemon

# 2. 실행 스테이지
FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

# 빌드 결과물 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]