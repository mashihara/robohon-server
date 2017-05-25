FROM java:latest
COPY ./target/demo-0.0.1-SNAPSHOT.jar /mnt/jar/.
WORKDIR /mnt/jar
CMD ["java","-jar","target/demo-0.0.1-SNAPSHOT.jar"]