#!/bin/bash

username=root
password=passw0rd
data_dir=/home/dan/Downloads/test-data


cd $data_dir/hca
tablename=HCA
echo "======start to upload $tablename===="
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/hca dataset"
#rm -rf $data_dir/hca


cd $data_dir/patient
tablename=Patient
echo "========start to upload $tablename======="
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/patient dataset"
#rm -rf $data_dir/patient

cd $data_dir/patientaddress
tablename=PersistentAddress
echo "=======start to upload $tablename========"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/patientaddress dataset"
#rm -rf $data_dir/patientaddress


cd $data_dir/service
tablename=Service
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/service"
#rm -rf $data_dir/serivce


cd $data_dir/schedule
tablename=Schedule
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/schedule dataset"
#rm -rf $data_dir/schedule


cd $data_dir/appointment
tablename=Appointment
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/appointment dataset"
#rm -rf $data_dir/appointment


cd $data_dir/schedule
tablename=Schedule
echo "start to upload $tablename"
for f in *.csv
do
    echo $f
    mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$f"' INTO TABLE $tablename FIELDS TERMINATED BY ','"
done
# you can uncomment it if the space is limited
#echo "delete the $data_dir/schedule dataset"
#rm -rf $data_dir/schedule

cd $data_dir/servicerecord
tablename=ServiceRecord
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

# you can uncomment it if the space is limited
#echo "delete the $data_dir/media dataset"
#rm -rf $data_dir/media


