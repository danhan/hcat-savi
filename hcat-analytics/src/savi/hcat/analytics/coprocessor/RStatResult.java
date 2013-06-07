package savi.hcat.analytics.coprocessor;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import savi.hcat.common.util.XConstants;
import savi.hcat.common.util.XTimestamp;

public class RStatResult extends RCopResult{

	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(RStatResult.class);
	
	Hashtable<String,Integer> hashUnit = null; // per month, weekly, daily
	String region = null;
	String unit = null;
	
	public RStatResult(){
		hashUnit = new Hashtable<String,Integer>();
	}
	
	public RStatResult setUnit(String u,String starttime,String endtime){
		this.unit =  u;
		if(unit.equals("m")){
			this.hashUnit = XTimestamp.getHashMonth(starttime, endtime);
		}else if(unit.equals("w")){
			this.hashUnit = XTimestamp.getHashWeek(starttime, endtime);
		}
		return this;
	}
	
	public void addOn(String unitKey, int count){		
		LOG.info("addOn: "+unitKey+";"+count);
		if(hashUnit.containsKey(unitKey)){
			int num = this.hashUnit.get(unitKey).intValue();
			this.hashUnit.put(unitKey, num+count);
		}else{
			this.hashUnit.put(unitKey, count);
		}
	}		
	
	public void setRegion(String r){
		this.region = r;
	}	
	/**
	 * row key: city-20130405-hcaid-pid
	 * @param rowKey
	 * @return
	 */
	public String parseUnitKey(String rowKey){
		LOG.info("parseUnitKey: rowkey: " + rowKey);
		String result = null;
		if(this.unit.equals("m")){
			result = rowKey.split(XConstants.ROW_KEY_DELIMETER)[1].substring(0, 6);
		}else if(this.unit.equals("w")){
			result = rowKey.split(XConstants.ROW_KEY_DELIMETER)[1];
		}
		LOG.info("the parsed unit key : "+ result);
		return result;
	}

	public Hashtable<String, Integer> getHashUnit() {
		return hashUnit;
	}

	public String getRegion() {
		return region;
	}
	
	
}
