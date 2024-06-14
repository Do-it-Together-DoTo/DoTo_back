#!/bin/bash
# 파일 경로 설정
JAR_FILE="/home/ubuntu/doto-0.0.1-SNAPSHOT.jar"
LOG_FILE="/home/ubuntu/deploy.log"
ERR_LOG_FILE="/home/ubuntu/deploy_err.log"

# 기존 파일 삭제
if [ -f "$JAR_FILE" ]; then
    rm "$JAR_FILE"
    echo "$JAR_FILE 삭제 완료"
fi

if [ -f "$LOG_FILE" ]; then
    rm "$LOG_FILE"
    echo "$LOG_FILE 삭제 완료"
fi

if [ -f "$ERR_LOG_FILE" ]; then
    rm "$ERR_LOG_FILE"
    echo "$ERR_LOG_FILE 삭제 완료"
fi

pid=$(pgrep -f doto)
if [ -n "${pid}" ]; then
    kill -15 ${pid}
    echo "Killed process ${pid}"
else
    echo "No process found"
fi

chmod +x /home/ubuntu/doto-0.0.1-SNAPSHOT.jar
nohup java -jar /home/ubuntu/doto-0.0.1-SNAPSHOT.jar >> /home/ubuntu/deploy.log 2>> /home/ubuntu/deploy_err.log &