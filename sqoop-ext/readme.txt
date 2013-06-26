
library:

1    http://apache.parentingamerica.com/avro/stable/java/

avro-mapred-1.7.4-hadoop1.jar  
avro-1.7.4.jar    


The schema should be defined in the option file in Sqoop jobs,
the schema is very important and should be consistent with the queried schema defined in hcat-analytics.
This schema is to insert the data into HBase, while the schema in hcat-analytics is to query the data from HBase.

Format should be :
--hbase-row-key
'{"region":"ab","identifier":"row_key","spatial":{"fields":"lattd,longtd","schema":{"space":"-138.95,41.77,60,19","indexing":"2","encoding":"1","tile":"-1","subspace":"0.001"}}}'

--column-family
d#d2#d3