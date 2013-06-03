package org.apache.sqoop.hbase;

import java.io.IOException;
import java.math.BigDecimal;
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
 * support two types of row keys and column-versions
 * row key : 1 in one column 
 *           2 concated by two columns with delimiter
 * column-version: 1 in two columns corresponding to column value and version value, 
 * e.g. 
 * Source table: key1,key2, col1,col2,value1,value2,value3,value4
 * destination table: rowkey=>key1.key2, column=>col1, version=>col2 value=JSON{value1,value2,value3,value4}
 * --hbase-row-key (key1,key2)
 * --column-family d#col1#col2
 * @author dan
 *
 */
public class TimeSeriesPutTransformer extends PutTransformer{

	  public static final Log LOG = LogFactory.getLog(
			  TimeSeriesPutTransformer.class.getName());		
	  private String[] rowkeyColumns = null;
	  private String columnFamily = null;
	  private String columnName = null;
	  private String versionName = null;
	  private char delimiter = '.';
	
	@Override
	public List<Put> getPutCommand(Map<String, Object> fields)
			throws IOException {
		
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);
		
		String rowKey = null;
		if(this.rowkeyColumns.length > 0){
			rowKey = "";
			for(int i=0;i<this.rowkeyColumns.length;i++){
				LOG.info("rowKey*********"+this.rowkeyColumns[i]);
				String val = this.getFieldValue(fields, this.rowkeyColumns[i]);
				if(null == val) return null;				
				rowKey += val;
				fields.remove(this.rowkeyColumns[i]);
				if(i < this.rowkeyColumns.length-1) 
					rowKey += this.delimiter;					
			}			
		}
		
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
					// TODO Auto-generated catch block
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
	
	private String getFieldValue(Map<String, Object> fields, String name){
		Object value = fields.get(name);
		if(null != value){
			return XUtil.toHBaseString(value);
		}
		return null;
	}

	/**
	 * This is to parse the input of the options
	 * The column Family be input as: 
	 * 1 d:col1:col2 ==> the value of col1 will be the column name in hbase, the value of col2 will be the version value in hbase
	 */
	@Override
	public void setColumnFamily(String colFamily) {
		String orig = colFamily;
		if(orig != null){
			String[] spliter = colFamily.split("#");
			if(spliter.length == 3){ // there should be three part for the column family input
				this.columnFamily = spliter[0];
				this.columnName = spliter[1];
				this.versionName = spliter[2];
			}else{
				LOG.warn("The column family setting is wrong,should be cf:c:v "+ colFamily);
			}
		}else{
			LOG.warn("The column family setting is wrong,should be cf:c:v "+ colFamily);
		}				
	}

	/**
	 * This is to parse the input of the options
	 * The row key should be input as 
	 * 1: (rowkey part1, rowkey part2)
	 * 2: rowkey
	 */	
	@Override
	public void setRowKeyColumn(String rowKeyCol) {
		if(rowKeyCol != null){
			
			if(rowKeyCol.matches("(.*)")){ // there are more than one columns responding to the row in sequence
				rowKeyCol = rowKeyCol.substring(1,rowKeyCol.length()-1);
				String[] parts = rowKeyCol.split(",");
				rowkeyColumns = new String[parts.length];
				for(int i=0;i<parts.length;i++){
					this.rowkeyColumns[i] = parts[i];
				}
			}else{ // there is only one column responding to the row
				this.rowkeyColumns = new String[]{rowKeyCol};
			}
		}else{
			LOG.warn("The row key setting is wrong,should be rowkey, or (key1,key2) "+ rowKeyCol);
		}
	}
	
	
	public String[] getRowKeyColumns(){
		return this.rowkeyColumns;
	}
	
	@Override
	public String getColumnFamily() {
		return this.columnFamily;
	}

	@Override
	public String getRowKeyColumn() {
		return null;
	}

}
