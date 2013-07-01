#!/bin/bash

rm -rf ../hcat-migration/src/oozie/sub-workflow/sqoop-ext.jar
cp ./bin/sqoop-ext.jar ../hcat-migration/src/oozie/sub-workflow/
ls -l ../hcat-migration/src/oozie/sub-workflow/sqoop-ext.jar
