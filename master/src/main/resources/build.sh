#!/usr/bin/env bash
set -x
echo "HELLO MAAAN !!!!"
pwd
ls -lah
#docker run ubuntu /bin/echo "Hello world!"
docker build -t ciengine/tst .
echo "*** RUN"
docker run ciengine/tst
echo "*** DONE"
docker ps

