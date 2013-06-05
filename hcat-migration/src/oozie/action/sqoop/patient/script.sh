#!/bin/sh


importFile=import.properties
optionFile=option-ab
hdfsJobFolder=/user/ubuntu/sqoop/jobs/patient
indexField="Service\$PersistentID"

. $importFile

# find the where condition in option file
oldStartIndex=`expr $startIndex - $chunkSize`
oldEndIndex=`expr $startIndex - 1`
oldWhere="$indexField >= "$oldStartIndex" and $indexField <= "$oldEndIndex
echo "the WHERE clause should be $oldWhere"

#calculate the new index
newStartIndex=`expr $chunkSize + $startIndex`
newEndIndex=`expr $newStartIndex - 1`
newStartIndexStr=startIndex=$newStartIndex

oldStartIndexStr=startIndex=$startIndex
chunkSizeStr=chunkSize=$chunkSize

# build the new WHERE clause
newWhere="$indexField >= "$startIndex" and $indexField <= "$newEndIndex


echo "Updating old option file: $optionFile"
# update the option file(replace the oldwhere with the new where and put it in a new file)
sed -e "s|$oldWhere|$newWhere|g" < $optionFile > "$optionFile.new"
mv "$optionFile.new" $optionFile
echo "DEBUG====option File======="
cat $optionFile
echo "DEBUG=========="


echo "Updating old import file: $importFile"
#update the import properties file
sed -i "s|$oldStartIndexStr|$newStartIndexStr|g" $importFile
echo "DEBUG=========ImportFile======"
cat $importFile
echo "DEBUG=========="


echo "Removing HDFS import file and option file "
echo "[Important]: HADOOP_PREFIX=>${HADOOP_PREFIX}"
${HADOOP_PREFIX}/bin/hadoop fs -rm $hdfsJobFolder/$importFile
${HADOOP_PREFIX}/bin/hadoop fs -rm $hdfsJobFolder/$optionFile

echo "Creating updated properties and option file"

${HADOOP_PREFIX}/bin/hadoop fs -put $importFile $hdfsJobFolder
${HADOOP_PREFIX}/bin/hadoop fs -put $optionFile $hdfsJobFolder

echo "Giving read/write permission to other users"

${HADOOP_PREFIX}/bin/hadoop fs -chmod 777 $hdfsJobFolder/$importFile
${HADOOP_PREFIX}/bin/hadoop fs -chmod 777 $hdfsJobFolder/$optionFile
