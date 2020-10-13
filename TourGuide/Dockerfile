FROM gradle:jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar

FROM openjdk:11-jre-slim
COPY --from=builder /home/gradle/src/build/libs/TourGuide_RewardsApp-1.0.0.jar /app/TourGuide_RewardsApp-1.0.0.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/TourGuide_RewardsApp-1.0.0.jar"]
