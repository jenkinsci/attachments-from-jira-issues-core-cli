#!/bin/bash

# User dropdown menu does not display on Jakarta build

JENKINS_WAR_VER=2.462-rc35026
JENKINS_WAR=jenkins-${JENKINS_WAR_VER}.war
PLUGIN_MANAGER_VER=2.13.0
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VER}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  base=https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download
  wget ${base}/${PLUGIN_MANAGER_VER}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
if [ ! -d plugins ]; then
  mkdir plugins
fi
java -jar ../$PLUGIN_MANAGER_JAR \
     --jenkins-version $JENKINS_WAR_VER \
     --plugin-download-directory plugins \
     --plugin-file plugins.txt

if [ ! -f ../$JENKINS_WAR ]; then
  base=https://get.jenkins.io/war-stable
  base=https://home.markwaite.net/~mwaite/jenkins-builds
  wget ${base}/${JENKINS_WAR_VER}/jenkins.war || \
  wget -O jenkins.war https://ci.jenkins.io/job/Core/job/jenkins/job/jakarta/76/artifact/org/jenkins-ci/main/jenkins-war/2.462-rc35026.112215163e41/jenkins-war-2.462-rc35026.112215163e41.war
  mv jenkins.war ../$JENKINS_WAR
fi

JENKINS_HOME=. java -jar ../$JENKINS_WAR
