FROM java:latest
COPY ./target/demo-0.0.1-SNAPSHOT.jar /mnt/jar/demo-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/mnt/jar/demo-0.0.1-SNAPSHOT.jar"]
