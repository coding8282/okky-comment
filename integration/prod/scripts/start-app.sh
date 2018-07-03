#!/usr/bin/env bash

sudo chmod +x /home/ec2-user/okky-comment-1.0.0.jar
sudo ln -sf /home/ec2-user/okky-comment-1.0.0.jar /etc/init.d/okky-comment
sudo service okky-comment start
sleep 10s