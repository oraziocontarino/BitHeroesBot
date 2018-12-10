package gui.nativeUi.request;
import java.io.Reader;

import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import global.Utils;

public class TestRequest extends ServerResource {

	@Post("application/json")
    public Representation doPost(Representation entity) throws Exception{
        final Reader r = entity.getReader();
        StringBuffer sb = new StringBuffer();
        int c;
        // Reads the JSON from the input stream
        while ((c = r.read()) != -1) {
            sb.append((char) c);
        }
        System.out.println(sb.toString()); // Shows the JSON received
        Representation result = new StringRepresentation(sb.toString());
        return result;
    }
	@Get("application/json")
    public Representation doGet(Representation entity) throws Exception{
        final Reader r = entity.getReader();
        StringBuffer sb = new StringBuffer();
        int c;
        // Reads the JSON from the input stream
        while ((c = r.read()) != -1) {
            sb.append((char) c);
        }
        System.out.println(sb.toString()); // Shows the JSON received
        Representation result = new StringRepresentation(sb.toString());
        return result;
    }
/*
    public Representation handleRequest(Representation data) {
		Representation data2 = data;
		String tmp = getQuery().getValues("payload");
		JSONObject payload = new JSONObject(tmp);
		//Utils.test(payload.getJSONObject("configuration"));
        Representation result = new StringRepresentation("");
        System.out.println(data2);        
        return result;
    }
    */
}