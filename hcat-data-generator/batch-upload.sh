#!/bin/bash

username=root
password=passw0rd
data_dir=/home/dan/Downloads/test-data

cd $data_dir/service

tablename=Service
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/appointment
tablename=Appointment
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/schedule
tablename=Schedule
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done



cd $data_dir/media
tablename=MediaMessage
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/servicerecord
tablename=ServiceRecord
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done


cd $data_dir/patient
tablename=Patient
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done

cd $data_dir/hca
tablename=HCA
for f in *.csv
do
   echo $f
   mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
