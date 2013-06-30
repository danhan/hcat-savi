#!/bin/bash

# There are someting ERROR
sudo rm /usr/local/hbase/lib/hcat-analytics.jar
sudo cp ./bin/hcat-analytics.jar /usr/local/hbase/lib
ls -l /usr/local/hbase/lib/hcat-analytics.jar
