package ca.ualberta.ssrg.hschema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

/**
 * "timestamp"=>{"field":"ts","period"=>"block","version":{"unit","interval":1}}
 * @author dan
 *
 */
public class XSchemaTimestamp {

	private String field = null;
	private String format = "yyyy-MM-dd HH:mm:ss";//"EEE MMM d HH:mm:ss z yyyy";	
	
	private String period = null;
	private int interval = 1; // this would be used as the coarsed timestamp, but in this case, it is no use.
	DateFormat dateFormat = new SimpleDateFormat(this.format);
	XSchemaVersion version = null;

	public XSchemaTimestamp(JSONObject obj){
		try{
			this.field = obj.getString(XConstants.EXT_KEY_FIELD);
			if(obj.has(XConstants.EXT_VERSION_INTERVAL)){
				this.interval = obj.getInt(XConstants.EXT_VERSION_INTERVAL);	
			}			
			if(obj.has(XConstants.EXT_TIMESTAMP_PERIOD_BLOCK)){
				this.period = obj.getString(XConstants.EXT_TIMESTAMP_PERIOD_BLOCK);	
			}			
			if(obj.has(XConstants.EXT_TIMESTAMP_FORMAT)){
				this.format = obj.getString(XConstants.EXT_TIMESTAMP_FORMAT);
				dateFormat = new SimpleDateFormat(this.format);
			}
			// version dimension has some common setting
			if(obj.has(XConstants.EXT_VERSION)){
				version = new XSchemaVersion(obj.getJSONObject(XConstants.EXT_VERSION),this.field,this.period,this.format);
			}						
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * get the time stamp value
	 * @param tsValue
	 * @return
	 */
	public String getTimestampValue(String tsValue){
		String ts = "";
		try{
			if(null != tsValue){
				if(null == this.period){ // use the value of field directly
					Date date = this.dateFormat.parse(tsValue);
					//TODO to deal with time zone
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH)+1;
					int day = cal.get(Calendar.DAY_OF_MONTH);
					ts += year;
					ts += formatDigit(month);
					ts += formatDigit(day);
				}else if(this.period.equals(XConstants.EXT_TIMESTAMP_PERIOD_BLOCK)){ // the base is day
					Date date = this.dateFormat.parse(tsValue);
					//TODO to deal with time zone
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH)+1;
					int day = cal.get(Calendar.DAY_OF_MONTH);
					ts += year;
					ts += formatDigit(month);
					ts += formatDigit(day);						
				}else if(this.period.equals(XConstants.EXT_TIMESTAMP_PERIOD_HOUR)){
					//TODO this is to deal with the hour
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ts;
	}
	
	private static String formatDigit(int m){
		if(m<10)
			return "0"+m;
		else
			return String.valueOf(m);
	}

	public XSchemaVersion getVersion() {
		return version;
	}
	public String getField() {
		return field;
	}
	
	
	
}
