FROM openjdk:8
VOLUME /temp
EXPOSE 8085
ADD ./target/service-consumo-0.0.1-SNAPSHOT.jar consumo.jar
ENTRYPOINT ["java","-jar","consumo.jar"]