#!/bin/bash

#./distribute-file.sh /usr/local/hadoop/conf/slaves /usr/local/hadoop/conf/ < node-list 

while read node
do

echo "distributing $1 to ${node}:$2 ..."

scp -r  $1 ${node}:$2

done
