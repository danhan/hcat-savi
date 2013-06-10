package savi.hcat.rest.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.filter.FilterList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import savi.hcat.analytics.coprocessor.HCATProtocol;
import savi.hcat.analytics.coprocessor.RStatResult;
import savi.hcat.common.util.XConstants;


import com.util.XTableSchema;



public class XStatApmtService extends XBaseStatService implements XStatisticsInterface{
		
	private static Log LOG = LogFactory.getLog(XStatApmtService.class);
	 
	public XStatApmtService(){
		this.tableSchema = new XTableSchema("schema/appointment.schema"); // it is used for proving table description to query in hbase
		if(this.tableSchema == null){
			LOG.error("the table schema fails to be loaded ");
		}else{
			LOG.info("family name: "+this.tableSchema.getFamilyName()+"; version"+this.tableSchema.getMaxVersions());
		}
		try {
			this.setHBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	/**
	 * get the summary of Appointment
	 */
	@Override
	public JSONArray getSummary(JSONObject request){		
		LOG.info("in getSummary");
		// get parameters of the query
		boolean decomposed = this.decompose(request);
		if(!decomposed)
			LOG.error("decompose Error!");

		//prepare the callback function
		SummaryCallBack callback = new SummaryCallBack(this);
		
		// compose the HBase RPC call
		String[] rowRange = getScanRowRange();// getRowRange				
		// send separate queries for each city
		for(String region: regions){			
			String start = region+XConstants.ROW_KEY_DELIMETER+rowRange[0];
			String end = region+XConstants.ROW_KEY_DELIMETER+rowRange[1];
			try {
				FilterList fList = getScanFilterList(region);// getFilter list
				// create the scan 
				final Scan scan = hbase.generateScan(new String[]{start,end}, fList,
						new String[] { this.tableSchema.getFamilyName() }, 
						new String[] {"0","1","2","3","4","5","6"},					
						this.tableSchema.getMaxVersions());
				
				LOG.info("scan: "+scan.toString());
				//send the caller 
				hbase.getHTable().coprocessorExec(HCATProtocol.class,
						scan.getStartRow(), scan.getStopRow(),
						new Batch.Call<HCATProtocol, RStatResult>() {

					public RStatResult call(HCATProtocol instance)
							throws IOException {
						
						return instance.getSummary(scan,condition,unit,start_time,end_time);
					};
				}, callback);

			} catch (IOException e1) {
				// TODO Auto-generated catch block			
				e1.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO callback.cities;
		LOG.info("the returned value: "+callback.cities.toString());
		for(String key: callback.cities.keySet()){
			JSONObject one_city = new JSONObject();
			TreeMap<String,Integer> treemap = new TreeMap<String,Integer>(callback.cities.get(key));
			try {
				one_city.put("city", key);
				JSONArray values = new JSONArray();
				for(Integer v:treemap.values()){
					values.put(v);
				}
				one_city.put("values", values);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			this.response.put(one_city);
		}
		
		return this.response;
	}

	


	/**
	 * To compute the average value
	 * @author dan
	 *
	 */

	class SummaryCallBack implements Batch.Callback<RStatResult> {
		public HashMap<String, Hashtable<String,Integer>> cities = new HashMap<String,Hashtable<String,Integer>>();
		int count = 0; // the number of coprocessor
		XBaseStatService service = null;

		public SummaryCallBack(XBaseStatService s) {
			this.service = s;
				
		}

		@Override
		public void update(byte[] region, byte[] row, RStatResult result) {	
			LOG.info("SummaryCallBack: update");			
			String city = result.getRegion();
			Hashtable<String,Integer> hashUnit = result.getHashUnit();
			if(this.cities.containsKey(city)){
				Hashtable<String,Integer> temp = this.cities.get(city);
				for(String key:temp.keySet()){
					temp.put(key, temp.get(key)+hashUnit.get(key));
				}
				
			}else{
				this.cities.put(city, result.getHashUnit());
			}			
		}
	}
	

	

	/**
	 * Not decided yet
	 */
	@Override
	public JSONArray getAverage(JSONObject request) {
		return this.response;
	}
	
}
