package be;

import java.awt.AWTException;

import org.json.JSONObject;

import be.dungeons.Mission;
import be.dungeons.Raid;
import be.events.Gauntlet;
import be.events.Trial;
import global.LogsManager;
import lib.CustomRobot;

public class BitHeroesBot {
	private static BitHeroesBot instance;
	private Mission mission;
	private Raid raid;
	private Trial trial;
	private Gauntlet gauntlet;
	private boolean running;
	
	private LogsManager logs;
	private final static int WAIT_TIME = 0;
	//private final static int WAIT_TIME = 10*60*1000;
	private BitHeroesBot() throws AWTException, InterruptedException {

		this.mission = new Mission();
		
		this.raid = new Raid();

		this.trial = new Trial();
		
		this.gauntlet = new Gauntlet();
		
		this.logs = new LogsManager();
		
		this.running = false;
	}
	
	public static BitHeroesBot getInstance() throws AWTException, InterruptedException{
		if(instance == null){
			instance = new BitHeroesBot();
		}
		return instance;
	}

	public void updateBotConfiguration(JSONObject configuration) throws Exception {
		raid.updateConfiguration(configuration);
		mission.updateConfiguration(configuration);
		trial.updateConfiguration(configuration);
		gauntlet.updateConfiguration(configuration);
	}
	
	public void run() throws Exception {
		//CustomRobot.getInstance().detectGamePoistion();
		running = true;
		while(running) {
			System.out.println("Starting trial");
			logs.update(LogsManager.RUNNING, LogsManager.TRIAL, LogsManager.RAID);
			trial.start(false);
			if(!running) {
				System.out.println("Exit trial");
				trial.reset();
				break;
			}
			logs.update(LogsManager.RUNNING, LogsManager.GAUNTLET, LogsManager.RAID);
			gauntlet.start(false);
			if(!running) {
				System.out.println("Exit gauntlet");
				gauntlet.reset();
				break;
			}
			System.out.println("Starting raid");
			logs.update(LogsManager.RUNNING, LogsManager.RAID, LogsManager.MISSION);
			raid.start(false);
			if(!running) {
				System.out.println("Exit raid");
				raid.reset();
				break;
			}
			raid.reset();
			System.out.println("Raid finished");
			
			System.out.println("Starting mission");
			logs.update(LogsManager.RUNNING, LogsManager.MISSION, LogsManager.RAID);
			mission.start(false);
			if(!running) {
				System.out.println("Exit mission");
				mission.reset();
				break;
			}
			mission.reset();
			System.out.println("Mission finished");
			
			System.out.println("Waiting "+(WAIT_TIME/60000)+" minutes...");
			logs.update(LogsManager.WAITING, LogsManager.NONE, LogsManager.RAID);
			CustomRobot.getInstance().sleep(WAIT_TIME);
		}
		logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
		raid.reset();
		mission.reset();
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
