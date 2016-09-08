#!/usr/bin/env bash
set -x
echo "HELLO MAAAN !!!!"
ls -lah | grep Dock
#docker run ubuntu /bin/echo "Hello world!"
docker build -t ciengine/tst .
 # docker run -i -t tst
#docker ps

