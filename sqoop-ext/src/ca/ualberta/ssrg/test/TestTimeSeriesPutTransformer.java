package ca.ualberta.ssrg.test;

import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.ualberta.ssrg.hschema.XConstants;
import ca.ualberta.ssrg.hschema.XSchemaVersion;

public class TestTimeSeriesPutTransformer {

	protected String columnFamily = null;
	protected Hashtable<String,String> columnNames = null; // <filed, prefix>
	protected XSchemaVersion version = null;
	protected Hashtable<String,String> rowkeyColumns = new Hashtable<String,String>();
	
	public void setColumnFamily(String colFamily) {		
		System.out.println("setColumnFamily(): "+colFamily);
		try{
			if(colFamily != null){
				JSONTokener tokener = new JSONTokener(colFamily);				
				JSONObject obj =  new JSONObject(tokener);
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
					this.rowkeyColumns.put(XConstants.EXT_ROW_KEY_TIMESTAMP,obj.getString(XConstants.EXT_ROW_KEY_TIMESTAMP));
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestTimeSeriesPutTransformer timeseries = new TestTimeSeriesPutTransformer();
		String columFamily = "{\"family\":\"d\",\"columns\":[{\"field\":\"sid\",\"prefix\":\"\"},{\"field\":\"type\",\"prefix\":\"m\"}],\"version\":{\"field\":\"ts\",\"interval\":1,\"unit\":\"sec\",\"period\":\"block\"}}";
		timeseries.setColumnFamily(columFamily);
		try{
			timeseries.version.getVersionValue("Fri Apr 12 15:16:50 MDT 2013");
			System.out.println(timeseries.columnFamily);
			System.out.println(timeseries.columnNames.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String rowkey = "{\"region\":\"localhost\",\"identifier\":\"ID\",\"combined\":[\"ts\",\"pid\",\"hid\",\"block\"]}";
		timeseries.setRowKeyColumn(rowkey);
		
		
	}

}
