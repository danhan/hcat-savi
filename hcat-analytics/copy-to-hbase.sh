#!/bin/bash

# There are someting ERROR
#sudo rm /usr/local/hbase/lib/hcat-analytics.jar
#sudo cp ./bin/hcat-analytics.jar /usr/local/hbase/lib
#ls -l /usr/local/hbase/lib/hcat-analytics.jar

../system-build/tools/distribute-file.sh ./bin/hcat-analytics.jar /usr/local/hbase/lib/ < ../system-build/tools/node
#hschema-hd.jar  org.json.jar
../system-build/tools/distribute-file.sh ./lib/hschema-hd.jar /usr/local/hbase/lib/ < ../system-build/tools/node
../system-build/tools/distribute-file.sh ./lib/org.json.jar /usr/local/hbase/lib/ < ../system-build/tools/node
