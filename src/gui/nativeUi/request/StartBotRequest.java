package gui.nativeUi.request;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import gui.javagx.AsyncBot;

public class StartBotRequest extends ServerResource {

	@Get ("json") // add the get annotation so it knows this is for gets
    // method is pretty self explanatory
    public Representation handleRequest() {
		String tmp = getQuery().getValues("payload");
		JSONObject payload = new JSONObject(tmp);
		AsyncBot.getInstance().setConfiguration(payload.getJSONObject("configuration"));
		AsyncBot.getInstance().run();
        Representation result = new StringRepresentation("");
        System.out.println(result);        
        return result;
    }
}