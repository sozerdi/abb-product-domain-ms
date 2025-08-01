FROM registry.access.redhat.com/ubi8/openjdk-21:latest
COPY build/libs/*.jar /app/app.jar
WORKDIR /app/
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
