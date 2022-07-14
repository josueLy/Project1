FROM openjdk:11.0-oracle
COPY "./target/client-service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]