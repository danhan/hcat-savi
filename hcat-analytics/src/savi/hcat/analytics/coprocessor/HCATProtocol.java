package savi.hcat.analytics.coprocessor;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.ipc.CoprocessorProtocol;

public interface HCATProtocol extends CoprocessorProtocol {

	
	public RCopResult getSummary(Scan scan)throws IOException;
	public RCopResult getAverage(Scan scan)throws IOException;
	
	
	
}
