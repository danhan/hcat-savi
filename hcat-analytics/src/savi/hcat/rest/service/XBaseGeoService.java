package savi.hcat.rest.service;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.util.hybrid.XHybridIndex;

import savi.hcat.common.util.XConstants;


public class XBaseGeoService extends XBaseService{
	
	private static Log LOG = LogFactory.getLog(XBaseGeoService.class);
	protected double latitude = 0.0;
	protected double longitude = 0.0;
	protected double radius = 0.0;
	protected int k = 0;
	protected HashMap<String,Rectangle2D.Double> areas = new HashMap<String,Rectangle2D.Double>();
	
	protected XHybridIndex hybrid = null;
	
	/**
	 * should be called first to get the instance, 
	 * for Geo spatial, temporarily it only supports Patient
	 * @param objName
	 * @return
	 */
	public static XBaseGeoService getInstance(String objName){	
		return new XGeoPatientService();
	}
	
	
	protected boolean decompose(JSONObject reqObj) {
		LOG.info("[DEBUG]: in decompose");
		try{
			// get the common parameter
			boolean common = decomposeCommonParams(reqObj);
			if(common){		
				// parameters for range
				if(reqObj.has(XConstants.POST_KEY_DISTANCE)){
					String val = reqObj.getString(XConstants.POST_KEY_DISTANCE);
					radius = (null!=val)?java.lang.Double.valueOf(val):-1;
				}
				//parameters for range
				if(reqObj.has(XConstants.POST_KEY_LOCATION)){
					String val = reqObj.getString(XConstants.POST_KEY_LOCATION);
					if(null != val){
						String items[] = val.split(",");
						latitude = (null != items[0])? java.lang.Double.valueOf(items[0]) : -1;
						longitude =  (null != items[1])? java.lang.Double.valueOf(items[1]) : -1;
					}
				}
				// for knn
				if(reqObj.has(XConstants.POST_KEY_K)){
					String val = reqObj.getString(XConstants.POST_KEY_K);
					k = (null!=val)?Integer.valueOf(val):-1;
				}
				// for windows query [x-min,y-min,width,height]
				if(reqObj.has(XConstants.POST_KEY_AREAS)){
					JSONObject val = reqObj.getJSONObject(XConstants.POST_KEY_AREAS);
					if(null != val){
						Iterator<String> iter = val.keys();
						while(iter.hasNext()){
							String key = iter.next();
							String value = val.getString(key);							
							if(null!=value){
								String[] items = value.split(",");
								Rectangle2D.Double rect = new Rectangle2D.Double(
														java.lang.Double.valueOf(items[0]),
														java.lang.Double.valueOf(items[1]),
														java.lang.Double.valueOf(items[2]),
														java.lang.Double.valueOf(items[3]));
								this.areas.put(key, rect);
							}	
						}
					}
				}				

				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return false;
	}	
	
	
}
