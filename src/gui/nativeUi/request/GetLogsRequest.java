package gui.nativeUi.request;
import java.awt.AWTException;

import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import be.BitHeroesBot;

public class GetLogsRequest extends ServerResource {

	@Get ("json") // add the get annotation so it knows this is for gets
    // method is pretty self explanatory
    public Representation handleRequest() throws AWTException, InterruptedException {
		/*
		JSONObject logs = BitHeroesBot.getInstance().getLogs();
		Representation result = new StringRepresentation(logs.toString());
        //System.out.println(logs.toString());        
        return result;
        */
		Representation result = new StringRepresentation("DISABLED");
		return result;
    }
}