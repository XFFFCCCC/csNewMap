#!/bin/bash

sync_jar(){
    configpath=$(cd `dirname $0`; pwd)
    cd ${configpath%web/config*}
    scp -r output/* alidev:/data/webapps/health-data/backend/
}


restart_robot(){
    ssh project@47.112.121.248 << eeooff

    waitForUp() {
    idx=1
    while ((idx < 30))
    do
        result=`curl -s -l -m 3 -o /dev/null -w "%{http_code}" localhost:9991/emotion/health/isAlive`
        echo "the ${idx}th times trys to connect to im service. return http code is ${result}"
        if [ ${result} -eq "200" ]; then
            echo "successfully restart lg emotion backend service."
            break;
        fi
        ((idx++))
        sleep 1s
    done
    if ((idx >= 30));then
        echo "lg emotion backend service may have not been started,Please check!"
        exit 1
    fi
  }

    echo "before restart lg-emotion-server ps ID:"
    ps -aux|grep virus-miniapp-backend-0.0.1-SNAPSHOT.jar|grep -v grep|awk '{print $2}'


    sudo svc -d /service/lg-emotion-server

    sudo svstat /service/lg-emotion-server

    sudo svc -u /service/lg-emotion-server

    #waitForUp

    echo "after restart lg-emotion-server ps ID:"
    ps -aux|grep virus-miniapp-backend-0.0.1-SNAPSHOT.jar|grep -v grep|awk '{print $2}'


    exit
eeooff
    echo "done!"
}

sync_jar

echo "sync jar to remote successful"

restart_robot
