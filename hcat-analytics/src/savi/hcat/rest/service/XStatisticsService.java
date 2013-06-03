package savi.hcat.rest.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.hbase.client.HTable;
import org.json.JSONArray;
import org.json.JSONObject;

import savi.hcat.common.util.XConstants;

import com.hbase.service.HBaseUtil;
import com.util.XTableSchema;

/**
 * This is to send call hBase client to send the request
 * @author dan
 *
 */

public class XStatisticsService {
		
	protected String status = null;
	protected String start_time = null; // 2013-08-28 12:22:22
	protected String end_time = null; // 2013-08-28 12:22:22
	protected String unit = null;
	protected ArrayList<String> cities = new ArrayList<String>();
	
	// output
	protected JSONArray response = null; 
	
	protected HBaseUtil hbase = null;	
	protected XTableSchema tableSchema = null;	
	private String HBASE_CONF_PATH = "conf/myhbase.xml";
	
	/**
	 * should be called first to get the instance
	 * @param objName
	 * @return
	 */
	public static XStatisticsService getInstance(String objName){		
		if(objName.equals(XConstants.STAT_VALUE_APPOINTMENT)){		
			return new XStatApmtService(); 
		}else if(objName.equals(XConstants.STAT_VALUE_SERVICE)){						
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
	protected XStatisticsService setHBase() throws IOException {
		try{
			if(hbase == null){
				hbase = new HBaseUtil(HBASE_CONF_PATH);
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
		System.out.println("[DEBUG]: in decompose");
		try{
			if(reqObj != null){
				
				if(reqObj.has(XConstants.STAT_KEY_STATUS)){
					this.status = (String)reqObj.get(XConstants.STAT_KEY_STATUS);
				}
				if(reqObj.has(XConstants.STAT_KEY_START_TIME)){				
					this.start_time = (String)reqObj.get(XConstants.STAT_KEY_START_TIME);
				}
				if(reqObj.has(XConstants.STAT_KEY_END_TIME)){
					this.end_time = (String)reqObj.get(XConstants.STAT_KEY_END_TIME);
				}
				if(reqObj.has(XConstants.STAT_KEY_UNIT)){
					this.unit = (String)reqObj.get(XConstants.STAT_KEY_UNIT);
				}
				if(reqObj.has(XConstants.STAT_KEY_CITIES)){
					String cityObj = (String)reqObj.get(XConstants.STAT_KEY_CITIES);
					this.cities.add((String)cityObj.split(",")[0]);
					this.cities.add((String)cityObj.split(",")[1]);
				}				
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return false;
	}
	
	
	public JSONArray getResponse() {
		if(response==null)
			response = new JSONArray();
		return response;
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
	
	
	
}
