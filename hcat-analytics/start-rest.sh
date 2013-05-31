#!/bin/bash

# use the command to start the rest server


# jar file which is used to preprocess the files


if [ ! -f "${JAVA_HOME}/bin/java" ]; then
	echo "JAVA_HOME not found."
	exit -1
fi

if [ ! -f "${HBASE_HOME}/hbase-0.94.1-security.jar" ]; then
        echo "HBASE_HOME not found, hbase-0.94.1 is needed."
        exit -1
fi

if [ ! -f "${HADOOP_HOME}/hadoop-core-1.0.3.jar" ]; then
        echo "HADOOP_HOME not found, hadoop-1.0.3 is needed."
        exit -1
fi


#${JAVA_HOME}/bin/java -Xmx2G -jar ./bin/hcat-analytics.jar

COMMONLIB=${HBASE_HOME}/lib/commons-lang-2.5.jar:\
${HBASE_HOME}/lib/commons-configuration-1.6.jar:\
${HBASE_HOME}/lib/log4j-1.2.16.jar:\
${HBASE_HOME}/lib/commons-logging-1.1.1.jar:\
${HBASE_HOME}/lib/zookeeper-3.4.3.jar:\
${HBASE_HOME}/lib/slf4j-api-1.4.3.jar:\
${HBASE_HOME}/lib/slf4j-log4j12-1.4.3.jar:\
${HBASE_HOME}/lib/protobuf-java-2.4.0a.jar:\
${PWD}/lib/hbase-hd.jar:\
${PWD}/lib/org.json.jar:\
${PWD}/lib/org.restlet.ext.json.jar:\
${PWD}/lib/org.restlet.jar

HBASELIB=${HBASE_HOME}/hbase-0.94.1-security.jar
HBASECONF=${HBASE_HOME}/conf

HADOOPLIB=${HADOOP_HOME}/hadoop-core-1.0.3.jar
HADOOPCONF=${HADOOP_HOME}/conf


MYLIB=${PWD}/bin/hcat-analytics.jar
MYCONF=${PWD}/conf/

# jar file which is used to preprocess the files

${JAVA_HOME}/bin/java -Xmx1500m -classpath ${COMMONLIB}:${HBASELIB}:${HBASECONF}:${HADOOPLIB}:${HADOOPCONF}:${MYLIB}:${MYCONF} savi.hcat.rest.XRestMain

