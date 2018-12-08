package be;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import org.json.JSONObject;

import global.Utils;
import lib.CustomRobot;

public abstract class BitHeroesGlobal {
	protected int state;
	protected int running;
	protected CustomRobot customRobot;
	protected Color fishingBarColor;
	protected Color fishingCaputureColor;
	protected Point topLeftCorner;
	protected Point bottomRightCorner;
	protected Point center;
	protected int width; 
	protected int height;
	protected int stepStartTime;
	protected boolean enabled;
	protected boolean firstRun;
	protected BitHeroesGlobal() throws AWTException {
		this.customRobot = CustomRobot.getInstance();
		
		this.state = 0;
		
		this.fishingBarColor = new Color(35, 107, 167);
		
		this.fishingCaputureColor = new Color(77, 254, 0);
		
		this.updateCoords(Utils.getDefaultConfiguration());

		
		this.stepStartTime = 0;
	}
	
	protected void updateCoords(JSONObject configuration) {
		this.topLeftCorner = Utils.getGameTopLeftCorner(configuration);
		
		this.bottomRightCorner = Utils.getGameBottomRightCorner(configuration);
		
		this.width = Utils.getGameWidth(this.topLeftCorner, this.bottomRightCorner);
		this.height = Utils.getGameHeight(this.topLeftCorner, this.bottomRightCorner);
		
		this.center = Utils.getGameCenter(this.topLeftCorner, this.width, this.height);
		this.updateJobCoords();
	}
	public int getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public void restartGame() throws InterruptedException, AWTException {
		int tries = 5;
		this.customRobot.mouseClick(this.topLeftCorner.x+5, this.topLeftCorner.y+5);
		
		for(int i=0; i<tries; i++) {
			this.customRobot.sleep(500);
			this.customRobot.send("{Esc}");
		}
		this.state = 0;
		this.stepStartTime = this.getTimestamp();
	}

	public void updateStepStartTime() {
		this.stepStartTime = this.getTimestamp();
	}
	
	public void stop() {
		this.running =- 10;
	}
	public void reset() {
		this.running = 0;
	}
	protected boolean stopJob(boolean loop) {
		return (!loop && !this.firstRun);
	}
	
	public void start(boolean loop) throws Exception {
		if(!this.enabled) {
			return;
		}
		this.firstRun = true;
		this.customRobot.sleep(1000);
		this.restartGame();
		this.running +=1;
		while(this.running > 0) {
			if(this.stopJob(loop)) {
				return;
			}
			this.main();
		}
	}
	
	protected abstract void main() throws Exception;
	protected abstract void updateJobCoords();
	protected abstract void updateConfiguration(JSONObject configuration);
	protected abstract void updateEnabledStatus(JSONObject configuration);

}