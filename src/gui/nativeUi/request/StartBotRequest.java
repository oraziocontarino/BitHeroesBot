package gui.nativeUi.request;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import be.AsyncBot;

public class StartBotRequest extends ServerResource {

	@Post
    public Representation doPost(JsonRepresentation entity) throws Exception{
		JSONObject payload = entity.getJsonObject().getJSONObject("payload");
		System.out.println(payload.toString());
		AsyncBot.getInstance().setConfiguration(payload.getJSONObject("configuration"));
		AsyncBot.getInstance().run();
        return new StringRepresentation("");
    }
}