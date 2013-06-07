package savi.hcat.rest.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.json.JSONArray;
import org.json.JSONObject;

import savi.hcat.common.util.XConstants;

import com.hbase.service.HBaseUtil;
import com.util.XTableSchema;

/**
 * This is to send call hBase client to send the request
 * @author dan
 *
 */

public class XBaseStatService extends XBaseService{
		
	private static Log LOG = LogFactory.getLog(XBaseStatService.class);
	
	protected String status = null;
	protected String unit = null;

	/**
	 * should be called first to get the instance
	 * @param objName
	 * @return
	 */
	public static XBaseStatService getInstance(String objName){		
		if(objName.equals(XConstants.POST_VALUE_APPOINTMENT)){		
			return new XStatApmtService(); 
		}else if(objName.equals(XConstants.POST_VALUE_SERVICE)){						
			return new XStatPatientService();
		}
		
		return null;
	}
	
	protected boolean decompose(JSONObject reqObj){
		System.out.println("[DEBUG]: in decompose");
		try{
			boolean common = decomposeCommonParams(reqObj);
			if(common){				
				if(reqObj.has(XConstants.POST_KEY_STATUS)){
					this.status = (String)reqObj.get(XConstants.POST_KEY_STATUS);
				}
				if(reqObj.has(XConstants.POST_KEY_UNIT)){
					this.unit = (String)reqObj.get(XConstants.POST_KEY_UNIT);
				}			
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return false;
	}
	
	
	public String getStatus() {
		return status;
	}	
	
}
