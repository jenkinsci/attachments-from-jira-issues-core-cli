#!/usr/bin/env bash

# Restarts Jenkins if it's HTTP 5xx.
#
# Author: nurupo <nurupo.contributions@gmail.com>
# Date: 2016-09-02
# License: MIT

MINUTES_BEFORE_RESTART=15
JENKINS_URL=https://example.com

RESPONSE=$(curl --write-out \%{http_code} --silent --output /dev/null ${JENKINS_URL}/computer/)
if [[ ${RESPONSE} == "5"* ]]
then
    echo "Jenkins is HTTP ${RESPONSE}."

    free -m

    uptime_current=$(</proc/uptime)
    uptime_current2=${uptime_current%%.*}
    minutes_current=$(( uptime_current2/60 ))

    if [ ! -f /var/run/jenkins_watchdog ]
    then
        echo "$uptime_current" > /var/run/jenkins_watchdog
    fi

    sleep 30

    uptime_previous=$(</var/run/jenkins_watchdog)
    uptime_previous2=${uptime_previous%%.*}
    minutes_previous=$(( uptime_previous2/60 ))

    diff=$(( minutes_current - minutes_previous ))

    echo "$diff minutes have passed since the last time we have tried to restart Jenkins"

    if [[ $diff -gt $MINUTES_BEFORE_RESTART ]]
    then
        echo "Restarting Jenkins..."

        echo "Jenkins last 500 log lines:"
        echo ""
        echo "================================================================================"
        echo ""
        tail -n 500 /var/log/jenkins/jenkins.log
        echo ""
        echo "================================================================================"
        echo ""
        echo "Jenkins thread dump:"
        echo ""
        echo "================================================================================"
        echo ""
        sudo -u jenkins jstack `pgrep -f "^/usr/bin/java.*/jenkins.war"`
        echo ""
        echo "================================================================================"
        echo ""
        /usr/sbin/service jenkins status
        /usr/sbin/service jenkins stop
        sleep 5
        /usr/sbin/service jenkins status
        /usr/sbin/service jenkins stop
        sleep 5
        /usr/sbin/service jenkins start
        /usr/sbin/service jenkins status
        echo "$uptime_current" > /var/run/jenkins_watchdog
    fi
fi
