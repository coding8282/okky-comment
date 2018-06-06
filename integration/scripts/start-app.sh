#!/usr/bin/env bash

nohup java -jar \
/home/ec2-user/okky-comment-1.0.0.jar \
--spring.profiles.active=prod > /dev/null 2> /dev/null < /dev/null &