package savi.hcat.rest;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

import savi.hcat.rest.service.XConstants;

/**
 * This is to do the sum and average of all objects
 * @author dan
 *
 */
public class XStatisticsResource extends XBaseResource{

	@Get	
	public Representation statistics(Representation entity) throws IOException {
		String jsonStr = null;
		try {
			jsonStr = entity.getText();
		} catch (IOException e) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return new JsonRepresentation(getErrorMessage(e));
		}
		//addCustomHttpHeaders();
		try {
			JSONObject payload = new JSONObject(jsonStr);
			Object object = payload.get(XConstants.STAT_KEY_OBJECT);
			
			// call service to get the data based on the parameter
			
			if(object!= null){
				if(object.equals(XConstants.STAT_VALUE_APPOINTMENT)){ // appointment statistics

				}else if(object.equals(XConstants.STAT_VALUE_SERVICE)){ // service statistics

				}
			}



			getResponse().setStatus(Status.SUCCESS_OK);

			JSONArray result = null;
			return new JsonRepresentation(result);

		} catch (JSONException e) {
			getResponse().setStatus(Status.SUCCESS_OK);
			return new JsonRepresentation(getErrorMessage(e));
		} /*catch (XStatisticsServiceException e) {
                 getResponse().setStatus(Status.SUCCESS_OK);
                 return new JsonRepresentation(getErrorMessage(e));
         } catch (ParseException e) {
                 getResponse().setStatus(Status.SUCCESS_OK);
                 return new JsonRepresentation(getErrorMessage(e));
         } */
	}

}
