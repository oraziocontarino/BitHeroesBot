package be;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

import global.LogsManager;
import global.Utils;
import lib.CustomRobot;

public class BitHeroesBot {
	private static BitHeroesBot instance;
	private Mission mission;
	private Raid raid;
	private boolean running;
	private JSONObject configuration;
	
	private LogsManager logs;

	private BitHeroesBot() throws AWTException, InterruptedException {
		//TODO: set mission
		this.mission = new Mission();
		
		//TODO: set raid
		this.raid = new Raid();
		
		this.logs = new LogsManager();
		
		this.running = false;
	}
	
	public static BitHeroesBot getInstance() throws AWTException, InterruptedException{
		if(instance == null){
			instance = new BitHeroesBot();
		}
		return instance;
	}

	public void updateBotConfiguration() {
		raid.updateCoords(this.configuration);
		mission.updateCoords(this.configuration);
		mission.setMission(this.configuration);
	}
	public void setConfiguration(JSONObject configuration) {
		this.configuration = configuration;
	}
	public JSONObject getConfiguration() {
		return this.configuration;
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
	
	public JSONObject getLogs() {
		return this.logs.getLogs();
	}
	
}
