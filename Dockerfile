FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY target/*.jar app.jar
EXPOSE 8080

# min 128 mb - max 256 mb
ENV JAVA_OPTS="-Xmx256m -Xms128m"
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
