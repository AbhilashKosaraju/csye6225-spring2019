#!/bin/bash

until [ -f /opt/tomcat/demofile.txt ]; do
	echo "waiting for ec2 instance"
	sleep 5
done
echo "ec2 instance is running in code deploy"

# start tomcat service
#sudo systemctl start tomcat.service
cd /home/centos
sudo chmod -Rf 777 apache-tomcat-9.0.16
pwd
sudo systemctl start tomcat.service
#./apache-tomcat-9.0.16/bin/startup.sh
