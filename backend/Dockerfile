FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.7_10

COPY target/posts-explorer-backend-*-SNAPSHOT.jar posts-explorer-backend-*-SNAPSHOT.jar

# FIXME create user a DO NOT run as root !
CMD java -jar posts-explorer-backend-*-SNAPSHOT.jar
