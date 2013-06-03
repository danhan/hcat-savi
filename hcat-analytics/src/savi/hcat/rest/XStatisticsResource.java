package savi.hcat.rest;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;

import savi.hcat.common.util.XConstants;
import savi.hcat.rest.service.XStatApmtService;
import savi.hcat.rest.service.XStatPatientService;
import savi.hcat.rest.service.XStatisticsService;

/**
 * This is to do the sum and average of all objects
 * @author dan
 *
 */
public class XStatisticsResource extends XBaseResource{

	@Post	
	public Representation statistics(Representation entity) throws IOException {
		System.out.println("in statistics() ");
		String jsonStr = null;
		try {
			jsonStr = entity.getText();
		} catch (IOException e) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			//return new JsonRepresentation(getErrorMessage(e));			
		}

		try {
			String kpi = getQuery().getValues("kpi");
			
			JSONObject payload = new JSONObject(jsonStr);
			Object object = payload.get(XConstants.STAT_KEY_OBJECT);			

			System.out.println("the request: "+payload.toString());
			// call service to get the data based on the parameter
			
			if(object!= null){
				if(object.equals(XConstants.STAT_VALUE_APPOINTMENT)){ // appointment statistics
					XStatApmtService stat_service = (XStatApmtService)XStatisticsService.getInstance(XConstants.STAT_VALUE_APPOINTMENT);
					stat_service.connectHBase();
					System.out.println("after connecting hbase ");
					if(kpi.equals("sum")){
						stat_service.getSummary(payload);	
					}else if(kpi.equals("avg")){
						stat_service.getAverage(payload);
					}													
					System.out.println("after kpi "+kpi);
				}else if(object.equals(XConstants.STAT_VALUE_PATIENT)){ // service statistics
					XStatPatientService stat_service = (XStatPatientService)XStatisticsService.getInstance(XConstants.STAT_VALUE_PATIENT);
					stat_service.connectHBase();
					System.out.println("after connecting hbase ");
					if(kpi.equals("sum")){
						stat_service.getSummary(payload);	
					}else if(kpi.equals("avg")){
						stat_service.getAverage(payload);
					}													
					System.out.println("after kpi "+kpi);					
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
