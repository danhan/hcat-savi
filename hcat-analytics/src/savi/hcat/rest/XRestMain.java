package savi.hcat.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
/**
 * 
 * @author dan
 *
 */
public class XRestMain {

	private static Log LOG = LogFactory.getLog(XRestMain.class);
	
	public static final String DOMAIN = "/analytics";

	private static Context createContext() {
		Context context = new Context();
		
		context.getParameters().set("maxThreads", "100");
		context.getParameters().set("maxConnectionsPerHost", "100");
		context.getParameters().set("maxTotalConnections", "100");
		return context;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.info("start REST in main()");		
		try {

			Server server = new Server(createContext(),Protocol.HTTP, 9999);

			// Create a new Component.
			Component component = new Component();

			// Add a new HTTP server listening on port 9999.
			component.getServers().add(server);            

			// Attach the sample application.
			component.getDefaultHost().attach(DOMAIN, new XRestApplication());

			// Start the component.
			component.start();
		} catch (Exception e) {
			// Something is wrong.
			e.printStackTrace();
		}
	}

}
