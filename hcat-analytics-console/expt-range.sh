#!/bin/bash

#location: ON=>(x:[-95,141602,-79.453125]; y: [41.771312,56.909002]); BC=>(x:[-138.95,-117.02], y:[49.00,59.99])

analytics_engine="10.12.11.12"
url="http://"$analytics_engine":9999/analytics"

# query parameters
query_start_time="2010-01-01 01:00:00"
query_end_time="2010-01-31 18:00:00"
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

query_location1='-130.00,54.00'
query_location2='-131.00,55.00'
query_location3='-120.00,51.00'
query_location4='-119,50'
query_location5='-118,54'
query_distance1='2'
query_distance2='3'
query_distance3='4'
query_distance4='5'
query_distance5='6'
locations=($query_location1 $query_location2 $query_location3 $query_location4 $query_location5)
distances=($query_distance1 $query_distance2 $query_distance3 $query_distance4 $query_distance5)
# with the same distance for five different locations
for j in ${distances[@]};do
 echo $j
for i in ${locations[@]};do
echo $i
range_query="{ 'regions':$range_query_region,'location':'$i', 'distance':'$j' }"
range_url=$url"/spatial/range"
send_request "$range_query" $range_url
done
done
