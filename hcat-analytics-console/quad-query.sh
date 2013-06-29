echo "===The result of Window==="

curl -H "Content-Type: application/json" -X POST -d '{ "regions":"localhost,bc", "areas":{"bc":"-95.141602,41.771312,17,16", "localhost":"-138.955078,49.009051,22,11"} }' http://localhost:9999/analytics/spatial/window

echo ""
echo "==============finished-========="
