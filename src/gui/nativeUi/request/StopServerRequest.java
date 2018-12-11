package gui.nativeUi.request;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import gui.nativeUi.RestletServer;

public class StopServerRequest extends ServerResource {
	@Post
    public Representation doPost(JsonRepresentation entity) throws Exception{
		RestletServer.getInstance().stop();
		System.exit(1);
		return new StringRepresentation(new JSONObject().put("isAlive", true).toString());
    }
}
