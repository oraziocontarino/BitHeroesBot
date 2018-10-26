package global;

import org.json.JSONObject;

public class LogsManager {
	private JSONObject logs;
	private final static String CURRENT_STATUS = "CURRENT_STATUS";
	private final static String CURRENT_ACTION = "CURRENT_ACTION";
	private final static String NEXT_ACTION = "NEXT_ACTION";
	
	public static final String RAID = "RAID";
	public static final String MISSION = "MISSION";
	public static final String FISHING = "FISHING";
	public static final String NONE = "NONE";
	public static final String RUNNING = "RUNNING";
	public static final String WAITING = "WAITING";
	public static final String IDLE = "IDLE";
	
	public LogsManager() {
		logs = new JSONObject().put(CURRENT_ACTION, "").put(CURRENT_STATUS, "").put(NEXT_ACTION, "");
	}
	
	
	public void setCurrentStatus(String value) {
		this.setStatus(CURRENT_STATUS, value);
	}
	
	public void setCurrentAction(String value) {
		this.setStatus(CURRENT_ACTION, value);
	}
	
	public void setNextAction(String value) {
		this.setStatus(NEXT_ACTION, value);
	}
	
	
	public String getCurrentStatus(String value) {
		return this.getStatus(CURRENT_STATUS);
	}
	
	public String getCurrentAction(String value) {
		return this.getStatus(CURRENT_ACTION);
	}
	
	public String getNextAction(String value) {
		return this.getStatus(NEXT_ACTION);
	}

	private void setStatus(String key, String value) {
		this.logs.put(key, value);
	}
	
	private String getStatus(String key) {
		return this.logs.getString(key);
	}
	
	public JSONObject getLogs() {
		return this.logs;
	}
}

