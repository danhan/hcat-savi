#!/bin/bash

#ubuntu in Nebula, oozie in SAVI
oozie_user='ubuntu'

hadoop dfs -rmr /user/$oozie_user/hcat
#hadoop dfs -put ./src/oozie /user/$oozie_user/hcat
hadoop dfs -put ./src/savi /user/$oozie_user/hcat
hadoop dfs -ls /user/$oozie_user/hcat
