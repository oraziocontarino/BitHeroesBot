package be;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lib.CustomRobot;
import lib.KeyBindingManager;

public class BitHeroesBot {
	private static BitHeroesBot instance;
	private Mission mission;
	private Raid raid;
	private boolean running;
	private WebEngine webEngine;
	private JSONObject configuration;
	/*
	 * 
var configuration = {
	error: {
		coords: false,
		mission: false,
		raid: false
	},
	topLeft : {
		x : 0,
		y : 0
	},
	bottomRight : {
		x : 0,
		y : 0
	},
	selectedMission: {
		label: "Z1D1",
		id: "Z1D1"
	},
	selectedRaid: {
		label: "R1 - Astorath",
		id: "R1"
	}
}
	 * */
	private BitHeroesBot(JSONObject configuration, WebEngine webEngine) throws AWTException, InterruptedException {
		this.configuration = configuration;
		this.webEngine = webEngine;
		Point[] coords = new Point[2];
		coords[0] = new Point(
				configuration.getJSONObject("topLeft").getInt("x"), 
				configuration.getJSONObject("topLeft").getInt("y")
		);
		
		coords[1] = new Point(
				configuration.getJSONObject("bottomRight").getInt("x"), 
				configuration.getJSONObject("bottomRight").getInt("y")
		);
		//TODO: set mission
		mission = new Mission(coords);
		
		//TODO: set raid
    	raid = new Raid(coords);
    	
    	running = false;
	}
    
	public static BitHeroesBot getInstance(JSONObject configuration, WebEngine webEngine) throws AWTException, InterruptedException{
        if(instance == null){
            instance = new BitHeroesBot(configuration, webEngine);
        }
        return instance;
    }
	public static BitHeroesBot getInstance() throws AWTException, InterruptedException{
        return instance;
    }
	public void run() throws InterruptedException, AWTException {
		//CustomRobot.getInstance().detectGamePoistion();
    	running = true;
		while(running) {
    		//CustomRobot.getInstance().sleep(100);
			//System.out.println("test");
			
	    	System.out.println("starting raid");
	    	raid.start(false);
	    	if(!running) {
	    		return;
	    	}
	    	System.out.println("starting mission");
	    	mission.start(false);
	    	if(!running) {
	    		return;
	    	}
	    	System.out.println("Waiting 10 minutes...");
	    	CustomRobot.getInstance().sleep(10*60*1000);
	    	
    	}
	}
	
	public void stop() {
		this.running = false;
		raid.stop();
		mission.stop();
		System.out.println("Force stop!");
	}
	
	public void changeMission(String missionKey) {
		mission.changeMission(missionKey);
	}
	

}
