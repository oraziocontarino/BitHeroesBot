package gui.nativeUi.request;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class TestRequest extends ServerResource{
	@Post
    public Representation doPost(JsonRepresentation entity) throws Exception{
		JSONObject payload = entity.getJsonObject().getJSONObject("payload");
        System.out.println("GONNA PRINT PAYLOAD: "+payload);
        return new StringRepresentation("OK FROM POST");
    }

}