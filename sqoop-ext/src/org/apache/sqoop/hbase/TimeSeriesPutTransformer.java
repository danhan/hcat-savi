package org.apache.sqoop.hbase;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ualberta.ssrg.hschema.XUtil;


/**
 * support two types of row keys and column-versions
 * row key : 1 in one column 
 *           2 concated by two columns with XConstants.DELIMETER_ROW_KEY
 * column-version: 1 in two columns corresponding to column value and version value, 
 * e.g. 
 * Source table: key1,key2, col1,col2,value1,value2,value3,value4
 * destination table: rowkey=>key1.key2, column=>col1, version=>col2 value=JSON{value1,value2,value3,value4}
 * --hbase-row-key (key1,key2)
 * --column-family d#col1#col2
 * @author dan
 *
 */
public class TimeSeriesPutTransformer extends HSchemaPutTransformer{

	  public static final Log LOG = LogFactory.getLog(
			  TimeSeriesPutTransformer.class.getName());			  
	
	  //TODO need test
	@Override
	public List<Put> getPutCommand(Map<String, Object> fields)
			throws IOException {
		
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);
		
		String rowKey = buildRowKeyValue(fields); // build the row key value
		
		if (null == rowKey) {
			// If the row-key column is null, we don't insert this row.
			LOG.warn("Could not insert row with null value for row-key column: "
					+ rowKey);
			return null;
		}
		
		Put put = new Put(Bytes.toBytes(rowKey));
		// the value of the column name is designed to be qualifer
		String qualifer = this.getFieldValue(fields, this.columnName);		
		fields.remove(this.columnName);
		String ts = this.getFieldValue(fields, this.versionName);
		fields.remove(this.versionName);		
		JSONObject object = new JSONObject();
		
		// all the remaining columns are wrapped into a JSON object
		for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
			// This is a regular field, not the row key.
			// Add it if it's not null.
			Object val = fieldEntry.getValue();
			if (null != val) {
				try {
					object.put(XUtil.toHBaseString(fieldEntry.getKey()), XUtil.toHBaseString(val));
				} catch (JSONException e) {					
					e.printStackTrace();
				}
			}
		}
		if(null != ts){
			put.add(colFamilyBytes, Bytes.toBytes(qualifer),
					Long.parseLong(ts),Bytes.toBytes(object.toString()));		
		}		

		return Collections.singletonList(put);
	}
	
}
