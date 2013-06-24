package savi.hcat.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XTimestamp {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Log LOG = LogFactory.getLog(XTimestamp.class);
	 
	public static String parseDate(String timestamp){
		
		//LOG.debug("in parseDate(): "+timestamp);
		String result = "";
		try {
			Date time = dateFormat.parse(timestamp);
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(time);
			 int year = cal.get(Calendar.YEAR);
			 int month = cal.get(Calendar.MONTH)+1;
			 int day = cal.get(Calendar.DAY_OF_MONTH);
			result += year;
			result += formatDigit(month);
			result += formatDigit(day);						
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		//LOG.debug("result: "+result);
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
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);			
			return formatDigit(cal.get(Calendar.MONTH));
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
		//LOG.info("getHashMonth "+ starttime+";"+endtime);
		Hashtable<String, Integer> unitHash = new Hashtable<String,Integer>();
		try {
			Date start = dateFormat.parse(starttime);
			Date end = dateFormat.parse(endtime);
			Calendar start_cal = Calendar.getInstance();
			start_cal.setTime(start);
			Calendar end_cal = Calendar.getInstance();
			end_cal.setTime(end);
			int start_year = start_cal.get(Calendar.YEAR);
			int end_year = end_cal.get(Calendar.YEAR);
			int start_month = start_cal.get(Calendar.MONTH)+1;
			int end_month = end_cal.get(Calendar.MONTH)+1;
			if(start_year == end_year){
				for(int i=start_month;i<=end_month;i++){					
					unitHash.put(String.valueOf(start_year)+formatDigit(i), 0);
				}
			}else{
				// the left of the start year
				for(int i=start_month;i<=12;i++){					
					unitHash.put(String.valueOf(start_year)+formatDigit(i), 0);
				}
				// the whole months of the middle year
				for(int i=start_year+1;i<=end_year;i++){					
					for(int j = 1;j<=12;j++){
						unitHash.put(String.valueOf(i)+formatDigit(j), 0);
					}
				}
				// the left of the end year
				for(int i=1;i<=end_month;i++){					
					unitHash.put(String.valueOf(end_year)+formatDigit(i), 0);
				}
				
			}
			
		} catch (ParseException e) {			
			e.printStackTrace();
		}	

		//LOG.info("formuated unit hash: "+unitHash.toString());
		return unitHash;
	}
	
	
	public static HashSet<String> getHashSetMonth(String starttime, String endtime){	
		//LOG.info("getHashSetMonth "+ starttime+";"+endtime);
		HashSet<String> unitSet = new HashSet<String>();
		try {
			Date start = dateFormat.parse(starttime);
			Date end = dateFormat.parse(endtime);
			Calendar start_cal = Calendar.getInstance();
			start_cal.setTime(start);
			Calendar end_cal = Calendar.getInstance();
			end_cal.setTime(end);
			int start_year = start_cal.get(Calendar.YEAR);
			int end_year = end_cal.get(Calendar.YEAR);
			int start_month = start_cal.get(Calendar.MONTH)+1;
			int end_month = end_cal.get(Calendar.MONTH)+1;			
			if(start_year == end_year){
				for(int i=start_month;i<=end_month;i++){					
					unitSet.add(String.valueOf(start_year)+formatDigit(i));
				}
			}else{
				// the left of the start year
				for(int i=start_month;i<=12;i++){					
					unitSet.add(String.valueOf(start_year)+formatDigit(i));
				}
				// the whole months of the middle year
				for(int i=start_year+1;i<=end_year;i++){
					if(i==end_year){
						// the left of the end year
						for(int j=1;j<=end_month;j++){					
							unitSet.add(String.valueOf(i)+formatDigit(j));
						}
					}else{
						for(int j = 1;j<=12;j++){
							unitSet.add(String.valueOf(i)+formatDigit(j));
						}
					}
				}

				
			}
			
		} catch (ParseException e) {			
			e.printStackTrace();
		}	

		//LOG.info("formuated unit hash: "+unitSet.toString());
		return unitSet;
	}	
	
	/**
	 * 
	 * @param start: 2013-08-28 12:22:22
	 * @param end: 2014-08-28 12:22:22
	 * @return: [{201308,?},{201309,?}]
	 */
	public static Hashtable<String,Integer> getHashWeek(String starttime, String endtime){
		//TODO
		return null;
	}	
	
	private static String formatDigit(int m){
		if(m<10)
			return "0"+m;
		else
			return String.valueOf(m);
	}
	
}
