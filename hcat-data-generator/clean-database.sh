#!/bin/bash

username=root
password=zhu88jie

"delete all data from all tables"
tablenames="
Service
Appointment
Schedule
MediaMessage
ServiceRecord
Patient
PersistentAddress
HCA
"
export tablenames

for t in tablenames
do
echo "delete from $t"
mysql -u$username -p$password hcaschedule -e "delete from $tablename"
done

echo "Finished"
