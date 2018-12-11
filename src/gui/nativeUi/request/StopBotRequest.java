package gui.nativeUi.request;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import be.AsyncBot;

public class StopBotRequest extends ServerResource {

	@Post
    public Representation doPost(JsonRepresentation entity) throws Exception{
		//JSONObject payload = entity.getJsonObject().getJSONObject("payload");
    	AsyncBot.getInstance().interrupt();
        Representation result = new StringRepresentation("");
        System.out.println(result);
		return result;      
    }
}