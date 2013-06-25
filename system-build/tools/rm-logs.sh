#!/bin/bash

while read node
do
  echo "remove files or directory of $1 on $node "
  ssh $node 'rm -rf $1'
done

#ssh username@domain.com 'rm /some/where/some_file.war'
