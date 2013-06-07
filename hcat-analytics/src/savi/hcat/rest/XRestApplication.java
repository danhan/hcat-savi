package savi.hcat.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class XRestApplication extends Application{

	/**
	 * bound the request from client to the service in the backend
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Appointment Resource
        router.attach("/statistics/{kpi}", XStatisticsResource.class);
        
        // HCAT resource 
        router.attach("/spatial/{query}", XGeoSpatialResource.class);
        
        // Patient resource
        router.attach("/report/{kpi}", XOverviewResource.class);
        
        // 
        //router.attach("/edges/{edgeId}", XEdgesResource.class);
        
        
        return router;		
	}
	

}
