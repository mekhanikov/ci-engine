#!/usr/bin/env bash
set -x
echo "HELLO MAAAN !!!!"
pwd
ls -lah
#docker run ubuntu /bin/echo "Hello world!"
# TODO use volume for mvn repo cache
# TODO share mvn cache between all docker containers? - no safe in parallel builds?
docker build -t ciengine/tst .
echo "*** RUN"
docker run ciengine/tst
echo "*** DONE"
docker ps

