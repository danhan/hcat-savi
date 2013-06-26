#!/bin/bash

username=root
password=passw0rd
data_dir=/home/dan/Downloads/test-data

folders="
hca
patient
patientaddress
service
"

echo "********************Upload the load**********************************************"
echo "******************* $data_dir****************"
echo "*******************to MySQL******************************************************"

printf "\n"

#upload the file to mysql
# $1 : the table directory 
function upload_one_folder(){
    for dir in $1; do
        # look for empty dir 
        if [ "$(ls -A $dir)" ]; then
            printf "\n[Folder]: $dir \n"
            for csv in "$dir"/*; do
               printf "\n[File]:[Start] $csv \n"
               # mysql
               printf "[File]:[end] $csv \n"
            done
        else
            printf "\n$dir is Empty\n"
        fi  
    done
}

# start to upload the small data
for f in $folders;do
    upload_one_folder "$data_dir/$f"
done

#upload the large table incrementally by month
months="
201001
201002
201003
201004
201005
201006
201007
201008
201009
201010
201011
201012
"
for f in $months;do
    upload_one_folder "$data_dir/$f"
done



echo "**************************************************"
echo "**********Finish uploading ******************"
echo "**************************************************"


