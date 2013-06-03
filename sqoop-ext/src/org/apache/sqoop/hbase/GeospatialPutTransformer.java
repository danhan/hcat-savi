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
/**
 * 
 * source table: key, lattd, longtd, attributes
 * hbase table: hgrid-index: columnindex-key: json{}
 * e.g.
 * --hbase-row-key (key1,key2,lattd,longtd)
 * --column-family d
 * @author dan
 *
 */

public class GeospatialPutTransformer extends PutTransformer{

	public static final Log LOG = LogFactory.getLog(
			  TimeSeriesPutTransformer.class.getName());	
	private String[] rowkeyColumns = null;
	private static String LATITUDE = "lattd";
	private static String LONGITUDE = "longtd";
	private static String OBJECTID = "ID";
	private char delimiter = '.';
	
	
	@Override
	public List<Put> getPutCommand(Map<String, Object> fields)
			throws IOException {
		//check bounding
		if(!fields.containsKey(LATITUDE)||!fields.containsKey(LONGITUDE)||!fields.containsKey(OBJECTID))
			return null;
		
		// indexing with the latitude and longitude
		String[] spatialIndex = this.createSpatailIndex(fields.get(LATITUDE), fields.get(LONGITUDE));
		if(null == spatialIndex){
			LOG.warn("Could not insert row with null value for latitude and longituted is not right");
			return null;
		}
			
		fields.remove(LATITUDE); fields.remove(LONGITUDE);
		// get row key
		String rowKey = null;
		if(null!=this.rowkeyColumns && this.rowkeyColumns.length>=0){
			rowKey = this.getRowKeyValue(fields);
			rowKey += this.delimiter;
			rowKey += spatialIndex[0];
		}
		if (null == rowKey) {
			// If the row-key column is null, we don't insert this row.
			LOG.warn("Could not insert row with null value for row-key column: "+ rowKey);
			return null;
		}
		
		Put put = new Put(Bytes.toBytes(rowKey));		
		// get column
		String objId = getObjectID(fields);
		fields.remove(OBJECTID);
		String qualifer = null;
		if(null != objId){
			qualifer = spatialIndex[1]+delimiter+objId;
		}
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(null != qualifer)
			put.add(Bytes.toBytes(this.getColumnFamily()), Bytes.toBytes(qualifer), Bytes.toBytes(object.toString()));		
		
		return Collections.singletonList(put);		
	}


	@Override
	public void setRowKeyColumn(String rowKeyCol) {
		System.out.println("[DEBUG: ]"+rowKeyCol);
		if(null != rowKeyCol && rowKeyCol.matches("(.*)") && rowKeyCol.contains(LATITUDE) 
						&& rowKeyCol.contains(LONGITUDE)){ // there is something input in the option
			rowKeyCol = rowKeyCol.substring(1,rowKeyCol.length()-1);
			String[] options = rowKeyCol.split(",");
			this.rowkeyColumns = new String[options.length-2];
			for(int i=0;i<this.rowkeyColumns.length;i++){
				this.rowkeyColumns[i] = options[i];
			}			
		}else{ // there is an error
			LOG.warn("Row key columns setting are wrong, should be (k1,k2,lattd,longtd): "+ rowKeyCol);			
		}
	}
	
	/**
	 * Get partial of row key value
	 * @param fields
	 * @return
	 */
	private String getRowKeyValue(Map<String, Object> fields){
		String rowKey = "";
		int len = this.rowkeyColumns.length;
		for(int i=0;i<len;i++){
			Object val = fields.get(this.rowkeyColumns[i]);
			if(null == val)	return null;
			rowKey += val.toString();
			if(i<len-1) 
				rowKey += delimiter;
		}
		return rowKey;
	}
	
	private String getObjectID(Map<String,Object> fields){
		Object val = fields.get(OBJECTID);
		if(null != val)
			return val.toString();
		return null;
	}
	
	
	/**
	 * TODO
	 * create the spatial index based on latitude and longitude in the record
	 * @param lattd
	 * @param longtd
	 * @return
	 */
	private String[] createSpatailIndex(Object lattd,Object longtd){
		String[] index = new String[2];
		if(null != lattd && null != longtd){
			index[0] = "01-01";
			index[1] = "01";
			return index;
		}
		return null;
	}
	
	public String[] getRowKeyColumns(){
		return this.rowkeyColumns;
	}
	
	@Override
	public String getRowKeyColumn() {
		return null;
	}

}
