#!/bin/bash

username=root
password=passw0rd
data_dir=/home/dan/Downloads/test-data

cd $data_dir/service
tablename=Service
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/appointment
tablename=Appointment
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/schedule
tablename=Schedule
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done



cd $data_dir/media
tablename=MediaMessage
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/servicerecord
tablename=ServiceRecord
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/patient
tablename=Patient
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done

cd $data_dir/hca
tablename=HCA
echo "start to upload $tablename"
for f in *.csv
do
   echo $f
   mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
