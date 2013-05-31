package savi.hcat.rest.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface XStatisticsInterface {
	
	/**
	 * A period of time, the total number of appointment, across the provinces
	 * @param request
	 * @return
	 */
	public JSONArray getSummary(JSONObject request);
	
	/**
	 * A period of time, the average number of appointment per month/week, across the provinces
	 * @param request
	 * @return
	 */
	public JSONArray getAverage(JSONObject request);
	
	
}
