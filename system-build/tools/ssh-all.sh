#!/bin/bash

instances="
10.0.0.14
10.0.0.16
10.0.0.30
10.0.0.35
10.0.0.36
10.0.0.37
10.0.0.39
10.0.0.40
"

export instances

for i in $instances
do
   ssh $i
done 
