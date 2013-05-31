package savi.hcat.analytics.coprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.BaseEndpointCoprocessor;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.util.Bytes;


public class HCATImpl extends BaseEndpointCoprocessor implements HCATProtocol {

	@Override
	public RStatResult getSummary(Scan scan,String condition,String unit,String starttime,String endtime) throws IOException {

		long sTime = System.currentTimeMillis();
		System.out.println(sTime+": in the getSummary....");
		/**Step1: get internalScanner***/
		InternalScanner scanner = ((RegionCoprocessorEnvironment) getEnvironment()).getRegion().getScanner(scan);
		List<KeyValue> keyvalues = new ArrayList<KeyValue>();
		RStatResult results = new RStatResult();
		results = results.setUnit(unit, starttime, endtime);
		results.setCity(Bytes.toString(scan.getStartRow()).split("-")[0]);
		
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
						System.out.println(Bytes.toString(kv.getRow())+"=>"+Bytes.toString(kv.getValue()));
						kvLength = (kvLength < kv.getLength())? kv.getLength():kvLength;
						cell_num++;
						String rowKey = Bytes.toString(kv.getRow());						
						String unitKey = results.parseUnitKey(rowKey);
						results.addOn(unitKey, Bytes.toInt(kv.getValue()));						
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
			//System.out.println("exe_time=>"+(eTime-sTime)+";result=>"+results.getRes().size()+";count=>"+count+";accepted=>"+accepted);	

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
