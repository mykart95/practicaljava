FROM amazoncorretto:20

ARG JAR_FILE=build/libs/practicaljava-3.0.0.jar

ADD ${JAR_FILE} practicaljava.jar

ENTRYPOINT ["java", "-jar", "practicaljava.jar"]