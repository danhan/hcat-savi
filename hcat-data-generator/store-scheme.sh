#!/bin/bash


username=hca
password=hcadb

echo "drop database"
mysql -u$username -p$password -e "drop database hcaschedule"

echo "create database"
mysql -u$username -p$password -e "create database hcaschedule"

echo "store the schema to hcaschedule"
mysql -u$username -p$password hcaschedule < hcat-db-schema.sql
