#!/bin/bash
part=5
awk "NR%$part==0" $1 > "part-$part-$1"
