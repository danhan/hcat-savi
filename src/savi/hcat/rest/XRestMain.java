package savi.hcat.rest;

import org.restlet.Component;
import org.restlet.data.Protocol;
/**
 * 
 * @author dan
 *
 */
public class XRestMain {

	public static final String DOMAIN = "/analytics";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            // Create a new Component.
            Component component = new Component();

            // Add a new HTTP server listening on port 9999.
            component.getServers().add(Protocol.HTTP, 9999);

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
