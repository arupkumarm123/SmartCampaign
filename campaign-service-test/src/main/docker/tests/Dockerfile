 FROM java:openjdk-8-jdk
 MAINTAINER shohinee585@gmail.com
 EXPOSE 8080
 
 RUN ls /data/jenkins/slaves/cd/workspace/smartcampaign/campaign-service-test/target

 ADD target/campaign-service-test.jar /data/campaign-service-test.jar
CMD java -jar /data/campaign-service.jar

