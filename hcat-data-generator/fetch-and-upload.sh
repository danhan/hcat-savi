#!/bin/bash

username=root
password=zhu88jie
data_dir=/home/ubuntu/test-data
datasource=ubuntu@10.0.0.47:/home/ubuntu/hcat-small

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
    file=$datasource/$f
    current_time=$(date "+%Y.%m.%d-%H.%M.%S")
    printf "\n====$current_time======Start to fetch $file============\n"
    printf "\n Fetch [folder][start][$current_time] $file"
    printf "\n scp -r $file $data_dir \n"
    scp -r $file $data_dir
    printf "\n Fetch [folder][end] $file\n"
    
    current_time=$(date "+%Y.%m.%d-%H.%M.%S")
    printf "\n===$current_time==Start to Upload $file================\n"

    dir="$data_dir/$f"
    if [ "$(ls -A $dir)" ]; then
	printf "\nUpload [Folder]: $dir \n"
        for csv in "$dir"/*; do
	   current_time=$(date "+%Y.%m.%d-%H.%M.%S")
       	    printf "\n[File]:[Start][$current_time] $csv \n"
            # mysql
	    if echo $csv | grep "media" ; then
		mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$csv"' INTO TABLE "MediaMessage" FIELDS TERMINATED BY ','"
	    else
		mysql --local-infile -u$username -p$password hcaschedule -e "LOAD DATA LOCAL INFILE '"$csv"' INTO TABLE "ServiceRecord" FIELDS TERMINATED BY ','"
            fi
            printf "[File]:[end] $csv \n"
        done
    else
        printf "\n$dir is Empty\n"
    fi
    current_time=$(date "+%Y.%m.%d-%H.%M.%S")
    printf "\n===$current_time==delete the folder $dir to save space======\n"
    rm -rf $dir
done


echo "**************************************************"
echo "**********Finish uploading large data****************"
echo "**************************************************"


