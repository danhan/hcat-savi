#!/bin/bash

jsondata="{ 'regions':'localhost','location':'-134,40', 'distance':20 }"
url="http://localhost:9999/analytics/spatial/range"

printf "===Spatial Range Request with CURL=========\n"

result=`curl -H "Content-Type: application/json" -X POST -d "$jsondata" $url`

printf "\n************************************************\n"
printf "** The result of Range query $jsondata \n"
printf "*************************************************\n"

echo ""
echo $result | python -mjson.tool
echo ""

printf "\n************************************************\n"
printf "************Finish the request*******************\n"
printf "*************************************************\n"
