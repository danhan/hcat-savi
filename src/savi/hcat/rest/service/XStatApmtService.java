package savi.hcat.rest.service;

import org.json.JSONArray;
import org.json.JSONObject;

public class XStatApmtService extends XStatisticsService implements XStatisticsInterface{


	@Override
	public JSONArray getSummary(JSONObject request) {
		// get parameters of the query
		this.decompose(request);
		// compose the HBase RPC call
		
		
		
		return null;
	}

	@Override
	public JSONArray getAverage(JSONObject request) {
		// get parameters of this query
		this.decompose(request);
		// compose the HBase RPC call
		return null;
	}

}
