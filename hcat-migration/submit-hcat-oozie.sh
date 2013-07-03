#!/bin/bash
oozie_ip='10.12.7.33'

#$OOZIE_HOME/bin/oozie job -oozie http://$oozie_ip:11000/oozie -config ./src/oozie/coordinator.properties -run


/usr/bin/oozie job -oozie http://$oozie_ip:11000/oozie -config ./src/savi/coordinator.properties -run
