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
RUN ssh-keyscan github.com >> /root/.ssh/known_hosts

WORKDIR /code
RUN git clone https://arupkumarm123:Shohini010@github.com/arupkumarm123/SmartCampaign.git
RUN cd SmartCampaign/campaign-service
WORKDIR /code/SmartCampaign/campaign-service
RUN [ "mvn", "install" ]
WORKDIR /code/SmartCampaign/campaign-service/target
RUN  pwd
ADD campaign-service-1.0-SNAPSHOT.jar /data/campaign-service.jar

CMD java -jar campaign-service.jar
