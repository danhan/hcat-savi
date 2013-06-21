package savi.hcat.rest.service.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import savi.hcat.common.util.XConstants;
import savi.hcat.rest.service.XBaseStatService;
import savi.hcat.rest.service.XStatApmtService;

public class XMySQLService extends XBaseStatService{

	private static Log LOG = LogFactory.getLog(XMySQLService.class);

    static XDBConnection dbCon;

    static public DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
            try {
                    dbCon = new XDBConnection();
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
            } catch (SQLException e) {
                    e.printStackTrace();
            }
    }

    public static void close() {
            dbCon.close();
    }
	
	
	
	/**
	 * select s.Schedule$startDate AS sweek,R_HCA as hid, R_Patient as 
	 * pid, Appointment$WEEKDAY AS wday,Appointment$block AS block, Appointment$startMinute AS startMin,
	 * Appointment$PersistentID as ID from Appointment a,Schedule s where a.R_Schedule=s.Schedule$PersistentID and 
	 * Appointment$PersistentID between 1 and 10 and s.Schedule$startDate >= CAST("2010-01-01" AS DATE) and s.Schedule$startDate <= CAST("2011-12-31" as DATE)
	 * @param condition
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public JSONArray getAppointmentbyMonth(JSONObject request){
 
		boolean decomposed = this.decompose(request);
		if(!decomposed)
			LOG.error("decompose Error!");
		
       try{
           Date startDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(this.start_time).getTime());
           Date endDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(this.end_time).getTime());
   		// join schedule and count all appointments
   		String sql = "select s.Schedule$startDate AS sweek,R_HCA as hid, R_Patient as pid, " +
   				"Appointment$block AS block," +
   				"Appointment$PersistentID as ID from Appointment a,Schedule s where a.R_Schedule=s.Schedule$PersistentID"+
   				" and s.Schedule$startDate >= ?"+
   				" and s.Schedule$startDate <= ?";
           PreparedStatement prest = dbCon.getConnection().prepareStatement(sql);
           prest.setDate(1, startDate);
           prest.setDate(2, endDate);
           Statement statement = dbCon.getConnection().createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, 
                   java.sql.ResultSet.CONCUR_READ_ONLY);//These are defaults though,I believe
           statement.setFetchSize(Integer.MIN_VALUE);
           
           
           ResultSet rs = prest.executeQuery();
    	   while(rs.next()){
    		   System.out.println(rs.getString("sweek"));
    	   }
    	         
    	   rs.close();
       }catch(Exception e){
    	   e.printStackTrace();
       }
       
       return null;
       
	}
	
	public static void main(String[] args){
		XMySQLService service = new XMySQLService();
		JSONObject obj = new JSONObject();
		try{
			obj.put(XConstants.POST_KEY_START_TIME, "2010-01-01");
			obj.put(XConstants.POST_KEY_END_TIME, "2011-12-31");
			service.getAppointmentbyMonth(obj);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
}