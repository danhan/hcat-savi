package savi.hcat.rest.service;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.filter.FilterList;
import org.json.JSONArray;
import org.json.JSONObject;

import savi.hcat.analytics.coprocessor.HCATProtocol;
import savi.hcat.analytics.coprocessor.RCopResult;



public class XStatApmtService extends XStatisticsService implements XStatisticsInterface{

	private JSONArray response = null;
	
	@Override
	public JSONArray getSummary(JSONObject request) {
		// get parameters of the query
		this.decompose(request);

		//prepare the callback function
		SummaryCallBack callback = new SummaryCallBack(this);

		// compose the HBase RPC call
		String[] rowRange = getScanRowRange();// getRowRange		
		FilterList fList = getScanFilterList();// getFilter list 		 
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
															
					return instance.getSummary(scan);
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

	@Override
	public JSONArray getAverage(JSONObject request) {
		// get parameters of this query
		this.decompose(request);
		//prepare the callback function
		AverageCallBack callback = new AverageCallBack(this);

		// compose the HBase RPC call
		String[] rowRange = getScanRowRange();// getRowRange		
		FilterList fList = getScanFilterList();// getFilter list 		 
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
															
					return instance.getAverage(scan);
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
	 * 
	 * @return
	 */
	private String[] getScanRowRange(){
		String[] rowRange = new String[2];

		return rowRange;
	}

	/**
	 * 
	 * @return
	 */	
	private FilterList getScanFilterList(){
		FilterList fList = new FilterList();

		return fList;

	}


	class SummaryCallBack implements Batch.Callback<RCopResult> {
		RCopResult res = new RCopResult();
		int count = 0; // the number of coprocessor
		XStatisticsService service = null;

		public SummaryCallBack(XStatisticsService s) {
			this.service = s;
		}

		@Override
		public void update(byte[] region, byte[] row, RCopResult result) {
			
		}
	}
	
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
