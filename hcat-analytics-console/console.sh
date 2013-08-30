#!/bin/bash

#location: ON=>(x:[-95,141602,-79.453125]; y: [41.771312,56.909002]); BC=>(x:[-138.95,-117.02], y:[49.00,59.99])

analytics_engine="10.12.11.12"
url="http://"$analytics_engine":9999/analytics"

# query parameters
query_start_time="2010-02-01 01:00:00"
query_end_time="2010-03-31 18:00:00"
query_one_region='bc'
query_regions='on,bc'
# incomplete,complete, refused prevented
query_service_condition='2'
# text,pictures,audio,video
query_media_condition='1'

#scope: ON=>(x:[-95,141602,-79.453125]; y: [41.771312,56.909002]); BC=>(x:[-138.95,-117.02], y:[49.00,59.99])
range_query_region='bc'
query_location='-119.367841249637,49.00'
query_distance='0.1'


function send_request(){

    printf "=== Request with CURL=========\n"
    result=`curl -H "Content-Type: application/json" -X POST -d "$1" $2`

    printf "\n************************************************\n"
    printf "** The result of query $1 \n"
    printf "*************************************************\n"
    echo $result | python -mjson.tool
    printf "\n************************************************\n"
    printf "************Finish the request*******************\n"
    printf "*************************************************\n"
}

USAGE="<1: range query; 2: windows query, 3: statistics(sum) for appointment; 4: statistics(percentage): servicerecord; 5: statistics(percentage): media>"
if [ -z "$1" ] && [ -z "$2" ]; then
    echo "$USAGE"
fi

if [ "$1" == "1" ];then
query_location1='-130.00,54.00'
query_location2='-131.00,55.00'
query_location3='-120.00,51.00'
query_location4='-119,50'
query_location5='-118,54'
query_distance1='0.2'
query_distance2='0.4'
query_distance3='0.6'
query_distance4='0.8'
query_distance5='1'
for i in {1..1};do
range_query="{ 'regions':$range_query_region,'location':'$query_location1', 'distance':'$query_distance1' }"
range_url=$url"/spatial/range"
send_request "$range_query" $range_url
done
elif [ "$1" == "2" ];then 
quad_query="{ 'regions':'$query_regions', 'areas':{'on':'-95.141602,41.771312,17,16', 'bc':'-138.955078,49.009051,22,11'} }"
quad_url=$url"/spatial/window"
send_request "$quad_query" $quad_url

elif [ "$1" == "3" ] ; then
appointment="{ 'object':'appointment', 'condition':'', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions'}"
stat_sum_url=$url"/statistics/sum"
send_request "$appointment" $stat_sum_url

elif [ "$1" == "4" ]; then
service_record="{ 'numerator': 'service', 'object': 'record', 'condition':'$query_service_condition', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions'}"
stat_pct_url=$url"/statistics/pct"
send_request "$service_record" $stat_pct_url

elif [ "$1" == "5" ]; then
media="{ 'numerator': 'media', 'object': 'media', 'condition':'$query_media_condition', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions'}"
stat_pct_url=$url"/statistics/pct"
send_request "$media" $stat_pct_url

elif [ "$1" == "41" ]; then
service_record="{ 'numerator': 'service', 'object': 'record', 'condition':'$query_service_condition', 'start-time':'$query_start_time', 'end-time':'$query_end_time', 'unit':'m', 'regions':'$query_regions','split-num':'2'}"
stat_pct_url=$url"/statistics/pct"
send_request "$service_record" $stat_pct_url
fi
