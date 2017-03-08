#!/bin/bash

PIDS=`ps -f | grep java | grep trending |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

rm -rf trending.jar logs/

DEPLOY_DIR=`pwd`

LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
    LOGS_DIR=`dirname $LOGS_FILE`
else
    LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log


mvn clean package install -Dmaven.test.skip=true
mv target/trending.jar .
rm -rf target/
nohup java -jar trending.jar > $STDOUT_FILE 2>&1 &

echo "OK!"
PIDS=`ps -f | grep java | grep trending |awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
