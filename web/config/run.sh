#!/bin/bash

#init environment
init(){
  configpath=$(cd `dirname $0`; pwd)
  cd ${configpath%config*}
  echo `pwd`
  if [ ! -d "data" ]; then
   mkdir -p data
  fi
  chmod 775 data
  if [ ! -d "logs" ]; then
   mkdir -p logs
  fi
  echo "data directory prepared."
}


# start robot application
start(){
     TIME_ZONE="-Duser.timezone=GMT+8"
     GC_METHOD="-XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+DisableExplicitGC"
     GC_SIZE="-Xmx2g -Xms2g -XX:NewRatio=1"
     GC_LOG="-XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:logs/gc.log"
     JAVA_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5897"
     exec java $TIME_ZONE $JAVA_DEBUG ${GC_SIZE} ${GC_METHOD} ${GC_LOG} -jar virus-miniapp-backend-0.0.1-SNAPSHOT.jar
  }



echo "startup robot server."


init

start

