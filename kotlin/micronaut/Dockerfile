FROM adoptopenjdk/openjdk11:jre11u-ubuntu-nightly

COPY build/libs/library-*-all.jar library.jar

EXPOSE 8080

CMD ["java", "-jar", "library.jar"]