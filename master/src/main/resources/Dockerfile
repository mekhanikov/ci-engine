FROM ubuntu

RUN apt-get update && apt-get install -y maven git curl && apt-get clean && rm -rf /var/lib/apt/lists/*
WORKDIR /build
RUN mkdir -p /root/.ssh/
COPY id_rsa /root/.ssh/
COPY settings.xml /root/.m2/
COPY agent-1.0-SNAPSHOT.jar ./
COPY environment_variables.properties ./
RUN echo "StrictHostKeyChecking no" > /etc/ssh/ssh_config

RUN java -jar -Dmode=start agent-1.0-SNAPSHOT.jar --spring.config.location=environment_variables.properties
# TODO it is steps should be in jar
#RUN git clone ssh://git@stash.hybris.com:7999/platform/atdd.git source
#RUN cp source/pom.xml ./
#RUN mvn clean install > logs.txt
