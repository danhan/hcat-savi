package savi.hcat.rest.service.mysql;

public class XQuery {

	
	public static String appointment="select s.Schedule$startDate AS sweek,R_HCA as hid, R_Patient as pid, " +
				"Appointment$WEEKDAY AS wday,Appointment$block AS block, Appointment$startMinute AS startMin," +
				"Appointment$PersistentID as ID from Appointment a,Schedule s where a.R_Schedule=s.Schedule$PersistentID"+
				" and s.Schedule$startDate >= ?"+
				" and s.Schedule$startDate <= ?";
}
