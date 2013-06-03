package savi.hcat.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XTimestamp {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Log LOG = LogFactory.getLog(XTimestamp.class);
	 
	public static String parseDate(String timestamp){
		
		LOG.debug("in parseDate(): "+timestamp);
		String result = "";
		try {
			Date time = dateFormat.parse(timestamp);
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(time);
			 int year = cal.get(Calendar.YEAR);
			 int month = cal.get(Calendar.MONTH)+1;
			 int day = cal.get(Calendar.DAY_OF_MONTH);
			result += year;
			if(month < 10){
				result+="0"+month;
			}else{
				result+=String.valueOf(month);
			}
			if(day < 10){
				result+="0"+day;
			}else{
				result+=String.valueOf(day);
			}						
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		LOG.debug("result: "+result);
		return result;
	}
	
	public static int parseWeekDay(String timestamp){
		Date date;
		try {
			date = dateFormat.parse(timestamp);		
			return date.getDay();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String parseMonth(String timestamp){
		Date date;
		try {
			date = dateFormat.parse(timestamp);		
			int month = date.getMonth();
			if(month < 10){
				return "0"+month;
			}else{
				return String.valueOf(month);
			}
		} catch (ParseException e) {			
			e.printStackTrace();
		}		
		return null;
	}	
	/**
	 * 
	 * @param start: 2013-08-28 12:22:22
	 * @param end: 2014-08-28 12:22:22
	 * @return: [{201308,?},{201309,?}]
	 */
	public static Hashtable<String,Integer> getHashMonth(String starttime, String endtime){
		
		return null;
	}
	
	/**
	 * 
	 * @param start: 2013-08-28 12:22:22
	 * @param end: 2014-08-28 12:22:22
	 * @return: [{201308,?},{201309,?}]
	 */
	public static Hashtable<String,Integer> getHashWeek(String starttime, String endtime){
		
		return null;
	}	
	
}
