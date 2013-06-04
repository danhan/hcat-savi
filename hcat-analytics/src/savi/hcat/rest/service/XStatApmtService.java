package savi.hcat.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import savi.hcat.analytics.coprocessor.HCATProtocol;
import savi.hcat.analytics.coprocessor.RCopResult;
import savi.hcat.analytics.coprocessor.RStatResult;
import savi.hcat.common.util.XConstants;
import savi.hcat.common.util.XTimestamp;


import com.util.XTableSchema;



public class XStatApmtService extends XStatisticsService implements XStatisticsInterface{
		
	private static Log LOG = LogFactory.getLog(XStatApmtService.class);
	 
	public XStatApmtService(){
		this.tableSchema = new XTableSchema("schema/appointment.schema");
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
		FilterList fList = getScanFilterList(rowRange);// getFilter list
		// send separate queries for each city
		for(String city: cities){			
			String start = city+XConstants.ROW_KEY_DELIMETER+rowRange[0];
			String end = city+XConstants.ROW_KEY_DELIMETER+rowRange[1];
			try {
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
						final String condition = status;	
						
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

	@Override
	public JSONArray getAverage(JSONObject request) {
		// get parameters of this query
		this.decompose(request);
		//prepare the callback function
		AverageCallBack callback = new AverageCallBack(this);

		// compose the HBase RPC call
		String[] rowRange = getScanRowRange();// getRowRange		
		FilterList fList = getScanFilterList(rowRange);// getFilter list 		 
		try {
			// create the scan 
			final Scan scan = hbase.generateScan(rowRange, fList,
					new String[] { this.tableSchema.getFamilyName() }, null,					
					this.tableSchema.getMaxVersions());
			
			//send the caller 
			hbase.getHTable().coprocessorExec(HCATProtocol.class,
					scan.getStartRow(), scan.getStopRow(),
					new Batch.Call<HCATProtocol, RCopResult>() {

				public RCopResult call(HCATProtocol instance)
						throws IOException {
					final String condition = status;						
					return instance.getAverage(scan,condition,unit,start_time,end_time);
				};
			}, callback);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block			
			e1.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.response;
	}

	/**
	 * This is to get the row range based on the parameters, start-time,end-time-cities
	 * rowkey: city-20130824-hcaid-pid, columns:0,1,2,3,4,5,6, version: 0,1,2,3, value: startminutes
	 * start time 2013-08-28 12:22:22
	 * end time 2013-08-28 12:22:22
	 * 
	 * @return
	 */
	private String[] getScanRowRange(){
		LOG.info("getScanRowRange");
		String[] rowRange = new String[2];
		rowRange[0] = XTimestamp.parseDate(this.start_time);
		rowRange[1] = XTimestamp.parseDate(this.end_time)+"*"; // it means include all rows before 
		LOG.info("row range: "+rowRange[0]+"=>"+rowRange[1]);
		return rowRange;
	}

	/**
	 * 
	 * @return
	 */	
	private FilterList getScanFilterList(String[] rowRange){
		LOG.info("getScanFilterList");		
		FilterList fList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
		List<Long> timestamps = new ArrayList<Long>();
		for(int i=0;i<this.tableSchema.getMaxVersions();i++)
			timestamps.add(Long.valueOf(i));
		try {
			Filter timestampFilter = hbase.getTimeStampFilter(timestamps);
			fList.addFilter(timestampFilter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fList;
	}
	

	/**
	 * To compute the average value
	 * @author dan
	 *
	 */

	class SummaryCallBack implements Batch.Callback<RStatResult> {
		public HashMap<String, Hashtable<String,Integer>> cities = new HashMap<String,Hashtable<String,Integer>>();
		int count = 0; // the number of coprocessor
		XStatisticsService service = null;

		public SummaryCallBack(XStatisticsService s) {
			this.service = s;
				
		}

		@Override
		public void update(byte[] region, byte[] row, RStatResult result) {	
			LOG.info("SummaryCallBack: update");			
			String city = result.getCity();
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
	 * To compute the average value
	 * @author dan
	 *
	 */
	class AverageCallBack implements Batch.Callback<RCopResult> {
		RCopResult res = new RCopResult();
		int count = 0; // the number of coprocessor
		XStatisticsService service = null;

		public AverageCallBack(XStatisticsService s) {
			this.service = s;
		}

		@Override
		public void update(byte[] region, byte[] row, RCopResult result) {
			
		}
	}	
	
	
	
}
