package savi.hcat.analytics.coprocessor;

import java.util.Hashtable;

import savi.hcat.common.util.XConstants;
import savi.hcat.common.util.XTimestamp;

public class RStatResult extends RCopResult{

	private static final long serialVersionUID = 1L;
	
	Hashtable<String,Integer> hashUnit = null; // per month, weekly, daily
	String city = null;
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
		if(hashUnit.contains(unitKey)){
			int num = this.hashUnit.get(unitKey).intValue();
			this.hashUnit.put(unitKey, num+count);
		}else{
			this.hashUnit.put(unitKey, count);
		}
	}		
	
	public void setCity(String c){
		this.city = c;
	}	
	/**
	 * row key: city-20130405-hcaid-pid
	 * @param rowKey
	 * @return
	 */
	public String parseUnitKey(String rowKey){
		if(this.unit.equals("m")){
			return rowKey.split(XConstants.ROW_KEY_DELIMETER)[1].substring(0, 5);
		}else if(this.unit.equals("w")){
			return rowKey.split(XConstants.ROW_KEY_DELIMETER)[1];
		}
		return null;
	}

	public Hashtable<String, Integer> getHashUnit() {
		return hashUnit;
	}

	public String getCity() {
		return city;
	}
	
	
}
