FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=target/*.jar
ARG PROFILES
ARG ENV

# ARG APP_VERSION
# ARG DEPLOY_DATE
# ENV APP_VERSION=${APP_VERSION}
# ENV DEPLOY_DATE=${DEPLOY_DATE}
#
# #version check 기능 추가
# RUN echo "app.version=${APP_VERSION}" > /app/version.properties && \
#     echo "deploy.date=${DEPLOY_DATE}" >> /app/version.properties


COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
