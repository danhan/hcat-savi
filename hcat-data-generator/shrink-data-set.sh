#!/bin/bash
# $1 => percentage
# $2 => the file name 
# $3 ==> the directory 
part=$1
awk "NR%$part==0" $2 > "$3/part-$part-$2"
