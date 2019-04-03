#!/bin/bash

# start tomcat service
#sudo systemctl stop tomcat.service
cd /home/centos
sudo chmod -Rf 777 apache-tomcat-8.5.37
pwd
./apache-tomcat-8.5.37/bin/shutdown.sh
