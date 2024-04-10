#!/bin/bash

complie(){
    configpath=$(cd `dirname $0`; pwd)
    cd ${configpath%web/config*}
}

sync_jar(){
    configpath=$(cd `dirname $0`; pwd)
    cd ${configpath%web/config*}
    scp -r output/* mdev:/data/webapps/virus-backend
}



restart_robot(){
    ssh mdev << eeooff


    ls /data/webapps/virus-backend -al

     echo "before restart virus ps ID:"
     ps -aux|grep virus-miniapp-backend-0.0.1-SNAPSHOT.jar|grep -v grep|awk '{print $2}'


    sudo svc -d /service/virus-backend

    sudo svstat /service/virus-backend

    sudo svc -u /service/virus-backend


    echo "after restart virus-data ps ID:"
    ps -aux|grep virus-miniapp-backend-0.0.1-SNAPSHOT.jar|grep -v grep|awk '{print $2}'


    exit
eeooff
    echo "done!"
}

#complie

sync_jar

echo "sync jar to remote successful"

restart_robot
