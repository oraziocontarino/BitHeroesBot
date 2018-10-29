package be;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import global.LogsManager;
import lib.CustomRobot;

public class BitHeroesBot {
	private static BitHeroesBot instance;
	private Mission mission;
	private Raid raid;
	private boolean running;
	private JSONObject configuration;
	
	private LogsManager logs;
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
	private BitHeroesBot(JSONObject configuration) throws AWTException, InterruptedException {
		this.configuration = configuration;
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
		this.mission = new Mission(coords);
		
		//TODO: set raid
		this.raid = new Raid(coords);
		
		this.running = false;
		this.logs = new LogsManager();
	}
	
	public static BitHeroesBot getInstance(JSONObject configuration) throws AWTException, InterruptedException{
		if(instance == null){
			instance = new BitHeroesBot(configuration);
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
			System.out.println("starting raid");
			logs.update(LogsManager.RUNNING, LogsManager.RAID, LogsManager.MISSION);
			//raid.start(false);
			if(!running) {
				System.out.println("Exit raid");
				break;
			}
			
			System.out.println("starting mission");
			logs.update(LogsManager.RUNNING, LogsManager.MISSION, LogsManager.RAID);
			mission.start(false);
			if(!running) {
				System.out.println("Exit mission");
				break;
			}

			System.out.println("Waiting 10 minutes...");
			logs.update(LogsManager.WAITING, LogsManager.NONE, LogsManager.RAID);
			CustomRobot.getInstance().sleep(10*60*1000);
		}
		logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
		System.out.println("THREAD DIED!");
	}
	
	public void stop() {
		this.running = false;
		raid.stop();
		mission.stop();
		System.out.println("Force stop!");
		logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
	}
	
	public void changeMission(String missionKey) {
		mission.setMission(missionKey);
	}
	
	public JSONObject getLogs() {
		return this.logs.getLogs();
	}
	
}
