package savi.hcat.rest.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;

import savi.hcat.analytics.coprocessor.RStatResult;
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
		}else if(objName.equals(XConstants.POST_VALUE_RECORD)){
			return new XStatRecordService(objName);
		}else if(objName.equals(XConstants.POST_VALUE_MEDIA)){
			return new XStatRecordService(objName);
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
	 * start time 201308
	 * end time 201309
	 * 
	 * @return
	 */
	protected String[] getScanRowRange(String start,String end){
		LOG.info("getScanRowRange");
		String[] rowRange = new String[2];
		rowRange[0] = start;
		rowRange[1] = end+"*"; // it means include all rows before 
		LOG.info("row range: "+rowRange[0]+"=>"+rowRange[1]);
		return rowRange;
	}

	/**
	 * 
	 * @return
	 */	
	protected FilterList getScanFilterList(String[] rowRange, String region){
		LOG.info("getScanFilterList");		
		FilterList fAll = new FilterList(FilterList.Operator.MUST_PASS_ALL);	
		try {
			String prefix = region+XConstants.ROW_KEY_DELIMETER;
/*			String identifier = null;
			if(null != this.numberator){
				if(this.numberator.equals(XConstants.POST_VALUE_SERVICE)){
					identifier="s";
				}else if(this.numberator.equals(XConstants.POST_VALUE_MEDIA)){
					identifier="m";
				}
			}			
			if(null != identifier){
				prefix += identifier+XConstants.ROW_KEY_DELIMETER;
			}*/
			LOG.info("getScanFilterList: prefix: "+prefix+";identifier=>"+";numerator=>"+this.numberator);	
			// add row filter ==> required
			Filter rowFilter = hbase.getPrefixFilter(prefix);
			fAll.addFilter(rowFilter);
			Filter rowTopFilter = hbase.getBinaryFilter(">=", prefix+rowRange[0]);
			Filter rowDownFilter = hbase.getBinaryFilter("<=", prefix+rowRange[1]);			
			fAll.addFilter(rowTopFilter);
			fAll.addFilter(rowDownFilter);			
			
			// add column filter ==> optional
			if(null != this.numberator){
				fAll.addFilter(this.buildColumnPrefixFilter());
			}else{ // temporary for appointment, this should be improved sometime
				fAll.addFilter(new ColumnPrefixFilter(Bytes.toBytes("w")));
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
	
	private FilterList buildTimestampsFilter(){
		FilterList fList = null;
		try{
			fList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
			// add timestamp filter ==> required
			ArrayList<Long> timestamps = new ArrayList<Long>();
			for(int i=0;i<this.tableSchema.getMaxVersions();i++)
				timestamps.add(Long.valueOf(i));	
			Filter timestampFilter = hbase.getTimeStampFilter(timestamps);			
			fList.addFilter(timestampFilter);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return fList;
	}
	
	private Filter buildRangeColumnFilter(){
		Filter columnFilter = null;
		try{
			columnFilter = hbase.getColumnRangeFilter(Bytes.toBytes("0"),true,Bytes.toBytes("6"),true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return columnFilter;
	}

	public String getCondition() {
		return condition;
	}
	
	
}
