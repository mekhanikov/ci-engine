#!/usr/bin/env bash
#set -x
#ps aux | grep build
./build.sh &
pid=$!
echo $! > pid
wait $!
status=$?
echo $status > status
echo "build.sh done with exit code: $status"
