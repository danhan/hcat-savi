package savi.hcat.rest.service;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;


import com.util.XTableSchema;

public class XStatPatientService extends XBaseStatService implements XStatisticsInterface{


	public XStatPatientService connectHBase(){
		this.tableSchema = new XTableSchema("schema/patient.schema");
		try {
			this.setHBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	
	@Override
	public JSONArray getSummary(JSONObject request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getAverage(JSONObject request) {
		// TODO Auto-generated method stub
		return null;
	}

}
