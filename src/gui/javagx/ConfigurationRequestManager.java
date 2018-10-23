package gui.javagx;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import javafx.scene.web.WebEngine;

public class ConfigurationRequestManager {
	public static final String ACTION_FIELD = "action";
	public static final String PAYLOAD_FIELD = "payload";
	public static final String SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
	public static final String SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
	public static final String SET_RAID_REQUEST = "SET_RAID_REQUEST";
	public static void handleRequest(WebEngine engine, String request) {
		System.out.println("Request: "+request);
    	JSONObject node = new JSONObject(request);
    	switch(node.getString("action")) {
			case SET_COORDS_REQUEST:
				setCoords(engine, node.getJSONObject(PAYLOAD_FIELD));
                
			break;
			case SET_MISSION_REQUEST:
				setMission(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case SET_RAID_REQUEST:
				setRaid(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			default:
				System.out.println("Unknown request: "+request);
    	}
	}
	
	public static void setCoords(WebEngine engine, JSONObject payload) {
		JSONObject topLeft = new JSONObject();
		JSONObject bottomRight = new JSONObject();
		boolean error;
		try {
			System.out.println("Action: "+SET_COORDS_REQUEST);
	    	System.out.println("Payload: "+payload.toString());
			Point[] coords = Utils.detectGamePoistion();
			topLeft.put("x", coords[0].x);
			topLeft.put("y", coords[0].y);
			bottomRight.put("x", coords[1].x);
			bottomRight.put("y", coords[1].y);
			error = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
    	engine.executeScript("setCoordsMessageCallback('"+response.toString()+"')");
	}

	public static void setMission(WebEngine engine, JSONObject payload) {
    	System.out.println("Action: "+SET_MISSION_REQUEST);
    	System.out.println("Payload: "+payload.toString());
		JSONObject response = new JSONObject();
		response.put("error", false);
		response.put("selectedMission", payload.getString("selectedMission"));
    	engine.executeScript("setMissionMessageCallback('"+response.toString()+"')");
	}
	
	public static void setRaid(WebEngine engine, JSONObject payload) {
    	System.out.println("Action: "+SET_RAID_REQUEST);
    	System.out.println("Payload: "+payload.toString());
		JSONObject response = new JSONObject();
		response.put("error", false);
		response.put("selectedRaid", payload.getString("selectedRaid"));
    	engine.executeScript("setRaidMessageCallback('"+response.toString()+"')");
	}
	
	
	
}
