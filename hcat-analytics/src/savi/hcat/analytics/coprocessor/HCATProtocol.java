package savi.hcat.analytics.coprocessor;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.ipc.CoprocessorProtocol;

public interface HCATProtocol extends CoprocessorProtocol {

	
	public RStatResult getSummary(Scan scan,String region, String condition,String unit,String starttime,String endtime)throws IOException;
	public RStatResult getPercentage(Scan scan,String region,String condition,String unit,String starttime,String endtime,String numerator)throws IOException;
	public RStatResult getAverage(Scan scan,String condition,String unit,String starttime,String endtime)throws IOException;
	
	
	public RStatResult doWindowQuery(Scan scan,String region, HashMap<String,Rectangle2D.Double> areas)throws IOException;	
	public RStatResult doRangeQuery(Scan scan,String region,double latitude,double longitude,double radius)throws IOException;
	
	
}
