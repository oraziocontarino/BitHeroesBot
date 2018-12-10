package gui.nativeUi.request;
import java.awt.Point;

import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import global.Utils;

public class SetCoordsRequest extends ServerResource {

	@Get ("json") // add the get annotation so it knows this is for gets
    // method is pretty self explanatory
    public Representation handleRequest() {
		JSONObject topLeft = new JSONObject();
		JSONObject bottomRight = new JSONObject();
		boolean error = false;
		try {
			Point[] coords = Utils.detectGamePoistion();
			if(coords[0] == null || coords[1] == null) {
				topLeft.put("x", "error");
				topLeft.put("y", "error");
				bottomRight.put("x", "error");
				bottomRight.put("y", "error");
				error = true;	
			} else {
				topLeft.put("x", coords[0].x);
				topLeft.put("y", coords[0].y);
				bottomRight.put("x", coords[1].x);
				bottomRight.put("y", coords[1].y);
				error = false;
			}
		} catch (Exception e) {
			topLeft.put("x", "error");
			topLeft.put("y", "error");
			bottomRight.put("x", "error");
			bottomRight.put("y", "error");
			error = true;
			e.printStackTrace();
		}
		JSONObject response = new JSONObject();
		response.put("error", error);
		response.put("topLeft", topLeft);
		response.put("bottomRight", bottomRight);
    	
        Representation result = new StringRepresentation(response.toString());
        System.out.println(result);        
        return result;
    }
}