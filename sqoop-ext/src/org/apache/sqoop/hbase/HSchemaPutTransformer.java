package org.apache.sqoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.ualberta.ssrg.hschema.XConstants;
import ca.ualberta.ssrg.hschema.XHGridSchema;
import ca.ualberta.ssrg.hschema.XSchemaTimestamp;
import ca.ualberta.ssrg.hschema.XSchemaVersion;
import ca.ualberta.ssrg.hschema.XUtil;


public abstract class HSchemaPutTransformer extends PutTransformer{

	public static final Log LOG = LogFactory.getLog(HSchemaPutTransformer.class.getName());
	protected Hashtable<String,Object> rowkeyColumns = new Hashtable<String,Object>();    
	protected String columnFamily = null;
	protected Hashtable<String,String> columnNames = null; // <filed, prefix>
	protected XSchemaVersion version = null;

	/**
	 * This is to parse the input of the options
	 * The column Family be input as: 
	 * 1 ":" cannot be used in the command line. So "=>" for replacement
	 */ 
	/**
	 * --hbase-column-family:
{
    "family"=> "d",
    "column"=>[
        {
            "field"=>"sid",
            "prefix"=> ""
        },
        {
            "field"=>"type",
            "prefix"=> "m"
        }
    ]
}
	 */
	
