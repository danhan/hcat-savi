package savi.hcat.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.engine.http.header.HeaderConstants;
import org.restlet.resource.ServerResource;

public class XBaseResource extends ServerResource {


	protected void addCustomHttpHeaders() {
		// add custom headers to avoid cross-domain problem.
		Form responseHeaders = (Form) getResponse().getAttributes().get(
				HeaderConstants.ATTRIBUTE_HEADERS);
		if (responseHeaders == null) {
			responseHeaders = new Form();
			getResponse().getAttributes().put(
					HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders);
		}
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Credentials", "true");
	}
	
	protected JSONObject getErrorMessage(String message) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("error", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	protected JSONObject getErrorMessage(Exception e) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		String s = writer.toString();
		try {
			writer.close();
		} catch (IOException e1) {
			// ignore
		}
		return getErrorMessage(s);
	}

}
