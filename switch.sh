#!/bin/bash

# Crawl current connected port of WAS
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
target_port=0

echo "> Nginx currently proxies to ${CURRENT_PORT}."

# Toggle port number
if [ ${CURRENT_PORT} -eq 8081 ]; then
	target_port=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
	target_port=8081
else
	echo "> No WAS is connected to nginx"
	exit 1
fi

# Change proxying port into target port
echo "set \$service_url http://127.0.0.1:${target_port};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Now Nginx proxies to ${target_port}."

# Reload nginx
sudo service nginx reload

echo "> Nginx reloaded."
