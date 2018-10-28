package gui.javagx;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import be.BitHeroesBot;
import global.Utils;
import javafx.scene.web.WebEngine;

public class ConfigurationRequestManager {
	public static final String ACTION_FIELD = "action";
	public static final String PAYLOAD_FIELD = "payload";
	public static final String SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
	public static final String SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
	public static final String SET_RAID_REQUEST = "SET_RAID_REQUEST";
	public static final String SET_START_BOT_REQUEST = "SET_START_BOT_REQUEST";
	public static final String SET_STOP_BOT_REQUEST = "SET_STOP_BOT_REQUEST";
	public static final String SET_GET_LOGS_REQUEST = "SET_GET_LOGS_REQUEST";
	private AsyncBot asyncBot;
	//TODO: change class from statick to singletone and use Thread thread for bot management (start/interuput)
	public void handleRequest(WebEngine engine, String request) {
		//System.out.println("Request: "+request);
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
			case SET_START_BOT_REQUEST:
				startBot(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case SET_STOP_BOT_REQUEST:
				stopBot(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case SET_GET_LOGS_REQUEST:
				getLogs(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			default:
				System.out.println("Unknown request: "+request);
    	}
	}
	
	public void setCoords(WebEngine engine, JSONObject payload) {
		JSONObject topLeft = new JSONObject();
		JSONObject bottomRight = new JSONObject();
		boolean error = false;
		try {
			System.out.println("Action: "+SET_COORDS_REQUEST);
	    	System.out.println("Payload: "+payload.toString());
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

	public void setMission(WebEngine engine, JSONObject payload) {
    	System.out.println("Action: "+SET_MISSION_REQUEST);
    	System.out.println("Payload: "+payload.toString());
    	
		JSONObject selectedMission = new JSONObject();
		selectedMission.put("id", payload.getString("selectedMission"));
		selectedMission.put("label", payload.getString("selectedMission"));
		
		JSONObject response = new JSONObject();
		response.put("error", false);
		response.put("selectedMission", selectedMission);
    	engine.executeScript("setMissionMessageCallback('"+response.toString()+"')");
	}
	
	public void setRaid(WebEngine engine, JSONObject payload) {
    	System.out.println("Action: "+SET_RAID_REQUEST);
    	System.out.println("Payload: "+payload.toString());
    	
		JSONObject selectedRaid = new JSONObject();
		selectedRaid.put("id", payload.getString("selectedRaid"));
		selectedRaid.put("label", payload.getString("selectedRaid"));
		
		JSONObject response = new JSONObject();
		response.put("error", false);
		response.put("selectedRaid", selectedRaid);
    	engine.executeScript("setRaidMessageCallback('"+response.toString()+"')");
	}
	
	public void startBot(WebEngine engine, JSONObject payload) {
		System.out.println("Action: "+SET_START_BOT_REQUEST);
    	System.out.println("Payload: "+payload.toString());
		AsyncBot.getInstance(payload).run();
    	System.out.println("freeeeeeeee: "+payload.toString());
    	engine.executeScript("setStartBotMessageCallback()");
	}

	public void stopBot(WebEngine engine, JSONObject payload) {
		System.out.println("Action: "+SET_STOP_BOT_REQUEST);
    	System.out.println("Payload: "+payload.toString());
    	AsyncBot.getInstance().interrupt();
    	engine.executeScript("setStopBotMessageCallback()");
	}
	
	public void getLogs(WebEngine engine, JSONObject payload) {
		//System.out.println("Action: "+SET_STOP_BOT_REQUEST);
    	//System.out.println("Payload: "+payload.toString());
    	try {
    		if(AsyncBot.getInstance() == null) {
    			return;
    		}
			JSONObject logs = BitHeroesBot.getInstance().getLogs();
			System.out.println(logs.toString());
	    	engine.executeScript("setGetLogsMessageCallback('"+logs.toString()+"')");
		} catch (InterruptedException | AWTException e) {
	    	engine.executeScript("setGetLogsMessageCallback('{}')");
			e.printStackTrace();
		}
	}
}
