package savi.hcat.rest.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static XStatisticsService getInstance(String objName){
		if(objName.equals(XConstants.STAT_VALUE_APPOINTMENT)){
			return new XStatApmtService(); 
		}else if(objName.equals(XConstants.STAT_VALUE_SERVICE)){
			return new XStatPatientService();
		}
		
		return null;
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
