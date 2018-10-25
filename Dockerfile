FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/stockai-reference-0.0.1-SNAPSHOT.jar stockai-reference-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","stockai-reference-0.0docker.1-SNAPSHOT.jar"]