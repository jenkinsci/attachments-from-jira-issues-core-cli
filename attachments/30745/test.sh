#!/bin/bash -eux

JAVA_HOME=/usr/lib/jvm/java-6-oracle/ mvn clean install -Dmaven.test.skip=true

java -jar ./war/target/jenkins.war &

while ! wget -O /dev/null http://localhost:8080/; do 
	sleep 1
done
	

if wget   'http://localhost:8080/job/fsdfsd/5/search/?q=sdfsdfsdfsdfsdfd' -O /dev/null; then
	kill %1
	exit 0
else 
	kill %1
	exit 1
fi
