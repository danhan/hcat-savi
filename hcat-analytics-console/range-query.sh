
echo "===The result of Range==="
curl -H "Content-Type: application/json" -X POST -d '{ "regions":"localhost","location":"-134,40", "distance":10 }' http://localhost:9999/analytics/spatial/range
echo ""
echo "============finished=============="
