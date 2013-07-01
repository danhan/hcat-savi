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
 * destination table: rowkey=>key1-key2-key3
 * --hbase-row-key json object
 * --column-family json object
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
		//System.out.println("getPutCommand: "+fields.toString());
		String colFamily = getColumnFamily();
		byte [] colFamilyBytes = Bytes.toBytes(colFamily);
		
		String rowKey = buildRowKeyValue(fields); // build the row key value
		
		if (null == rowKey) {
			// If the row-key column is null, we don't insert this row.
			LOG.warn("Could not insert row with null value for row-key column: "
					+ rowKey);
			throw new IOException("Could not insert row with null value for row-key column: "
					+ rowKey);
		}
		
		Put put = new Put(Bytes.toBytes(rowKey));
		
		try{			
			// the value of the column name is designed to be qualifer
			String qualifer = this.getColumnValue(fields);	
			String tsValue = this.getFieldValue(fields, this.version.getTsField());	
			//System.out.println("[DEBUG]: ts Value: "+this.version.getTsField()+"=>"+tsValue);
			String version = tsValue;
			if(!this.version.isStandalone()){ // this identifies whether the version dimension comes from analysis of timestamp or from individual column
				version = this.version.getVersionValue(tsValue);	
			}			
			fields.remove(this.version.getTsField());
			if(null == qualifer || null == colFamily || null == version){
				throw new IOException("family=>"+this.getColumnFamily()+"; qualifer=>"+qualifer+";version=>"+version);
			}
			
			//System.out.println("rowKey=>"+rowKey+";family=>"+this.getColumnFamily()+"; qualifer=>"+qualifer+";version=>"+version);
			JSONObject object =  new JSONObject();						
							
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
			if(null != version){
				put.add(colFamilyBytes, Bytes.toBytes(qualifer),
						Long.parseLong(version),Bytes.toBytes(object.toString()));		
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}


		return Collections.singletonList(put);
	}
	/**
	 * columnsNames has been set by HSchemaPutTransformer#setColumnFamily()
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private String getColumnValue(Map<String, Object> fields) throws Exception{
		//System.out.println("in getColumnValue()"+fields.toString());
		String result = null;
		if(null != this.columnNames && this.columnNames.size()>0){
			for(String key: this.columnNames.keySet()){
				if(fields.containsKey(key)){			
					String value = getFieldValue(fields,key);
					result = this.columnNames.get(key)+value;									
					fields.remove(key); // remove this filed because it is not needed to store into the cell value
					//System.out.println("columns=>"+key + "; value=>"+result);
					break;
				}
			}
		}else{
			//System.out.println("the column name is null or empty");
			throw new Exception("the column name is null or empty");
		}
		return result;
	}

}
