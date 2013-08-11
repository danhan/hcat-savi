package savi.hcat.rest.service.mysql;

public class XQuery {

	
	public static String appointment="select s.Schedule$startDate AS sweek,R_HCA as hid, R_Patient as pid, " +
				"Appointment$WEEKDAY AS wday,Appointment$block AS block, Appointment$startMinute AS startMin," +
				"Appointment$PersistentID as ID from Appointment a,Schedule s where a.R_Schedule=s.Schedule$PersistentID"+
				" and s.Schedule$startDate >= ?"+
				" and s.Schedule$startDate <= ?";
	
	public static String app_short="select s.Schedule$startDate AS sweek, " +
			"Appointment$PersistentID as ID from Appointment a,Schedule s where a.R_Schedule=s.Schedule$PersistentID"+
			" and s.Schedule$startDate >= ?"+
			" and s.Schedule$startDate <= ?";
	
	public static String app_block="select count(*) from Appointment where Appointment$block=1";
	
	public static String record = "select ID, ts, pid, hid, block, sid, status from (" +
			"select ServiceRecord$PersistentID as ID, ServiceRecord$Status as status, R_Service as sid, " +
			"ServiceRecord$timestamp as ts, Appointment$block as block,  R_Patient as pid,  " +
			"R_HCA as hid from ServiceRecord sr left join Appointment app on sr.R_Appointment = app.Appointment$PersistentID " +
			"where ServiceRecord$timestamp between ? and ? ) as short ";
}
