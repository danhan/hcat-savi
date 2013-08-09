#/bin/bash

# Prepare the image of Virtual Machine with 
#    java-6 installed. 
#    time updates
# Prepare the installation images of Hadoop, HBase, Oozie, Sqoop


# Step1: Enabling SSH User Equivalency on Cluster Nodes
# distribute all the id_rsa.pub id_rsa to each node
./distribute-file [src] [desc] < node

# add id_rsa.pub to authorized_keys file
for j in {1..8}; do ssh -i savikey.pem hadoop$j 'cat /home/ubuntu/.ssh/id_rsa.pub >> /home/ubuntu/.ssh/authorized_keys' & done;

# Till now finished to 

# Step2: Check and edit all /etc/hosts
for j in {1..8}; do scp /etc/hosts hadoop$j:/home/ubuntu; ssh hadoop$j 'sudo mv /home/ubuntu/hosts /etc/' & done;

# check  if it is right
for j in {1..8}; do ssh hadoop$j 'cat /etc/hosts' & done;

# Step3: Check the clock on all nodes 
for j in {1..8}; do ssh hadoop$j 'date' & done;
# Step4: check all java
for j in {1..8}; do ssh hadoop$j 'java -version' & done;

# Step5: Install Hadoop

for j in {1..8}; do ssh hadoop$j 'sudo mkdir -p /app/data/hadoop/tmp' & done;
sudo mkfs.ext4 /dev/vdb
sudo mount /dev/vdb /app/data/hadoop/tmp
sudo chown -R ubuntu:ubuntu /app/data/hadoop/tmp
sudo rm -rf /app/data/hadoop/tmp/*

# copy hadoop to each node
for j in {1..8}; do scp -r /home/ubuntu/download/hadoop hadoop$j:/home/ubuntu/ & done;
for j in {1..8}; do ssh hadoop$j 'sudo mv /home/ubuntu/hadoop /usr/local/; sudo chown -R ubuntu:ubuntu /usr/local/hadoop' & done;

# start hadoop dfs

# start jobtracker
/usr/local/hadoop/bin/hadoop-daemon.sh start jobtracker
for j in {1..8}; do ssh hadoop$j '/usr/local/hadoop/bin/hadoop-daemon.sh start tasktracker' & done;
# Step6: Edit .bashrc and scp to all nodes

# Step7: Install zookeeper
# edit zoo.cfg

# Step8: Install HBase
for j in {1..8}; do scp -r /home/ubuntu/download/hbase hadoop$j:/home/ubuntu/ & done;
for j in {1..8}; do ssh hadoop$j 'sudo mv /home/ubuntu/hbase /usr/local/; sudo chown -R ubuntu:ubuntu /usr/local/hbase' & done;
for j in {1..8}; do ssh hadoop$j 'rm -rf /usr/local/hbase/logs/*' & done;

# make sure each node can ssh to manager
# do it manually


# HBase Performance test 
#  time hbase org.apache.hadoop.hbase.PerformanceEvaluation  randomWrite 5 
# real	7m11.588s , user	0m5.336s sys	0m0.780s

# Hadoop Mapreduce Performance test
# time hadoop jar hadoop-*test*.jar mrbench -numRuns 10 -maps 10 -inputLines 100 -reduces 8


# Step9 Install Sqoop


# Step10 Install Oozie
#  http://www.cnblogs.com/Deron/archive/2013/06/02/3111178.html
#  Install maven, zip

# Step11 copy hadoop,hbase,zookeeper configuration into the node where ooize&sqoop is installed

# Step12 prepare the hcat db instances on the edge

# Step13 OOzie
#   install java and git, ant
# each hbase node should be ssh acceptable
