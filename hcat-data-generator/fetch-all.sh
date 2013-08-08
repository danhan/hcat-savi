#!/bin/bash

# whether you want to fetch data from nebula cloud, or just load them remotely
# the instance internal ip: 10.0.0.47
# the public ip: 136.159.94.246
dest_dir=/home/ubuntu/test-data/
datasource=ubuntu@136.159.94.246:/home/ubuntu/hcat-small
keypair=mykeypair.pem


# this is for the small data
folders="
hca
patient
patientaddress
service
appointment
schedule
"
echo "*************************************************"
echo "** Fetch data from $datasource **"
echo "************to $dest_dir*******"

printf "\n ======Fetch the small data first=======\n"
for f in $folders;do
    file=$datasource/$f
    current_time=$(date "+%Y.%m.%d-%H.%M.%S")
    printf "\n[folder][start][$current_time] $file"
    printf "\nscp -i $keypair -r $file  $dest_dir \n"
    scp -i $keypair  -r $file  $dest_dir
    printf "\n[folder][end] $file\n"
done

# this is for the large data
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
#printf "============fetch the large data ============\n"


for f in $months; do
    file=$datasource/$f
    current_time=$(date "+%Y.%m.%d-%H.%M.%S")
    printf "\n[folder][start][$current_time] $file"
    printf "\n scp -i $keypair -r $file $dest_dir \n"
    scp -i $keypair -r $file $dest_dir
    printf "\n[folder][end] $file\n"
done


