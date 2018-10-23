package gui.javagx;

import org.json.JSONObject;

public class ConfigurationRequestManager {
	public static final String ACTION_FIELD = "action";
	public static final String PAYLOAD_FIELD = "payload";
	public static final String SET_COORDS_REQUEST = "SET_COORDS_REQUEST";
	public static final String SET_MISSION_REQUEST = "SET_MISSION_REQUEST";
	public static final String SET_RAID_REQUEST = "SET_RAID_REQUEST";
	public static void handleRequest(String request) {
    	JSONObject node = new JSONObject(request);
    	switch(node.getString("action")) {
			case SET_COORDS_REQUEST:
				setCoords(node.getString(PAYLOAD_FIELD));
			break;
			case SET_MISSION_REQUEST:
			break;
			case SET_RAID_REQUEST:
			break;
    	}
    	System.out.println("Data: "+request);
    	System.out.println("Action: "+node.getString("action"));
    	System.out.println("Payload: "+node.getString("payload"));
	}
	
	public static void setCoords(String payload) {
		
	}
}
