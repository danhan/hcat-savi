package savi.hcat.common.util;

public class XConstants {

	/*	"object"
	“status”: finished/unfinished
  	 “start-time”: 
  	 “end-time”:
  	 “cities”: [city1,city2]       => this is a particular attribute */
	public static final String STAT_KEY_OBJECT="object";
	public static final String STAT_KEY_STATUS="status";
	public static final String STAT_KEY_START_TIME="start-time";
	public static final String STAT_KEY_END_TIME="end-time";
	public static final String STAT_KEY_CITIES="cities"; // "c1,c2,c3"
	public static final String STAT_KEY_UNIT="unit"; // per m/w/d ==> month/week/day
	
	public static final String STAT_VALUE_APPOINTMENT="appointment";
	public static final String STAT_VALUE_SERVICE="service";
	public static final String STAT_VALUE_HCA="hca";
	public static final String STAT_VALUE_PATIENT="patient";
	
	
	/**
	 * HBase table
	 */
	public static final String TABLE_APPOINTMENT="appointment";
	public static final String TABLE_PATIENT="patient";
	
	public static final String ROW_KEY_DELIMETER = ".";
	
	
	
}
