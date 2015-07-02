FROM java:openjdk-8-jdk
MAINTAINER shohinee585@gmail.com
EXPOSE 8080
RUN apt-get update
RUN apt-get install -y maven
WORKDIR /code
ADD /campaign-service/pom.xml /code/pom.xml
ADD /campaign-service/src /code/src
RUN [ "mvn", "install" ]
ADD target/campaign-service-1.0-SNAPSHOT.jar /data/campaign-service.jar

CMD java -jar campaign-service.jar
