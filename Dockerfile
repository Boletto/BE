FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

# 빌드 결과물 JAR 복사
COPY ./build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar", "--spring.profiles.active=prod"]
