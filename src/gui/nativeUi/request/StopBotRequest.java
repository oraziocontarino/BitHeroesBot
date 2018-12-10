package gui.nativeUi.request;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import gui.javagx.AsyncBot;

public class StopBotRequest extends ServerResource {

	@Get ("json") // add the get annotation so it knows this is for gets
    // method is pretty self explanatory
    public Representation handleRequest() {
    	AsyncBot.getInstance().interrupt();
        Representation result = new StringRepresentation("");
        System.out.println(result);
		return result;      
    }
}