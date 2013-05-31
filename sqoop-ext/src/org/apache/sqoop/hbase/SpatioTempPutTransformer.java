package org.apache.sqoop.hbase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.sqoop.hbase.PutTransformer;

/**
 * this is the data model for spatio-temporal dataset
 * @author dan
 *
 */
public class SpatioTempPutTransformer extends PutTransformer{

	  public static final Log LOG = LogFactory.getLog(
			  SpatioTempPutTransformer.class.getName());	
	  
	public List<Put> getPutCommand(Map<String, Object> fields) throws IOException{
		String rowKeyCol = getRowKeyColumn();
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);

		Object rowKey = fields.get(rowKeyCol);
		if (null == rowKey) {
			// If the row-key column is null, we don't insert this row.
			LOG.warn("Could not insert row with null value for row-key column: "
					+ rowKeyCol);
			return null;
		}

		Put put = new Put(Bytes.toBytes("HDTest-"+Math.random()));

		for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
			String colName = fieldEntry.getKey();
			if (!colName.equals(rowKeyCol)) {
				// This is a regular field, not the row key.
				// Add it if it's not null.
				Object val = fieldEntry.getValue();
				if (null != val) {
					put.add(colFamilyBytes, Bytes.toBytes("HD-Column"+Math.random()),Bytes.toBytes("HD-C-Value"));
				}
			}
		}

		return Collections.singletonList(put);
	}

}
