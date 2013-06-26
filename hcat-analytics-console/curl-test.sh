#!/bin/bash


echo "===The result of Range==="
curl -H "Content-Type: application/json" -X POST -d '{ "start-time":"2012-01-01 01:00:00", "regions":"localhost", "end-time":"2013-01-01 01:00:00", "location":"-134,40", "distance":2 }' http://localhost:9999/analytics/spatial/range

echo ""
echo "===The result of Window==="

curl -H "Content-Type: application/json" -X POST -d '{ "start-time":"2012-01-01 01:00:00", "end-time":"2013-01-01 01:00:00", "regions":"localhost", "areas":["NW","NE","SW","SE"] }' http://localhost:9999/analytics/spatial/window

echo ""
echo "===The result of ServicePercentage==="

curl -H "Content-Type: application/json" -X POST -d '{ "numerator": "service", "object": "record", "condition":"", "start-time":"2013-01-01 01:00:00", "end-time":"2014-01-01 01:00:00", "unit":"m", "regions":"localhost" }' http://localhost:9999/analytics/statistics/pct



echo ""
echo "===The result of Media Percentage==="

curl -H "Content-Type: application/json" -X POST -d '{ "numerator": "media", "object": "record", "condition":"", "start-time":"2013-01-01 01:00:00", "end-time":"2014-01-01 01:00:00", "unit":"m", "regions":"localhost" }' http://localhost:9999/analytics/statistics/pct



echo ""
echo "===The result of Appointment==="

curl -H "Content-Type: application/json" -X POST -d '{ "object":"appointment", "condition":"", "start-time":"2010-01-01 01:00:00", "end-time":"2012-12-31 01:00:00", "unit":"m", "regions":"localhost" }' http://localhost:9999/analytics/statistics/sum


