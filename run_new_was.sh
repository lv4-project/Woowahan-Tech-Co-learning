#!/bin/bash

# Crawl current connected port of WAS
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
target_port=0

echo "> Current port of running WAS is ${CURRENT_PORT}."



# Toggle port Number
if [ ${CURRENT_PORT} -eq 8081 ]; then
	target_port=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
	target_port=8081
else
	echo "> No WAS is connected to nginx"
	exit 1
fi

# Kill runnnig was of target port


TARGET_PID=$(lsof -Fp -i TCP:${target_port} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

echo "> Kill WAS running at ${target_port}."
sudo kill ${TARGET_PID}

# Run new WAS as a daemon
echo "> Now new WAS runs at ${target_port}."
java -jar -Dserver.port=${target_port} -Dspring.profiles.active=production ./build/libs/tecolearning-0.0.1-SNAPSHOT.jar &
exit 0
