package savi.hcat.rest.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.json.JSONArray;
import org.json.JSONObject;

import savi.hcat.analytics.coprocessor.RStatResult;
import savi.hcat.common.util.XConstants;

import com.hbase.service.HBaseUtil;
import com.util.XTableSchema;

public class XBaseService {

	private static Log LOG = LogFactory.getLog(XBaseStatService.class);
	// output
	protected JSONArray response = new JSONArray(); 
	
	protected HBaseUtil hbase = null;	
	protected XTableSchema tableSchema = null;	
	private String HBASE_CONF_PATH = "conf/myhbase.xml";
	
	// common parameters included in POST body
	protected String start_time = null; // 2013-08-28 12:22:22
	protected String end_time = null; // 2013-08-28 12:22:22
	protected ArrayList<String> regions = new ArrayList<String>();
	
	
	/**
	 * should be called second to connect with HBase
	 * @param hbaseConfPath
	 * @return
	 * @throws IOException
	 */
	protected void setHBase() throws IOException {
		LOG.info("in setHBase()");
		try{
			if(hbase == null){
				hbase = new HBaseUtil(HBASE_CONF_PATH);
				HTable tableHandler = hbase.getTableHandler(this.tableSchema.getTableName());
				LOG.info("table name: "+this.tableSchema.getTableName());
				String scanCache = hbase.getHBaseConfig().get("hbase.block.cache.size");	
								
				if(scanCache != null){				
					hbase.setScanConfig(Integer.valueOf(scanCache), true);				
				}else{					
					System.out.println("Default scan cache: "+tableHandler.getScannerCaching());
				}				
			}else{
				LOG.info("HBase is not null");
			}
						
		}catch(Exception e){
			if(hbase != null)
				hbase.closeTableHandler();
			e.printStackTrace();
		}			
	}
	

	protected boolean decomposeCommonParams(JSONObject reqObj){
		System.out.println("[DEBUG]: in doComposeCommonParams");
		try{
			if(reqObj != null){
				if(reqObj.has(XConstants.POST_KEY_START_TIME)){				
					this.start_time = (String)reqObj.get(XConstants.POST_KEY_START_TIME);
				}
				if(reqObj.has(XConstants.POST_KEY_END_TIME)){
					this.end_time = (String)reqObj.get(XConstants.POST_KEY_END_TIME);
				}
				if(reqObj.has(XConstants.POST_KEY_REGIONS)){
					String cityObj = (String)reqObj.get(XConstants.POST_KEY_REGIONS);
					String[] items = cityObj.split(",");
					for(String item: items){
						this.regions.add(item);	
					}					
				}				
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public JSONObject buildRequestStat(RStatResult result){
		JSONObject reqStatJSON = new JSONObject();
		try{
			reqStatJSON.put(XConstants.REQUEST_STAT_COP_EXE_TIME, result.getEnd()-result.getStart());
			reqStatJSON.put(XConstants.REQUEST_STAT_CELLS, result.getCells());
			reqStatJSON.put(XConstants.REQUEST_STAT_ROWS, result.getRows());
			reqStatJSON.put(XConstants.REQUEST_CELL_SIZE, result.getKvLength());			
		}catch(Exception e){
			e.printStackTrace();
		}
		return reqStatJSON;		
	}
	
	
	public JSONArray getResponse() {
		if(response==null)
			response = new JSONArray();
		return response;
	}
	
	public String getStart_time() {
		return start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	
}
