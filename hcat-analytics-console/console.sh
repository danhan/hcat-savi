#!/bin/bash

engine="localhost"
url="http://"$engine":9999/analytics"
query_start_time="2010-01-01 01:00:00"
query_end_time="2012-12-31 01:00:00"
query_one_region='bc'
query_regions='localhost,bc'


function send_request(){

    printf "===Spatial Range Request with CURL=========\n"
    result=`curl -H "Content-Type: application/json" -X POST -d "$1" $2`

    printf "\n************************************************\n"
    printf "** The result of Range query $1 \n"
    printf "*************************************************\n"
    echo $result | python -mjson.tool
    printf "\n************************************************\n"
    printf "************Finish the request*******************\n"
    printf "*************************************************\n"
}

USAGE="<the table schema desc file, e.g. xx.schema> <hbase configuration file or directory>"
if [ -z "$1" ] && [ -z "$2" ]; then
    echo "$USAGE"
fi

if [ "$1" == "1" ];then
range_query="{ 'regions':$query_one_region,'location':'-134,40', 'distance':20 }"
range_url=$url"/spatial/range"
send_request "$range_query" $range_url

elif [ "$1" == "2" ];then 
quad_query="{ 'regions':'$query_regions', 'areas':{'bc':'-95.141602,41.771312,17,16', 'localhost':'-138.955078,49.009051,22,11'} }"
quad_url=$url"/spatial/window"
send_request "$quad_query" $quad_url

elif [ "$1" == "3" ] ; then
appointment="{ 'object':'appointment', 'condition':'', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions'}"
stat_sum_url=$url"/statistics/sum"
send_request "$appointment" $stat_sum_url

elif [ "$1" == "4" ]; then
service_record="{ 'numerator': 'service', 'object': 'record', 'condition':'', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions'}"
stat_pct_url=$url"/statistics/pct"
send_request "$service_record" $stat_pct_url

fi
