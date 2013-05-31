package savi.hcat.rest.service;

import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.filter.FilterList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.util.XTableSchema;

import savi.hcat.analytics.coprocessor.HCATProtocol;
import savi.hcat.analytics.coprocessor.RCopResult;
import savi.hcat.analytics.coprocessor.RStatResult;
import savi.hcat.common.util.XTimestamp;



public class XStatApmtService extends XStatisticsService implements XStatisticsInterface{

	private JSONArray response = null;
	
	public XStatApmtService connectHBase(){
		this.tableSchema = new XTableSchema("schema/appointment.schema");
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
		// get parameters of the query
		this.decompose(request);

		//prepare the callback function
		SummaryCallBack callback = new SummaryCallBack(this);
		
		// compose the HBase RPC call
		String[] rowRange = getScanRowRange();// getRowRange		
		FilterList fList = getScanFilterList(rowRange);// getFilter list
		// send separate queries for each city
		for(String city: cities){			
			rowRange[0] = city+"-"+rowRange[0];
			rowRange[1] = city+"-"+rowRange[1];
			try {
				// create the scan 
				final Scan scan = hbase.generateScan(rowRange, fList,
						new String[] { this.tableSchema.getFamilyName() }, null,					
						this.tableSchema.getMaxVersions());

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
		String[] rowRange = new String[2];
		rowRange[0] = XTimestamp.parseDate(this.start_time);
		rowRange[1] = XTimestamp.parseDate(this.end_time)+"*"; // it means include all rows before 
		return rowRange;
	}

	/**
	 * 
	 * @return
	 */	
	private FilterList getScanFilterList(String[] rowRange){
		FilterList fList = null;
		return fList;
	}
	
	
	
	/**
	 * To compute the average value
	 * @author dan
	 *
	 */

	class SummaryCallBack implements Batch.Callback<RStatResult> {
		RStatResult res = new RStatResult();
		int count = 0; // the number of coprocessor
		XStatisticsService service = null;

		public SummaryCallBack(XStatisticsService s) {
			this.service = s;
		}

		@Override
		public void update(byte[] region, byte[] row, RStatResult result) {			
			Set<String> keys = result.getHashUnit().keySet();
			
			for(String key:keys){
				System.out.println(key + "=>"+result.getHashUnit().get(key));
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
