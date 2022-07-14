FROM openjdk:11.0-oracle
COPY "./target/transaction-service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]