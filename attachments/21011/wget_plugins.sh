#!/bin/sh
JSON_DOMAIN=http://jenkins.mirror.isppower.de
JSON_FILE=update-center.json
PLUGIN_URI=$JSON_DOMAIN/plugins/
REPLACE_URI=http://jenkinspluginsupdate.mirror.lan/jenkins-update/plugins/
BASE=/srv/www/jenkins-update
PLUGINS=$BASE/plugins

if [ "$1" == "manual" ]; then echo "starte manuelles Update"; fi

# Logverzeichnis
if [ ! -d $BASE/log ]; then
  mkdir $BASE/log;
else
  rm $BASE/log/download.log
fi

# In Zielverzeichnis wechseln (wget laedt immer in das aktuelle Verzeichnis herunter)
cd $BASE
rm $JSON_FILE
wget -q $JSON_DOMAIN/updates/$JSON_FILE
sed -i "s#http://updates.jenkins-ci.org/download/plugins#http://jenkinspluginsupdate.mirror.lan/jenkins-update/plugins/#g" $JSON_FILE

# DL der Jenkin Plugins
if [ ! -d $PLUGINS ]; then mkdir -p $PLUGINS; fi
if [ ! "$1" == "manual" ]; then cd $PLUGINS; fi
echo "$(date '+%H:%M:%S') --- Starte Download ---" > $BASE/log/download.log
echo "$(date '+%H:%M:%S') --- Starte Download ---"
wget -r --timeout=5 --tries=0 -l0 -np --cut-dirs=1 -nH -A hpi -N $PLUGIN_URI -o $BASE/log/download.log
rc=$?
echo "$(date '+%H:%M:%S') --- download finished ---" >> $BASE/log/download.log
echo "$(date '+%H:%M:%S') --- download finished ---"
echo "rc_status = $rc" >> $BASE/log/download.log
echo "rc_status = $rc"
