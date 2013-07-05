#!/bin/bash
oozie_ip='10.12.7.31'

$OOZIE_HOME/bin/oozie job -oozie http://$oozie_ip:11000/oozie -config ./src/oozie/coordinator.properties -run


