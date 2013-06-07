package ca.ualberta.ssrg.test;

import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.ualberta.ssrg.hschema.XConstants;
import ca.ualberta.ssrg.hschema.XHGridSchema;

public class TestGeoSpatialPutTransformer {
	protected Hashtable<String,String> rowkeyColumns = new Hashtable<String,String>();
	
	public void setRowKeyColumn(String rowKeyCol) {		
		System.out.println("setRowKeyColumn(): "+rowKeyCol);
		try{
			if(rowKeyCol != null){
				JSONTokener tokener = new JSONTokener(rowKeyCol);				
				JSONObject obj =  new JSONObject(tokener);	
				System.out.println(obj.toString());
				if(obj.has(XConstants.EXT_ROW_KEY_REGION)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_REGION, obj.getString(XConstants.EXT_ROW_KEY_REGION));
				}
				if(obj.has(XConstants.EXT_ROW_KEY_TIMESTAMP)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_TIMESTAMP,obj.getString(XConstants.EXT_ROW_KEY_TIMESTAMP));
				}
				if(obj.has(XConstants.EXT_ROW_KEY_IDENTIFIER)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_IDENTIFIER,obj.getString(XConstants.EXT_ROW_KEY_IDENTIFIER));
				}
				if(obj.has(XConstants.EXT_ROW_KEY_SPATIAL)){
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_SPATIAL,obj.getString(XConstants.EXT_ROW_KEY_SPATIAL));
				}
				System.out.println(this.rowkeyColumns.toString());
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected XHGridSchema getSpatialSchema() throws Exception{
		XHGridSchema schema = null;
		if(this.rowkeyColumns.size() > 0){	
			try{
				// spatial index
				System.out.println("here"+this.rowkeyColumns.keySet().toString());
				if(this.rowkeyColumns.containsKey(XConstants.EXT_ROW_KEY_SPATIAL)){
					String spatial = this.rowkeyColumns.get(XConstants.EXT_ROW_KEY_SPATIAL);
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
				}else{
					throw new Exception("There is no "+XConstants.EXT_ROW_KEY_SPATIAL);
				}
			}catch(Exception e){
				e.printStackTrace();
			}				
		}else{
			throw new Exception("the row key columns size is 0");
		}
		return schema;
	}	
	
	public static void main(String args[]){
		String rowkey = "{\"region\":\"ab\",\"identifier\":\"row_key\",\"spatial\":{\"fields\":\"lattd,longtd\",\"schema\":{\"space\":\"0,0,100,100\",\"indexing\":\"2\",\"encoding\":\"1\",\"tile\":\"-1\",\"subspace\":\"0.01\"}}}";
		TestGeoSpatialPutTransformer transformer = new TestGeoSpatialPutTransformer();
		transformer.setRowKeyColumn(rowkey);
		try {
			transformer.getSpatialSchema();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("", "3");
		System.out.println(map.get(""));
		
	}
}
