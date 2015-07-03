FROM java:openjdk-8-jdk
MAINTAINER shohinee585@gmail.com
EXPOSE 8080

RUN apt-get update
RUN apt-get install -y maven git python-virtualenv
RUN mkdir /root/.ssh
# Copy over private key, and set permissions
#ADD id_rsa /root/.ssh/id_rsa

# Create known_hosts
RUN touch /root/.ssh/known_hosts
# Add bitbuckets key
RUN ssh-keyscan github.com >> /root/.ssh/known_hosts

RUN pwd
RUN ls
WORKDIR /code
RUN git clone git@github.com:arupkumarm123/SmartCampaign.git
#ADD campaign-service/pom.xml /code/pom.xml
#ADD campaign-service/src /code/src
RUN cd campaign-service
RUN [ "mvn", "install" ]
ADD target/campaign-service-1.0-SNAPSHOT.jar /data/campaign-service.jar

CMD java -jar campaign-service.jar
