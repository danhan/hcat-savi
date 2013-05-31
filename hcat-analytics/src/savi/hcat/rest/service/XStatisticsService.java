package savi.hcat.rest.service;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hbase.service.HBaseUtil;
import com.util.XTableSchema;

/**
 * This is to send call hBase client to send the request
 * @author dan
 *
 */

public class XStatisticsService {
		
	protected String status = null;
	protected String start_time = null;
	protected String end_time = null;
	protected String cities = null;
	
	
	protected HBaseUtil hbase = null;
	//protected String table_name = null;
	protected XTableSchema tableSchema = null;	
	
	/**
	 * should be called first to get the instance
	 * @param objName
	 * @return
	 */
	public XStatisticsService getInstance(String objName){		
		if(objName.equals(XConstants.STAT_VALUE_APPOINTMENT)){
			//this.table_name = XConstants.TABLE_APPOINTMENT;
			this.tableSchema = new XTableSchema("schema/appointment.schema");
			return new XStatApmtService(); 
		}else if(objName.equals(XConstants.STAT_VALUE_SERVICE)){
			//this.table_name = XConstants.TABLE_PATIENT;
			this.tableSchema = new XTableSchema("schema/patient.schema");
			return new XStatPatientService();
		}
		
		return null;
	}
	
	/**
	 * should be called second to connect with HBase
	 * @param hbaseConfPath
	 * @return
	 * @throws IOException
	 */
	public XStatisticsService connectHBase(String hbaseConfPath) throws IOException {
		try{
			if(hbase == null){
				hbase = new HBaseUtil(hbaseConfPath);
				HTable tableHandler = hbase.getTableHandler(this.tableSchema.getTableName());
				
				String scanCache = hbase.getHBaseConfig().get("hbase.block.cache.size");	
								
				if(scanCache != null){				
					hbase.setScanConfig(Integer.valueOf(scanCache), true);				
				}else{
					System.out.println("Default scan cache: "+tableHandler.getScannerCaching());
				}				
			}		
						
		}catch(Exception e){
			if(hbase != null)
				hbase.closeTableHandler();
			e.printStackTrace();
		}	
		return this;
	}	
	
	protected boolean decompose(JSONObject reqObj){
		try{
			if(reqObj != null){
				
				if(reqObj.has(XConstants.STAT_KEY_STATUS)){
					this.status = (String)reqObj.getString(XConstants.STAT_KEY_STATUS);
				}else if(reqObj.has(XConstants.STAT_KEY_START_TIME)){
					this.status = (String)reqObj.getString(XConstants.STAT_KEY_START_TIME);
				}else if(reqObj.has(XConstants.STAT_KEY_END_TIME)){
					this.status = (String)reqObj.getString(XConstants.STAT_KEY_END_TIME);
				}else if(reqObj.has(XConstants.STAT_KEY_CITIES)){
					this.status = (String)reqObj.getString(XConstants.STAT_KEY_CITIES);
				}				
				return true;
			}
		}catch(JSONException e){
			e.printStackTrace();
		}

		return false;
	}
	
	
	public String getStatus() {
		return status;
	}
	public String getStart_time() {
		return start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public String getCities() {
		return cities;
	}
	
	
	
	
	
	
}
