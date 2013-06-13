#!/bin/bash


echo "===The result of Range==="
curl -H "Content-Type: application/json" -X POST -d '{ "start-time":"2012-01-01 01:00:00", "regions":"ab", "end-time":"2013-01-01 01:00:00", "location":"40,40", "distance":1000 }' http://core1:9999/analytics/spatial/range

echo ""
echo "===The result of Window==="

curl -H "Content-Type: application/json" -X POST -d '{ "start-time":"2012-01-01 01:00:00", "end-time":"2013-01-01 01:00:00", "regions":"ab", "areas":["NW","NE","SW","SE"] }' http://core1:9999/analytics/spatial/window

echo ""
