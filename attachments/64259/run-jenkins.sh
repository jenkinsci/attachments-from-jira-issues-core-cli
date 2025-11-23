#!/bin/bash

# Plugin Bootstrap 5 API built only for 2.496 - latest LTS is 2.492.3
#
# https://issues.jenkins.io/browse/JENKINS-75527

JENKINS_WAR_VERSION=2.492.3
JENKINS_WAR=jenkins-${JENKINS_WAR_VERSION}.war
PLUGIN_MANAGER_VERSION=2.13.2
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VERSION}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${PLUGIN_MANAGER_VERSION}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
if [ ! -d plugins ]; then
  mkdir plugins
fi
java -jar ../$PLUGIN_MANAGER_JAR --jenkins-version $JENKINS_WAR_VERSION --latest false --plugin-download-directory plugins --plugin-file plugins.txt

if [ ! -f ../$JENKINS_WAR ]; then
  wget https://get.jenkins.io/war-stable/${JENKINS_WAR_VERSION}/jenkins.war
  mv jenkins*.war ../$JENKINS_WAR
fi

# Skip the setup wizard
# https://github.com/jenkinsci/configuration-as-code-plugin/issues/38#issuecomment-494509452
echo $JENKINS_WAR_VERSION | tee jenkins.install.UpgradeWizard.state jenkins.install.InstallUtil.lastExecVersion

MY_JENKINS_URL=http://$(hostname --fqdn):8080/ JENKINS_HOME=. java -jar ../$JENKINS_WAR
