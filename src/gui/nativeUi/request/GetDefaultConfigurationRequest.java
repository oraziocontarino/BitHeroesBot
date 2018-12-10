package gui.nativeUi.request;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import global.Utils;

public class GetDefaultConfigurationRequest extends ServerResource {

	@Get ("json") // add the get annotation so it knows this is for gets
    // method is pretty self explanatory
    public Representation handleRequest() {
		JSONObject defaultConfiguration = Utils.getDefaultConfiguration();
        Representation result = new StringRepresentation(defaultConfiguration.toString()); 
		System.out.println(result.toString());		  
        return result;
    }
}