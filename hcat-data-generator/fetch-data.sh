#!/bin/bash

# whether you want to fetch data from nebula cloud, or just load them remotely
scp -i mykeypair.pem -r ubuntu@136.159.94.246:/home/ubuntu/hcat-data/*  <destination>
