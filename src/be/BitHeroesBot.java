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
	private boolean running;
	private int roundDelay;
	private BitHeroesGlobal[] tasks;
	
	private LogsManager logs;
	private int currentTaskIndex;
	
	private BitHeroesBot() throws AWTException, InterruptedException {
		this.tasks = new BitHeroesGlobal[4];
		
		tasks[0] = new Trial();
		tasks[1] = new Gauntlet();
		tasks[2] = new Raid();
		tasks[3] = new Mission();
		
		this.logs = new LogsManager();
		
		this.running = false;
		
		this.roundDelay = 0;
		this.currentTaskIndex = 0;
	}
	
	public static BitHeroesBot getInstance() throws AWTException, InterruptedException{
		if(instance == null){
			instance = new BitHeroesBot();
		}
		return instance;
	}

	private void updateBotConfiguration(JSONObject configuration) throws Exception {
		for(int i = 0; i<tasks.length; i++) {
			tasks[i].updateConfiguration(configuration);
		}
		this.updateRoundDelay(configuration);
	}
	
	public void run(JSONObject configuration) throws Exception {
		this.updateBotConfiguration(configuration);
		running = true;
		while(running) {
			for(currentTaskIndex = 0; currentTaskIndex < tasks.length; currentTaskIndex++) {
				String name = "", logCurrentTask = "", lognextTask = "";
				if(tasks[currentTaskIndex] instanceof Raid) {
					name = "Raid";
					logCurrentTask = LogsManager.RAID;
					lognextTask = LogsManager.MISSION;
				}else if(tasks[currentTaskIndex] instanceof Mission) {
					name = "Mission";
					logCurrentTask = LogsManager.MISSION;
					lognextTask = LogsManager.GAUNTLET;
				}else if(tasks[currentTaskIndex] instanceof Gauntlet) {
					name = "Gauntlet";
					logCurrentTask = LogsManager.GAUNTLET;
					lognextTask = LogsManager.RAID;
				}else if(tasks[currentTaskIndex] instanceof Trial) {
					name = "Trial";
					logCurrentTask = LogsManager.RAID;
					lognextTask = LogsManager.RAID;
				}
				
				if(!tasks[currentTaskIndex].isEnabled()) {
					System.out.println("Skip | Task["+currentTaskIndex+"] | "+name);
					continue;
				}
				
				System.out.println("Start | Task["+currentTaskIndex+"] | "+name);
				logs.update(LogsManager.RUNNING, logCurrentTask, lognextTask);
				tasks[currentTaskIndex].start(false);
				tasks[currentTaskIndex].reset();
				System.out.println("End   | Task["+currentTaskIndex+"] | "+name);
				if(!running) {
					logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
					System.out.println("THREAD DIED!");
					return;
				}
			}
			if(!running) {
				logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
				System.out.println("THREAD DIED!");
				return;
			}
			System.out.println("Waiting "+this.roundDelay+" milliseconds...");
			logs.update(LogsManager.WAITING, LogsManager.NONE, LogsManager.RAID);
			CustomRobot.getInstance().sleep(this.roundDelay);
		}
		System.out.println("THREAD DIED!");
	}
	
	public void stop() {
		this.running = false;
		tasks[currentTaskIndex].stop();
		logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
		System.out.println("Tasks stopped!");
	}
	
	public JSONObject getLogs() {
		return this.logs.getLogs();
	}
	private void updateRoundDelay(JSONObject configuration) {
		this.roundDelay = configuration.getJSONObject("delays").getInt("round");
	}
	
}
