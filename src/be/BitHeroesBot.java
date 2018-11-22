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
	private BitHeroesBot() throws AWTException, InterruptedException {
		this.tasks = new BitHeroesGlobal[4];
		
		tasks[0] = new Trial();
		tasks[1] = new Gauntlet();
		tasks[2] = new Raid();
		tasks[3] = new Mission();
		
		this.logs = new LogsManager();
		
		this.running = false;
		
		this.roundDelay = 0;
	}
	
	public static BitHeroesBot getInstance() throws AWTException, InterruptedException{
		if(instance == null){
			instance = new BitHeroesBot();
		}
		return instance;
	}

	public void updateBotConfiguration(JSONObject configuration) throws Exception {
		for(BitHeroesGlobal task : tasks) {
			task.updateConfiguration(configuration);
		}
		this.updateRoundDelay(configuration);
	}
	
	public void run() throws Exception {
		//CustomRobot.getInstance().detectGamePoistion();
		running = true;
		while(running) {
			int index = 0;
			for(BitHeroesGlobal task : tasks) {
				String name = "", logCurrentTask = "", lognextTask = "";
				if(task instanceof Raid) {
					name = "Raid";
					logCurrentTask = LogsManager.RAID;
					lognextTask = LogsManager.MISSION;
				}else if(task instanceof Mission) {
					name = "Mission";
					logCurrentTask = LogsManager.MISSION;
					lognextTask = LogsManager.GAUNTLET;
				}else if(task instanceof Gauntlet) {
					name = "Gauntlet";
					logCurrentTask = LogsManager.GAUNTLET;
					lognextTask = LogsManager.RAID;
				}else if(task instanceof Trial) {
					name = "Trial";
					logCurrentTask = LogsManager.RAID;
					lognextTask = LogsManager.RAID;
				}
				System.out.println("Starting task["+index+"] "+name);
				logs.update(LogsManager.RUNNING, logCurrentTask, lognextTask);
				task.start(false);
				if(!running) {
					System.out.println("Exit "+name);
					logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
					task.reset();
					System.out.println("THREAD DIED!");
					return;
				}
				task.reset();
				index ++;
			}
			System.out.println("Waiting "+this.roundDelay+" milliseconds...");
			logs.update(LogsManager.WAITING, LogsManager.NONE, LogsManager.RAID);
			CustomRobot.getInstance().sleep(this.roundDelay);
		}
		System.out.println("THREAD DIED!");
	}
	
	public void stop() {
		this.running = false;
		System.out.println("Gonna stop all tasks...");
		for(BitHeroesGlobal task : tasks) {
			task.stop();
		}
		System.out.println("Tasks stopped!");
		logs.update(LogsManager.IDLE, LogsManager.NONE, LogsManager.NONE);
	}
	
	public JSONObject getLogs() {
		return this.logs.getLogs();
	}
	private void updateRoundDelay(JSONObject configuration) {
		this.roundDelay = configuration.getJSONObject("delays").getInt("round");
	}
	
}
