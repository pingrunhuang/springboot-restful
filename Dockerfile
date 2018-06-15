FROM java:8u111-jdk

ADD . /code

WORKDIR /code

ENTRYPOINT ["java", "-jar", "/code/target/DataExceptionHandler-1.0-SNAPSHOT.jar"]