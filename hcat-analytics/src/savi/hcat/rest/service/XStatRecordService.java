package savi.hcat.rest.service;

import java.io.IOException;
import java.util.Hashtable;

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

/**
 * Time series service
 * @author dan
 *
 */
public class XStatRecordService  extends XBaseStatService{

	private static Log LOG = LogFactory.getLog(XStatRecordService.class);
	
	public XStatRecordService(){
		this.tableSchema = new XTableSchema("schema/record.schema"); // it is used for proving table description to query in hbase
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
	 * Get  the percentage of service in appointments (numerator / object)
	 * request
	{
 “numerator”: “service”
 “object”:  “appointment”
  "condition":"unfinished",
  "start-time":"2012-01-01 01:00:00",
  "end-time":"2012-01-01 01:00:00",
  "unit":"m",
  "regions":"bc,ab"
}
	 */
		
	public JSONArray getPercentage(JSONObject request){		
		LOG.info("in getSummary: "+request.toString());
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
						new String[] { this.tableSchema.getFamilyName() }, null,this.tableSchema.getMaxVersions());
				
				LOG.info("scan: "+scan.toString());
				//send the caller 
				hbase.getHTable().coprocessorExec(HCATProtocol.class,
						scan.getStartRow(), scan.getStopRow(),
						new Batch.Call<HCATProtocol, RStatResult>() {

					public RStatResult call(HCATProtocol instance)
							throws IOException {						
						
						return instance.getPercentage(scan,condition,unit,start_time,end_time,numberator);
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
		
		// prepare the returned
		LOG.info("the returned value: "+callback.regions.toString());
		for(String key: callback.regions.keySet()){
			RStatResult result = callback.regions.get(key);
			Hashtable<String, Hashtable<String,int[]>> unitHashArray = result.getHashUnitArray();
			JSONObject regionJSON = new JSONObject();		
			try {
				regionJSON.put("region", key);
				JSONArray values = new JSONArray();
				for(String unit: unitHashArray.keySet() ){
					JSONObject unitJSON = new JSONObject();
					Hashtable<String, int[]> numerators = unitHashArray.get(unit);
					JSONObject numJSON = new JSONObject();
					for(String one_num: numerators.keySet()){						
						numJSON.put(one_num, numerators.get(one_num));
					}
					unitJSON.put(unit, numJSON);
					values.put(unitJSON);
				}				
				regionJSON.put("values", values);
				regionJSON.put("total", result.getRows()); // total number of appointments because one row corresponds to one appointment
				// add the statistics of request
				JSONObject reqStatJSON = this.buildRequestStat(result);
				regionJSON.put("request_stat", reqStatJSON);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			this.response.put(regionJSON);
		}
		
		return this.response;
	}
	
	class SummaryCallBack implements Batch.Callback<RStatResult> {
		public Hashtable<String, RStatResult> regions = 
						new Hashtable<String, RStatResult>();
		int count = 0; // the number of coprocessor
		XBaseStatService service = null;

		public SummaryCallBack(XBaseStatService s) {
			this.service = s;
				
		}

		@Override
		public void update(byte[] region, byte[] row, RStatResult result) {	
			LOG.info("PercentageCallBack: update: "+result.toString());			
			String regionPatient = result.getRegion();			
			if(this.regions.containsKey(regionPatient)){
				RStatResult one_stat = this.regions.get(regionPatient);
				one_stat.mergeUnitHashArray(result.getHashUnitArray());
				
			}else{
				this.regions.put(regionPatient, result);
			}			
		}
	}
	
}
