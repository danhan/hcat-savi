package savi.hcat.analytics.coprocessor;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.BaseEndpointCoprocessor;


public class HCATImpl extends BaseEndpointCoprocessor implements HCATProtocol {

	@Override
	public RCopResult getSummary(Scan scan) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RCopResult getAverage(Scan scan) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
