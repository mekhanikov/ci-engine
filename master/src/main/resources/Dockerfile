FROM ubuntu

RUN apt-get update && apt-get install -y maven && apt-get clean && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["mvn", "-v"]
