FROM openjdk:17-oracle
EXPOSE 8080
ADD target/oracle-api-challenge.jar oracle-api-challenge.jar
ENTRYPOINT ["java","-jar","/oracle-api-challenge.jar"]
