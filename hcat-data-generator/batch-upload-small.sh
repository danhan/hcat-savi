#!/bin/bash

username=root
password=zhu88jie
data_dir=/home/ubuntu/test-data

folders="
hca
patient
patientaddress
service
appointment
schedule
"

echo "********************Upload the load**********************************************"
echo "******************* $data_dir****************"
echo "*******************to MySQL******************************************************"

printf "\n"

#upload the file to mysql
# $1 : the table directory 
# S2 : the table name
function upload_one_folder(){
    for dir in $1; do
        # look for empty dir 
        if [ "$(ls -A $dir)" ]; then
            printf "\n[Folder]: $dir \n"
            for csv in "$dir"/*; do
	      current_time=$(date "+%Y.%m.%d-%H.%M.%S")
               printf "\n[File][Start][$current_time] $csv \n"
               # mysql
	       mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$csv"' INTO TABLE $2 FIELDS TERMINATED BY ','"
               printf "[File]:[end] $csv \n"
            done
        else
            printf "\n$dir is Empty\n"
        fi  
    done
}

# start to upload the small data
for f in $folders;do
    tablename=""
    if echo $f | grep "hca"; then
    	tablename="HCA";
    elif echo $f | grep "address"; then
        tablename="PersistentAddress";
    elif echo $f | grep "patient"; then
        tablename="Patient"
    elif echo $f | grep "service"; then
        tablename="Service"
    elif echo $f | grep "schedule"; then
        tablename="Schedule"
    elif echo $f | grep "appointment"; then
        tablename="Appointment"
    fi
    printf "== $tablename===="
    upload_one_folder "$data_dir/$f" $tablename
done




echo "**************************************************"
echo "**********Finish uploading small data******************"
echo "**************************************************"


