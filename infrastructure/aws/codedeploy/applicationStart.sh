#!/bin/bash

# start tomcat service
#sudo systemctl start tomcat.service
cd /home/centos
sudo chmod -Rf 777 apache-tomcat-8.5.37
pwd
sudo systemctl start tomcat.service
#./apache-tomcat-8.5.37/bin/startup.sh
