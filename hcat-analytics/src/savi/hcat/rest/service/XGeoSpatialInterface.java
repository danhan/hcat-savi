package savi.hcat.rest.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface XGeoSpatialInterface {
	
	/**
	 * A period of time, the total number of patients in NW,NE,SW,SE, across the regions
	 * @param request
	 * @return
	 */
	public JSONArray doWindowStatistics(JSONObject request);
	
	/**
	 * A period of time, a list of patients in based on given location and distance, across the regions
	 * @param request
	 * @return
	 */
	public JSONArray doRangeSearch(JSONObject request); 
	
	/**
	 * A period of time, a list of patients based on the k, across the regions
	 * @param request
	 * @return
	 */
	public JSONArray doKNNSearch(JSONObject request); 

}
