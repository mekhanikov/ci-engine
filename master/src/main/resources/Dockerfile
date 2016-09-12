FROM ubuntu

RUN apt-get update && apt-get install -y maven git curl && apt-get clean && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /root/.ssh/
COPY id_rsa /root/.ssh/
COPY settings.xml /root/.m2/
#RUN cat -v /root/.ssh/id_rsa
RUN echo "StrictHostKeyChecking no" > /etc/ssh/ssh_config
WORKDIR /build

# TODO it is steps should be in jar
RUN git clone ssh://git@stash.hybris.com:7999/platform/atdd.git source
RUN cp source/pom.xml ./
RUN mvn clean install > logs.txt
#RUN mvn -B hybris:yunitinit -Dtenant=master hybris:initialize

#RUN mkdir source; cd source; git clone ssh://git@stash.hybris.com:7999/platform/atdd.git
#ENTRYPOINT ["mvn", "-v"]
