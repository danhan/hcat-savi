package savi.hcat.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class XTimestamp {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	public static String parseDate(String timestamp){
/*		Date date;
		try {
			date = dateFormat.parse(timestamp);		
			
			if(month < 10){
				return "0"+month;
			}else{
				return String.valueOf(month);
			}
		} catch (ParseException e) {			
			e.printStackTrace();
		}	*/	
		return null;
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