	@Override
	public void setColumnFamily(String colFamily) {		
		System.out.println("setColumnFamily(): "+colFamily);
		try{
			if(colFamily != null){
				colFamily = colFamily.replace("=>", ":");
				System.out.println("after replacing: "+colFamily);
				JSONTokener tokener = new JSONTokener(colFamily);				
				JSONObject obj =  new JSONObject(tokener);
				System.out.println("JSONObject: "+ obj.toString());
				
				// for family
				if(obj.has(XConstants.EXT_FAMILY)){
					this.columnFamily = obj.getString(XConstants.EXT_FAMILY);
					System.out.println("colum Family "+ columnFamily);					
				}
				//for columns
				if(obj.has(XConstants.EXT_COLUMNS)){
					JSONArray columns= obj.getJSONArray(XConstants.EXT_COLUMNS);
					this.columnNames = new Hashtable<String,String>(); // 
					for(int i=0;i<columns.length();i++){
						JSONObject one_column = columns.getJSONObject(i);
						this.columnNames.put(one_column.getString(XConstants.EXT_KEY_FIELD), 
								one_column.getString(XConstants.EXT_COLUMNS_PREFIX));					
						System.out.println("columnNames "+ this.columnNames.toString());
					} 
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * --hbase-row-key:
	 * {
			“region”: “ab”,
			"timestamp":{"field":"ts","period":"block","format":"","interval":20,"version":{"unit":"sec","interval":1,}}
			“identifier”:”ID”,
			"spatial":{"fields":"lattd,longtd","schema":{"space":"0,0,1000,1000","indexing":"2","encoding":"1","tile":"-1","subspace":"0.01"}}// if region==””, then HGrid spatial; otherwise, using region for QT			
		}
	 */
	@Override
	public void setRowKeyColumn(String rowKeyCol) {		
		System.out.println("setRowKeyColumn(): "+rowKeyCol);
		try{
			if(rowKeyCol != null){
				JSONTokener tokener = new JSONTokener(rowKeyCol);				
				JSONObject obj =  new JSONObject(tokener);	
				if(obj.has(XConstants.EXT_ROW_KEY_REGION)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_REGION, obj.getString(XConstants.EXT_ROW_KEY_REGION));
				}
				if(obj.has(XConstants.EXT_ROW_KEY_TIMESTAMP)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_TIMESTAMP,obj.getJSONObject(XConstants.EXT_ROW_KEY_TIMESTAMP));
				}
				if(obj.has(XConstants.EXT_ROW_KEY_IDENTIFIER)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_IDENTIFIER,obj.getString(XConstants.EXT_ROW_KEY_IDENTIFIER));
				}
				
				if(obj.has(XConstants.EXT_ROW_KEY_COMBINED)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_COMBINED,obj.getString(XConstants.EXT_ROW_KEY_COMBINED));
				}	
				if(obj.has(XConstants.EXT_ROW_KEY_SPATIAL)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_SPATIAL,obj.getString(XConstants.EXT_ROW_KEY_SPATIAL));
				}								
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected String buildRegionToRowKey(Map<String, Object> fields){
		String rowKey = "";
		if(this.rowkeyColumns.size() > 0){			
			// region information
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_REGION)){
				rowKey += this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_REGION);
			}
		}
		return rowKey;
	}
	/**
	 * 
	 * get the region
	 * @param fields
	 * @return
	 */
	protected String getRegionInfo(Map<String, Object> fields){
		String region = null;
		if(this.rowkeyColumns.size() > 0){			
			// region information
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_REGION)){
				region = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_REGION));
			}
		}
		return region;
	}	
	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected String buildIdentifierToRowKey(Map<String, Object> fields){
		String rowKey = "";
		if(this.rowkeyColumns.size() > 0){			
			// many columns in MYSQL to be added in the row key
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_IDENTIFIER)){
				String field = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_IDENTIFIER));
				String val = this.getFieldValue(fields, field);
				if(null == val) return null;				
				rowKey += val;						
				fields.remove(field);						
				
				}			
			}
		return rowKey;
	}		
	
	/**
	 * 
	 *get object ID
	 * @return
	 */
	protected String getObjectIdentifier(Map<String, Object> fields){
		String objectId = null;
		if(this.rowkeyColumns.size() > 0){			 
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_IDENTIFIER)){
				String field = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_IDENTIFIER));
				String val = this.getFieldValue(fields, field);
				if(null == val) return null;				
				objectId = val;																		
				}			
			}
		return objectId;
	}		
	
	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected String buildCombinedToRowKey(Map<String, Object> fields){
		System.out.println("buildCombinedToRowKey: " );
		String rowKey = "";
		if(this.rowkeyColumns.size() > 0){
			// many columns in MYSQL to be added in the row key
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_COMBINED)){
				String identifiers = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_COMBINED));
				if(null != identifiers && !identifiers.isEmpty()){
					String[] items = identifiers.split(",");
					int len = items.length;
					for(int i=0;i<len;i++){
						String field = items[i];
						String val = this.getFieldValue(fields, field);
						if(null == val) 
							System.out.println("the value : "+field + " is Null");				
						rowKey += val;						
						fields.remove(field);
						if(i < len-1)
							rowKey += XConstants.DELIMETER_ROW_KEY;
					}
				}			
			}
		}
		System.out.println("the commbined key: "+rowKey );
		return rowKey;
	}	

	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected String buildTimestampToRowKey(Map<String, Object> fields){
		System.out.println("Entry: buildTimestampToRowKey()");
		String rowKey = "";
		if(this.rowkeyColumns.size() > 0){			
			if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_TIMESTAMP)){
				JSONObject timestampJSON = (JSONObject)this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_TIMESTAMP);
				XSchemaTimestamp tsObject = new XSchemaTimestamp(timestampJSON);
				// the version is in timestamp object
				this.version = tsObject.getVersion();				
				String tsValue = this.getFieldValue(fields, tsObject.getField());
				if(null == tsValue) 
					System.out.println("the value : "+tsObject.getField() + " is Null");	
				else{
					rowKey += tsObject.getTimestampValue(tsValue);
					if(this.version.isStandalone()){
						fields.remove(tsObject.getField());
					}					
						
				}			
			}				
		}
		System.out.println("the formated timestamp: "+rowKey);
		return rowKey;
	}	

	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected double[] getSpatialLocationValue(Map<String, Object> fields){
		double[] location = new double[]{-1,-1};
		String[] columns = this.getSpatialField();
		String v1 = this.getFieldValue(fields, columns[0]);
		location[0] = (null!=v1)?Double.valueOf(v1).doubleValue():-1; 
		String v2 = this.getFieldValue(fields, columns[1]);
		location[1] = (null!=v2)?Double.valueOf(v2).doubleValue():-1;		
		return location;
	}
	
	
	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected String[] getSpatialField(){
		System.out.println("in getSpatialFiled()");
		String[] location = null;
		if(this.rowkeyColumns.size() > 0){	
			try{
				// spatial index
				if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_SPATIAL)){
					String spatial = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_SPATIAL));
					JSONObject object = new JSONObject(spatial);
					if(object.has(XConstants.EXT_ROW_KEY_SPATIAL_FIELDS)){
						String fields = object.getString(XConstants.EXT_ROW_KEY_SPATIAL_FIELDS);
						if(null != fields && !fields.isEmpty()){					
							location = fields.split(",");					
						}					
					}

				}				
			}catch(Exception e){
				e.printStackTrace();
			}				
		}
		System.out.println("the spatial Filed is : "+ location[0]+","+location[1]);
		return location;
	}
	
	/**
	 * 
	 * get the row key value for the cell, just build the common rowkey value
	 * in each child class, they should add their own special row key value
	 * @param fields
	 * @return
	 */
	protected XHGridSchema getSpatialSchema() throws Exception{
		XHGridSchema schema = null;
		if(this.rowkeyColumns.size() > 0){	
			try{
				// spatial index
				if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_SPATIAL)){
					String spatial = String.valueOf(this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_SPATIAL));
					System.out.println("spatial string: "+spatial);
					JSONObject object = new JSONObject(spatial);
					System.out.println("spatial object: "+object.toString());
					if(object.has(XConstants.EXT_ROW_KEY_SPATIAL_SCHEMA)){
						String schemaStr = object.getString(XConstants.EXT_ROW_KEY_SPATIAL_SCHEMA);
						if(null != schemaStr && !schemaStr.isEmpty()){								
							schema = new XHGridSchema(schemaStr);				
						}					
					}else{
						throw new Exception("There is no "+XConstants.EXT_ROW_KEY_SPATIAL_SCHEMA + " in "+XConstants.EXT_ROW_KEY_SPATIAL);
					}
				}				
			}catch(Exception e){
				e.printStackTrace();
			}				
		}else{
			throw new Exception("the row key columns size is 0");
		}
		return schema;
	}	
	
	/**
	 * get the row key value for the cell 
	 * @param fields
	 * @return
	 */
	public String buildRowKeyValue(Map<String, Object> fields){
		String rowKey = "";
		String region = this.buildRegionToRowKey(fields);	
		if(!region.isEmpty()){
			rowKey += region;	
			rowKey += XConstants.DELIMETER_ROW_KEY;
		}
		String timestamp =this.buildTimestampToRowKey(fields);
		if(!timestamp.isEmpty()){
			rowKey += timestamp;		
			rowKey += XConstants.DELIMETER_ROW_KEY;	
		}
		String identifier = this.buildIdentifierToRowKey(fields);	
		if(!identifier.isEmpty()){
			rowKey += identifier;	
			rowKey += XConstants.DELIMETER_ROW_KEY;
		}
		String combined = this.buildCombinedToRowKey(fields);
		if(!combined.isEmpty()){
			rowKey += combined;	
		}		 
		return rowKey;
	}	
	
	
	public Hashtable<String,Object> getRowKeyColumns(){
		return this.rowkeyColumns;
	}
	

	@Override
	public String getRowKeyColumn() {
		return null;
	}
	
	
	public String getColumnFamily() {
		return columnFamily;
	}

	protected String getFieldValue(Map<String, Object> fields, String name){
		Object value = fields.get(name);
		if(null != value){
			return XUtil.toHBaseString(value);
		}
		return null;
	}
	
	
	
	@Override
	public abstract List<Put> getPutCommand(Map<String, Object> fields) throws IOException;
		
	
	
}
