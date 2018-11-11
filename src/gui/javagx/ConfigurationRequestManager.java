package gui.javagx;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import be.BitHeroesBot;
import global.Utils;
import javafx.scene.web.WebEngine;

@SuppressWarnings("restriction")
public class ConfigurationRequestManager {
	public static final String ACTION_FIELD = "action";
	public static final String PAYLOAD_FIELD = "payload";
	public static final String SET_COORDS = "SET_COORDS";
	public static final String SET_MISSION = "SET_MISSION";
	public static final String SET_RAID = "SET_RAID";
	public static final String START_BOT = "START_BOT";
	public static final String STOP_BOT = "STOP_BOT";
	public static final String GET_LOGS = "GET_LOGS";
	public static final String GET_DEFAULT_CONFIGURATION = "GET_DEFAULT_CONFIGURATION";
	public static final String TEST = "TEST";

	public void handleRequest(WebEngine engine, String request) {
		System.out.println("Request: "+request);
    	JSONObject node = new JSONObject(request);
    	switch(node.getString("action")) {
			case SET_COORDS:
				setCoords(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case START_BOT:
				startBot(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case STOP_BOT:
				stopBot(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case GET_LOGS:
				getLogs(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case GET_DEFAULT_CONFIGURATION:
				getDefaultConfiguration(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			case TEST:
				test(engine, node.getJSONObject(PAYLOAD_FIELD));
			break;
			default:
				System.out.println("Unknown request: "+request);
    	}
	}
	public void test(WebEngine engine, JSONObject payload) {
		Utils.test(payload.getJSONObject("configuration"));
	}
	public void setCoords(WebEngine engine, JSONObject payload) {
		JSONObject topLeft = new JSONObject();
		JSONObject bottomRight = new JSONObject();
		boolean error = false;
		try {
			System.out.println("Action: "+SET_COORDS);
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

    	this.runScript(engine, SET_COORDS, response.toString());
	}

	public void startBot(WebEngine engine, JSONObject payload) {
		System.out.println("Action: "+START_BOT);
    	System.out.println("Payload: "+payload.toString());
		AsyncBot.getInstance().setConfiguration(payload.getJSONObject("configuration"));
		AsyncBot.getInstance().run();
    	this.runScript(engine, START_BOT, "");
	}

	public void stopBot(WebEngine engine, JSONObject payload) {
		System.out.println("Action: "+STOP_BOT);
    	System.out.println("Payload: "+payload.toString());
    	AsyncBot.getInstance().interrupt();
    	this.runScript(engine, STOP_BOT, "");
	}
	
	public void getLogs(WebEngine engine, JSONObject payload) {
		//System.out.println("Action: "+SET_STOP_BOT);
    	//System.out.println("Payload: "+payload.toString());
    	try {
			JSONObject logs = BitHeroesBot.getInstance().getLogs();
			//System.out.println(logs.toString());
	    	this.runScript(engine, GET_LOGS, logs.toString());
		} catch (InterruptedException | AWTException e) {
	    	this.runScript(engine, GET_LOGS, "{}");
			e.printStackTrace();
		}
	}
	
	public void getDefaultConfiguration(WebEngine engine, JSONObject payload) {
		//System.out.println("Action: "+SET_STOP_BOT);
    	//System.out.println("Payload: "+payload.toString());
		JSONObject defaultConfiguration = Utils.getDefaultConfiguration();
		System.out.println(defaultConfiguration.toString());
		this.runScript(engine, GET_DEFAULT_CONFIGURATION, defaultConfiguration.toString());
		
	}
	
	private void runScript(WebEngine engine, String action, String payload) {
		engine.executeScript("resolvePromise('"+action+"', '"+payload+"')");
	}

}
