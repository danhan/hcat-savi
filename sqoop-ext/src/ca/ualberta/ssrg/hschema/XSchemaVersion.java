package ca.ualberta.ssrg.hschema;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class XSchemaVersion implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(XSchemaVersion.class);
		
	private String tsField = null;
	private String tsformat = "yyyy-MM-dd HH:mm:ss";
	private String unit = XConstants.EXT_VERSION_UNIT_SECOND;

	private String period = XConstants.EXT_TIMESTAMP_PERIOD_BLOCK; // Period stands for the coarsed granualrity of timestamp, unit means the finest granularity of timestmap 
	private int interval = 1;
	DateFormat dateFormat = null;
	boolean standalone = false; // this means whether the version dimension comes from the same field with timestamp or not
	
	/**
	 * {"field":"ts","interval":"1","unit":"min","period":"hour"}”
	 * @param obj
	 */
	public XSchemaVersion(JSONObject obj,String field,String p,String f){
		try{
			this.tsField = field;	
			this.period = p;
			this.tsformat = f;
			if(obj.has(XConstants.EXT_VERSION_UNIT)){
				this.unit = obj.getString(XConstants.EXT_VERSION_UNIT);	
			}			
			if(obj.has(XConstants.EXT_VERSION_INTERVAL)){
				this.interval = obj.getInt(XConstants.EXT_VERSION_INTERVAL);	
			}		
			
			if(obj.has(XConstants.EXT_TIMESTAMP_PERIOD)){
				this.period = obj.getString(XConstants.EXT_TIMESTAMP_PERIOD);	
			}			
			if(obj.has(XConstants.EXT_TIMESTAMP_FORMAT)){
				this.tsformat = obj.getString(XConstants.EXT_TIMESTAMP_FORMAT);				
			}			
			if(obj.has(XConstants.EXT_KEY_FIELD)){ // if there is field in version object, it means it is standalone
				this.tsField = obj.getString(XConstants.EXT_KEY_FIELD);	
				this.standalone = true; 
			}	
			
			dateFormat = new SimpleDateFormat(this.tsformat);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * get the exact version value based on the real timestamp value and the configurations	
	 * @param tsValue : the real value of the timestamp
	 * @return
	 * @throws Exception
	 */
	//TODO to calulate the version offset based on the period
	public String getVersionValue(String tsValue) throws Exception{
		LOG.info("getVersionValue: tsValue=>"+tsValue);
		String result = null;
		try{
			if(null != tsValue){
				long offset = this.getOffsetOfPeriod(tsValue);	
				if(this.unit.equals(XConstants.EXT_VERSION_UNIT_MINUTE)){				
					result = String.valueOf(offset/60 / this.interval); // this is to get the offset 				
				}else if(this.unit.equals(XConstants.EXT_VERSION_UNIT_SECOND)){				
					result = String.valueOf(offset/this.interval);
				}	
			}else{
				System.out.println("**** timestamp value is null***");
			}
			
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		LOG.info("the version value: "+result);
		return result;
	}
	/**
	 * compute the offset(seconds) of the period which is stored in version dimension
	 * @param tsBaseValue
	 * @param tsValue
	 * @return
	 */
	private long getOffsetOfPeriod(String tsValue){		
		long offset = 0;
		try{
			if(this.period.equals(XConstants.EXT_TIMESTAMP_PERIOD_BLOCK)){
				Date date = this.dateFormat.parse(tsValue);
				//TODO to deal with time zone
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int hour_of_day = cal.get(Calendar.HOUR_OF_DAY);
				if(hour_of_day >= 8 && hour_of_day<10){
					Calendar baseCal = cal;
					baseCal.set(year, month, day, 8, 0,0);					
					Date baseDate = baseCal.getTime();					
					offset = (date.getTime() - baseDate.getTime())/1000;
				}else if(hour_of_day >=10 && hour_of_day < 12){
					Calendar baseCal = cal;
					baseCal.set(year, month, day, 10, 0,0);
					Date baseDate = baseCal.getTime();
					System.out.println(baseDate.toString());
					offset = (date.getTime() - baseDate.getTime())/1000;
				}else if(hour_of_day >=13 && hour_of_day < 15){
					Calendar baseCal = cal;
					baseCal.set(year, month, day, 13, 0,0);
					Date baseDate = baseCal.getTime();
					offset = (date.getTime() - baseDate.getTime())/1000;
				}else if(hour_of_day >=15 && hour_of_day < 17){
					Calendar baseCal = cal;
					baseCal.set(year, month, day, 15, 0,0);
					Date baseDate = baseCal.getTime();
					offset = (date.getTime() - baseDate.getTime())/1000;
				}else{
					throw new Exception ("the timestamp is not valid becaue out of work time: "+tsValue);
				}
				
			}else if(this.period.equals(XConstants.EXT_TIMESTAMP_PERIOD_HOUR)){
				//TODO this is to deal with the hour
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("ts value=>"+tsValue+"; the second offset is "+offset);
		//LOG.info("the second offset is "+offset);
		return offset;
	}
	
	public String toString(){
		String output = "tsField=>"+this.tsField;
		output += ";tsFormat=>"+this.tsformat;
		output += ";unit=>"+this.unit;
		output += ";period=>"+this.period;
		output += ";interval=>"+this.interval;
		return output;
	}

	public void setTsformat(String tsformat) {
		this.tsformat = tsformat;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getTsformat() {
		return tsformat;
	}
	public String getUnit() {
		return unit;
	}
	public int getInterval() {
		return interval;
	}
	public String getTsField() {
		return tsField;
	}
	public void setTsField(String tsField) {
		this.tsField = tsField;
	}
	public boolean isStandalone() {
		return standalone;
	}
	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}
	
	
	
}
