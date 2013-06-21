#!/bin/bash

username=root
password=zhu88jie


tablename=Service
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=Appointment
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=Schedule
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=MediaMessage
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=ServiceRecord
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=Patient
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=PersistentAddress
mysql -u$username -p$password hcaschedule -e "delete from $tablename"

tablename=HCA
mysql -u$username -p$password hcaschedule -e "delete from $tablename"
