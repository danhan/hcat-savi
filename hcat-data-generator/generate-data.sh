#!/bin/bash
# This is to preprocess the files to CSV files
#input: file format(xml,binary), input directory in HDFS/local storage, output directory
#output: CSV files are stored in output directory


# use the command to upload the data into HDFS
USAGE="<0:schedule, 1:patient location>"
if [ -z "$1" ]; then
	echo "$USAGE"
	exit -1
fi


${PWD}/../lib/commons-math3-3.0.jar
#MYCONF=${PWD}/../conf/

# jar file which is used to preprocess the files
# java heap space should be large, because it will generate large data
${JAVA_HOME}/bin/java -Xms1500m -Xmx2000m -classpath ${MYLIB}:${MYCONF} XDataGenerator $* 

