#!/bin/bash

tables="
hca
service
patient-spatial
appointment
record
media
"

export tables

myDir=$(readlink -f $0 | xargs dirname)

for t in $tables
do
 echo "create table ./schema/$t.schema"
 ${myDir}/create-hbase-table.sh "./schema/$t.schema" ./conf/myhbase.xml
done
