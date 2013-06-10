package savi.hcat.rest.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;

import savi.hcat.common.util.XConstants;
import savi.hcat.common.util.XTimestamp;

/**
 * This is to send call hBase client to send the request
 * @author dan
 *
 */

public class XBaseStatService extends XBaseService{
		
	private static Log LOG = LogFactory.getLog(XBaseStatService.class);
	
	protected String condition = null;
	protected String unit = null;
	protected String numberator = null;	

	/**
	 * should be called first to get the instance
	 * @param objName
	 * @return
	 */
	public static XBaseStatService getInstance(String objName){		
		if(objName.equals(XConstants.POST_VALUE_APPOINTMENT)){				
			return new XStatApmtService(); 
		}else if(objName.equals(XConstants.POST_VALUE_SERVICE)){						
			return new XStatPatientService();
		}else if(objName.equals(XConstants.POST_VALUE_RECORD)){
			return new XStatRecordService();
		}
		
		return null;
	}
	
	protected boolean decompose(JSONObject reqObj){
		System.out.println("[DEBUG]: in decompose");
		try{
			boolean common = decomposeCommonParams(reqObj);
			if(common){				
				if(reqObj.has(XConstants.POST_KEY_CONDITION)){
					this.condition = (String)reqObj.get(XConstants.POST_KEY_CONDITION);
				}
				if(reqObj.has(XConstants.POST_KEY_UNIT)){
					this.unit = (String)reqObj.get(XConstants.POST_KEY_UNIT);
				}	
				if(reqObj.has(XConstants.POST_KEY_NUMBERATOR)){
					this.numberator = (String)reqObj.get(XConstants.POST_KEY_NUMBERATOR);
				}
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return false;
	}
	
	
	/**
	 * This is to get the row range based on the parameters, start-time,end-time-cities
	 * rowkey: city-20130824-hcaid-pid, columns:0,1,2,3,4,5,6, version: 0,1,2,3, value: startminutes
	 * start time 2013-08-28 12:22:22
	 * end time 2013-08-28 12:22:22
	 * 
	 * @return
	 */
	protected String[] getScanRowRange(){
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
	protected FilterList getScanFilterList(String region){
		LOG.info("getScanFilterList");		
		FilterList fAll = new FilterList(FilterList.Operator.MUST_PASS_ALL);	
		try {
/*			FilterList fList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
			// add timestamp filter ==> required
			List<Long> timestamps = new ArrayList<Long>();
			for(int i=0;i<this.tableSchema.getMaxVersions();i++)
				timestamps.add(Long.valueOf(i));	
			Filter timestampFilter = hbase.getTimeStampFilter(timestamps);			
			fList.addFilter(timestampFilter);
			fAll.addFilter(fList);*/
			// add row filter ==> required
			Filter rowFilter = hbase.getPrefixFilter(region+XConstants.ROW_KEY_DELIMETER);
			fAll.addFilter(rowFilter);
			// add column filter ==> optional
			if(null != this.numberator){
				fAll.addFilter(this.buildColumnPrefixFilter());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fAll;
	}
	
	private Filter buildColumnPrefixFilter(){
		Filter filter = null;
		try{
			if(this.numberator.equals(XConstants.POST_VALUE_SERVICE)){						
				filter = new ColumnPrefixFilter(Bytes.toBytes("s"));
			}else if(this.numberator.equals(XConstants.POST_VALUE_MEDIA)){
				filter = new ColumnPrefixFilter(Bytes.toBytes("m"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return filter;
	}	
	
	public String getCondition() {
		return condition;
	}
	
	/**
	 * @TODO
	 * the scanned version can be 1 or all based on the conditions
	 * e.g. the required service in an appointment only needs to get one version, but if the Incomplete/Complete/Refused/Prevented
	 * @return
	 */
	private long getScanVersions(){
		// TODO now, it is not needed. as the status should be 0/1/2/3, corresponding to Incomplete/Complete/Refused/Prevented
		return -1;
	}
	
	
}
