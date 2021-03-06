package gui.nativeUi.request;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import be.BitHeroesBot;

public class GetLogsRequest extends ServerResource {

	@Post
    public Representation doPost(JsonRepresentation entity) throws Exception{
		//JSONObject payload = entity.getJsonObject().getJSONObject("payload");
		JSONObject logs = BitHeroesBot.getInstance().getLogs();
		//System.out.println(logs);
		return new StringRepresentation(logs.toString());
    }
}