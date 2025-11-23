dmg=$1

# A script to install JDK automatically from the command line given a dmg file.

tempfoo=`basename $0`
TMPFILE=`mktemp /tmp/${tempfoo}.XXXXXX` || exit 1
hdiutil verify $dmg
hdiutil mount -readonly -nobrowse -plist $dmg > $TMPFILE
vol=`grep Volumes $TMPFILE  | sed -e 's/.*>\(.*\)<\/.*/\1/'`
pkg=`ls -1 "$vol"/*.pkg`
sudo installer -pkg "$pkg" -target /
hdiutil unmount "$vol"

echo "Installed JDK with"
