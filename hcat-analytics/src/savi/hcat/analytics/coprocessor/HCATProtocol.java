package savi.hcat.analytics.coprocessor;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.ipc.CoprocessorProtocol;

public interface HCATProtocol extends CoprocessorProtocol {

	
	public RStatResult getSummary(Scan scan,String condition,String unit,String starttime,String endtime)throws IOException;
	public RCopResult getAverage(Scan scan,String condition,String unit,String starttime,String endtime)throws IOException;
	
	
	
}
