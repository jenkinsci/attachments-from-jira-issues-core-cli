#!/bin/sh

TIMESTAMP=$(date +%Y-%m-%d)
BASE=/srv/www/jenkins-update

if [ ! -d $BASE/log ]; then
  mkdir $BASE/log;
else
  rm $BASE/log/error.log $BASE/log/fallback.sh $BASE/log/symlink.log
fi
if [ ! -f $BASE/log/fallback.sh ]; then echo "#!/bin/sh" > $BASE/log/fallback.sh; chmod 744 $BASE/log/fallback.sh; fi
echo "---- creating symlinks $(date) ----" >> $BASE/log/symlink.log
echo "---- creating symlinks $(date) ----"
echo "---- creating symlinks $(date) ----" >> $BASE/log/error.log

# create symlinks in plugins for those in jenkins/plugins
if [ -d $BASE/jenkins/plugins/ ]; then
DIRS=$(ls $BASE/jenkins/plugins/) 
for i in ${DIRS[@]}; do
  UDIRS=$(ls $BASE/jenkins/plugins/$i)
  for j in ${UDIRS[@]}; do
    if [ ! -L $BASE/plugins/$i/$j ] && [ ! -d $BASE/plugins/$i/$j ]; then
      if [ ! -d $BASE/plugins/$i ]; then mkdir $BASE/plugins/$i; echo "INFO: $BASE/plugins/$i wurde angelegt" >> $BASE/log/error.log;  fi
      ln -s $BASE/jenkins/plugins/$i/$j $BASE/plugins/$i/$j 2>> $BASE/log/error.log
      echo "ln -s $BASE/jenkins/plugins/$i/$j $BASE/plugins/$i/$j" >> $BASE/log/symlink.log
      echo "rm $BASE/plugins/$i/$j" >> $BASE/log/fallback.sh
    else
      OLD_TIME=$(date --reference=$BASE/plugins/$i/$j/$i.hpi +%s)
      NEW_TIME=$(date --reference=$BASE/jenkins/plugins/$i/$j/$i.hpi +%s)
      if [ $NEW_TIME -gt $OLD_TIME ]; then
        rm -r $BASE/plugins/$i/$j 2>> $BASE/log/error.log
        ln -s $BASE/jenkins/plugins/$i/$j $BASE/plugins/$i/$j 2>> $BASE/log/error.log
        echo "ln -s $BASE/jenkins/plugins/$i/$j $BASE/plugins/$i/$j" >> $BASE/log/symlink.log
        echo "rm $BASE/plugins/$i/$j" >> $BASE/log/fallback.sh
      elif [ $OLD_TIME -gt $NEW_TIME ]; then
        rm -r $BASE/jenkins/plugins/$i/$j 2>> $BASE/log/error.log
        ln -s $BASE/plugins/$i/$j $BASE/jenkins/plugins/$i/$j 2>> $BASE/log/error.log
        echo "ln -s $BASE/plugins/$i/$j $BASE/jenkins/plugins/$i/$j" >> $BASE/log/symlink.log
        echo "rm $BASE/jenkins/plugins/$i/$j" >> $BASE/log/fallback.sh
      fi
    fi
  done
done
fi

# move those in BASE to plugins
DIRS=(`ls $BASE | awk '{print $NF}' | grep -v "^log$" | grep -v "^jenkins$" | grep -v "^plugins$"`)
for i in ${DIRS[@]}; do
  UDIRS=(`ls $BASE/$i`)
  for j in ${UDIRS[@]}; do
    if [ ! -L $BASE/plugins/$i/$j ] && [ ! -d $BASE/plugins/$i/$j ]; then
      if [ ! -d $BASE/plugins/$i ]; then mkdir $BASE/plugins/$i; echo "INFO: $BASE/plugins/$i wurde angelegt" >> $BASE/log/error.log;  fi
      mv $BASE/$i/$j $BASE/plugins/$i/$j 2>> $BASE/log/error.log
      echo "mv $BASE/$i/$j $BASE/plugins/$i/$j" >> $BASE/log/symlink.log
      echo "mv $BASE/plugins/$i/$j $BASE/$i/$j" >> $BASE/log/fallback.sh
    else
      OLD_TIME=$(date --reference=$BASE/plugins/$i/$j/$i.hpi +%s)
      NEW_TIME=$(date --reference=$BASE/$i/$j/$i.hpi +%s)
      if [ $NEW_TIME -gt $OLD_TIME ]; then
        rm -r $BASE/plugins/$i/$j 2>> $BASE/log/error.log
        mv $BASE/$i/$j $BASE/plugins/$i/$j 2>> $BASE/log/error.log
        echo "mv $BASE/$i/$j $BASE/plugins/$i/$j" >> $BASE/log/symlink.log
        echo "mv $BASE/plugins/$i/$j $BASE/$i/$j" >> $BASE/log/fallback.sh
      elif [ $OLD_TIME -ge $NEW_TIME ]; then
        rm -r $BASE/$i/$j
        echo "rm $BASE/$i/$j" >> $BASE/log/symlink.log
      fi
    fi
  done
done

# cleanup
DIRS=(`ls $BASE | awk '{print $NF}' | grep -v "^log$" | grep -v "^jenkins$" | grep -v "^plugins$"`)
for i in ${DIRS[@]}; do
  UDIRS=(`ls $BASE/$i`)
  if [ "${UDIRS[0]}" == "" ]; then
    rm -r $BASE/$i
    echo "rm $BASE/$i" >> $BASE/log/symlink.log
  fi
done
echo "---- creating symlinks finished $(date) ----"
echo "---- creating symlinks finished $(date) ----" >> $BASE/log/error.log
echo "rc_status = 0"
