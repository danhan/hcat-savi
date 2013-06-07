package savi.hcat.rest;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import savi.hcat.common.util.XConstants;
import savi.hcat.rest.service.XBaseGeoService;
import savi.hcat.rest.service.XGeoPatientService;

public class XGeoSpatialResource extends XBaseResource{
	private static Log LOG = LogFactory.getLog(XGeoSpatialResource.class);	
	
	@Post	
	public Representation doService(Representation entity) throws IOException {
		LOG.info("in doService() ");						
		
		String jsonStr = null;
		try {
			jsonStr = entity.getText();
		} catch (IOException e) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new JsonRepresentation(getErrorMessage(e));			
		}

		try {
			String query = (String)getRequestAttributes().get("query");			
			LOG.info("the query: "+query);
			JSONObject payload = new JSONObject(jsonStr);				
			LOG.info("the request: "+payload.toString());
			// call service to get the data based on the parameter
			JSONArray result = null;
			
			if(query != null){
				XGeoPatientService geoPatient = (XGeoPatientService)XBaseGeoService.getInstance("");
				if(query.equals(XConstants.QUERY_TYPE_WINDOW)){ // the window query to get the NW,NE,SW,SE									
					geoPatient.doWindowStatistics(payload);					
				}else if(query.equals(XConstants.QUERY_TYPE_RANGE)){ // the range query to get list of objects based on the location and distance
					geoPatient.doRangeSearch(payload);
					
				}else if(query.equals(XConstants.QUERY_TYPE_KNN)){ // knn query to get k objects and its distance.
					geoPatient.doKNNSearch(payload);				
				}else {
					result = new JSONArray(getErrorMessage("the query is not supported now")+entity.toString());
				}

			}else{
				result = new JSONArray(new JSONObject().put("error", "the query is wrong"+entity.toString()));				
			}

			getResponse().setStatus(Status.SUCCESS_OK);
				
			return new JsonRepresentation(result);

		} catch (JSONException e) {
			getResponse().setStatus(Status.SUCCESS_OK);
			return new JsonRepresentation(getErrorMessage(e));
		}
	}
}
