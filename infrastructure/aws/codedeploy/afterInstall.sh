#!/bin/bash

sudo systemctl stop tomcat.service

sudo rm -rf /opt/tomcat/apache-tomcat-9.0.16/webapps/docs  /opt/tomcat/apache-tomcat-9.0.16/webapps/examples /opt/tomcat/apache-tomcat-9.0.16/webapps/host-manager  /opt/tomcat/apache-tomcat-9.0.16/webapps/manager /opt/tomcat/apache-tomcat-9.0.16/webapps/ROOT

sudo chown tomcat:tomcat /opt/tomcat/apache-tomcat-9.0.16/webapps/ROOT.war

# cleanup log files
sudo rm -rf /opt/tomcat/apache-tomcat-9.0.16/logs/catalina*
sudo rm -rf /opt/tomcat/apache-tomcat-9.0.16/logs/*.log
sudo rm -rf /opt/tomcat/apache-tomcat-9.0.16/logs/*.txt
