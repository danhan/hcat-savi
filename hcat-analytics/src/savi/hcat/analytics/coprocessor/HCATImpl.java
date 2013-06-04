package savi.hcat.analytics.coprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.BaseEndpointCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.util.Bytes;

import savi.hcat.common.util.XConstants;


public class HCATImpl extends BaseEndpointCoprocessor implements HCATProtocol {

	private static Log LOG = LogFactory.getLog(HCATImpl.class);
	
	
	@Override
	public synchronized RStatResult getSummary(Scan scan,String condition,String unit,String starttime,String endtime) throws IOException {
		
		long sTime = System.currentTimeMillis();
		LOG.info(sTime+": in the getSummary: "+scan.toJSON()+";"+condition+";"+unit+";"+starttime+";"+endtime);
		/**Step1: get internalScanner***/
		InternalScanner scanner = ((RegionCoprocessorEnvironment) getEnvironment()).getRegion().getScanner(scan);		
		
		List<KeyValue> keyvalues = new ArrayList<KeyValue>();
		RStatResult results = new RStatResult();
		results = results.setUnit(unit, starttime, endtime); // by month/week	
		LOG.info("the scan start row "+Bytes.toString(scan.getStartRow()) + "; end-row: "+Bytes.toString(scan.getStopRow()));
		LOG.info("parts: XXX: "+Bytes.toString(scan.getStartRow()).split(XConstants.ROW_KEY_DELIMETER).length);
		String c = Bytes.toString(scan.getStartRow()).split(XConstants.ROW_KEY_DELIMETER)[0];		
		results.setCity(c); // set city in order to aggregate in the client 
		LOG.info("get the city "+c);
		
		boolean hasMoreResult = false;				
		/**Step2: iterate the result from the scanner**/		
		int cell_num = 0;
		int row_num = 0;		
		int kvLength = 0;		
		try {
			do {
				hasMoreResult = scanner.next(keyvalues);
				if(keyvalues != null && keyvalues.size() > 0){	
					row_num++;					
					for(KeyValue kv:keyvalues){
						LOG.info(Bytes.toString(kv.getRow())+"=>"+Bytes.toString(kv.getValue()));
						kvLength = (kvLength < kv.getLength())? kv.getLength():kvLength;
						cell_num++;
						String rowKey = Bytes.toString(kv.getRow());						
						String unitKey = results.parseUnitKey(rowKey);
						if(condition.equals("unfinished")){
							if(Bytes.toString(kv.getValue()).equals("null"))
								results.addOn(unitKey, 1);	
						}else if(condition.equals("finished")){
							if(!Bytes.toString(kv.getValue()).equals("null"))
								results.addOn(unitKey, 1);
						}else{
							results.addOn(unitKey, 1); // there is no condition, 
						}											
					}
				}				
				keyvalues.clear();

			} while (hasMoreResult);

			long eTime = System.currentTimeMillis();

			results.setStart(sTime);
			results.setEnd(eTime);
			results.setRows(row_num);
			results.setCells(cell_num);
			results.setKvLength(kvLength);
			LOG.info("Hash Unit: "+results.getHashUnit().toString());	
			LOG.info("exe_time=>"+(eTime-sTime)+";result=>"+results.getHashUnit().size()+";cell_num=>"+cell_num+";row_num=>"+row_num);	

		} catch(Exception e){
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		return results;	

	}

	@Override
	public RCopResult getAverage(Scan scan,String condition,String unit,String starttime,String endtime) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	

}
