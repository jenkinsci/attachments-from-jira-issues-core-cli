#!/bin/bash
export LC_ALL="de_DE.ISO8859-15"
export LANG="de_DE.ISO8859-15"
export DTRACE_DOF_INIT_DISABLE=1
export JAVA_HOME=/data/apps/java/jdk1.7
#export JENKINS_JAVA_OPTIONS="-d64 -Djava.awt.headless=true -Xms4096m -Xmx4096m -XX:PermSize=256m -XX:MaxPermSize=512m"
export JENKINS_JAVA_OPTIONS="-d64 -Djava.awt.headless=true -XX:PermSize=512M -XX:MaxPermSize=2048M -Xmn128M -Xms1024M -Xmx2048M"
export JENKINS_HOME=/data/apps/scmadmin/jenkins-test
ulimit -n 4096
nohup $JAVA_HOME/bin/java $JENKINS_JAVA_OPTIONS -jar /data/apps/jenkins/app/jenkins-1.609.1.war --httpPort=8082 --prefix=/ci > $JENKINS_HOME/jenkins.log 2>&1
