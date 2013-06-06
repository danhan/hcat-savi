package java.app.createhtable;

import org.json.JSONObject;

import com.hbase.service.HBaseUtil;
import com.util.XParser;


/**
 * Input: a description of your table,
 * output: a table is created in HBase 
 * @author dan
 *
 */
public class CreateHBaseTable {

	HBaseUtil hbase_service = null;
	/**
	 * @param confPath hbase customized configuration 
	 */
	public CreateHBaseTable(String confPath){
		hbase_service = new HBaseUtil(confPath);
	}
	
	
	public boolean createTable(String schemaFile){
		JSONObject table;
		try {
			table = XParser.getTableDescription(schemaFile);
			if(table != null)
				return hbase_service.createTable(table);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("jsonobject table is null");
		return false;		
	}		
		
	
	public static void main(String[] args){
		
		if(args.length<2){
			System.out.println("<tableDesscription>,<hbase Configuration>");
			return;
		}
		String tableDesc = args[0];
		String hbaseConf = args[1];
		CreateHBaseTable generation = new CreateHBaseTable(hbaseConf);
		generation.createTable(tableDesc);			
		
	}
}
