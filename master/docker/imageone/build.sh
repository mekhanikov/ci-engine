#!/usr/bin/env bash
set -x
echo "HELLO MAAAN !!!!"
pwd
ls -lah
#docker run ubuntu /bin/echo "Hello world!"
# TODO use volume for mvn repo cache
# TODO share mvn cache between all docker containers? - no safe in parallel builds?
ID=$(uuidgen)
echo $ID > dockerimageid.txt
docker build -t $ID .

idc=$(docker create $ID)
docker cp $idc:/build/agentoutput.log - > agentoutput.log.tar
docker rm -v $idc

#docker cp $ID:/build/agentoutput.log - > agentoutput.log.tar

#docker build -t ciengine/tst .
#echo "*** RUN"
#cciengine/tst
#echo "*** DONE"
docker ps

