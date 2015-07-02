FROM java:openjdk-8-jdk
MAINTAINER shohinee585@gmail.com
EXPOSE 8080
RUN apt-get update
RUN apt-get install -y maven
WORKDIR /code
ADD pom.xml /code/pom.xml
ADD src /code/src
RUN [ "mvn", "install" ]
ADD campaign-service-1.0-SNAPSHOT.jar /data/campaign-service.jar

CMD java -jar campaign-service.jar
