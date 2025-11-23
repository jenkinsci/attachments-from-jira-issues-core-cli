#!/bin/bash

user=mwaite:mwaite

cookie_file=$(mktemp /tmp/JENKINS-70599-cookie.XXXXXX)
crumb=$(curl --cookie-jar $cookie_file -u $user -s http://localhost:8080/crumbIssuer/api/xml?xpath=concat\(//crumbRequestField,%22:%22,//crumb\))
content_type='Content-Type: application/xml'
data='<jenkins><install plugin="git@5.0.0"></install></jenkins>'

echo Calling curl now with crumb: $crumb and cookie file $cookie_file

# When called with --location, the curl call will fail with 404 because http://localhost:8080/updates is not found
curl --location --request POST -u $user --url http://localhost:8080/pluginManager/installNecessaryPlugins --cookie $cookie_file --header "$crumb" --header "$content_type" --data "$data"

rm -f $cookie_file
