package be;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import org.json.JSONObject;

import lib.CustomRobot;

public abstract class Dungeon extends BitHeroesGlobal {
	protected Color refuseButtonColor;
	protected Point eroicoButton;
	protected Point accettaSquadraButton;
	protected Point[] autoBox;
	protected Color autoBoxEnabledColor;
	protected CustomRobot customRobot;
	protected Point refuseExitDungeon;
	protected boolean firstRun;
	protected boolean running;
	protected boolean enabled;
	
	protected Dungeon() throws AWTException {
		super();
		this.customRobot = CustomRobot.getInstance();
		
		
		//autoBoxEnabledColor = "0x8dd61d";
		this.autoBoxEnabledColor = new Color(141, 214, 29);
		
		//refuseButtonColor = "0xf49745";
		this.refuseButtonColor = new Color(244, 141, 53);
		
		this.setDungeonCoords();
		
	}
	protected abstract void updateConfiguration(JSONObject configuration);
	protected abstract void state0() throws InterruptedException, AWTException;
	protected abstract void state1() throws InterruptedException, AWTException;
	
	protected abstract void updateEnabledStatus(JSONObject configuration);
	private void setDungeonCoords() {
		this.eroicoButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.75)),
				(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		
		this.accettaSquadraButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.75)),
				(int) (this.topLeftCorner.y + (this.height*0.90))
		);
		this.autoBox = new Point[2];
		
		this.autoBox[0] = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.95)),
				(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.autoBox[1] = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.99)),
				(int) (this.topLeftCorner.y + (this.height*0.55))
		);
		this.refuseExitDungeon = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.60)),
				(int) (this.topLeftCorner.y + (this.height*0.70))
		);
	}
	
	@Override 
	protected void updateCoords(JSONObject configuration) {
		super.updateCoords(configuration);
		this.setDungeonCoords();
	}
	
	protected void state2() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.eroicoButton.x, this.eroicoButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}

	protected void state3() throws InterruptedException, AWTException {
		this.customRobot.sleep(1000);
		this.customRobot.mouseClick(this.accettaSquadraButton.x, this.accettaSquadraButton.y);
		this.state++;
		this.customRobot.sleep(1000);
		this.updateStepStartTime();
	}
	
	protected void state4() throws InterruptedException, AWTException {
		if(isCompleted()) {
			this.firstRun = false;
			this.restartGame();
			this.customRobot.sleep(1000);
		}else {
			this.customRobot.sleep(10000);
		}
	}
	protected abstract void state5() throws InterruptedException, AWTException;
	
	protected boolean isCompleted() throws InterruptedException, AWTException {
		boolean autoDisabled = true;
		int maxTries = 20;
		int currentTry = 0;
		refuseEventCheck();
		this.customRobot.sleep(1000);
		
		this.customRobot.send("{enter}");
		this.customRobot.sleep(500);

		while(autoDisabled && currentTry < maxTries) {
			if(!this.running) {
				return true;
			}
			if(this.customRobot.pixelSearch(this.autoBox[0].x, this.autoBox[0].y, this.autoBox[1].x, this.autoBox[1].y, this.autoBoxEnabledColor, 10) != null) {
				autoDisabled = false;
			}else {
				this.customRobot.sleep(500);
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
				currentTry++;
			}
		}
	   if(currentTry == maxTries) {
		  return true;
	   } else {
		  return false;
	   }
	}
	
	protected void refuseEventCheck() throws AWTException, InterruptedException {
		Point searchedPixel = this.customRobot.pixelSearch(this.topLeftCorner.x, this.topLeftCorner.y, this.bottomRightCorner.x, this.bottomRightCorner.y, this.refuseButtonColor, 10);
		if(searchedPixel != null){
			System.out.println("FOUND! at: ["+searchedPixel.x+", "+searchedPixel.y+"]");
			this.customRobot.mouseClick(searchedPixel.x, searchedPixel.y);
			this.customRobot.sleep(1000);
			this.customRobot.send("{enter}");
			this.customRobot.sleep(1000);
			this.customRobot.send("{esc}");
			this.customRobot.sleep(1000);
			this.customRobot.mouseMove(0, 0);
			this.customRobot.sleep(1000);
			searchedPixel = this.customRobot.pixelSearch(this.topLeftCorner.x, this.topLeftCorner.y, this.bottomRightCorner.x, this.bottomRightCorner.y, this.refuseButtonColor, 10);
			if(searchedPixel != null) {
				//Familar Capture
				this.customRobot.send("{enter}");
				this.customRobot.sleep(1000);
				this.customRobot.send("{enter}");
				this.customRobot.sleep(1000);
			}else {
				this.customRobot.mouseClick(this.refuseExitDungeon.x, this.refuseExitDungeon.y);
				this.customRobot.sleep(1000);
			}
		}else {
			System.out.println("NOT FOUND!");
		}
	}
	
	protected boolean stopDungeon(boolean loop) {
		return (!loop && !this.firstRun);
	}
	
	protected abstract void main() throws InterruptedException, AWTException;

	public void start(boolean loop) throws InterruptedException, AWTException {
		if(!this.enabled) {
			return;
		}
		this.firstRun = true;
		this.customRobot.sleep(1000);
		this.restartGame();
		this.running = true;
		while(running) {
			if(this.stopDungeon(loop)) {
				return;
			}
			this.main();
		}
	}
	
	public void stop() {
		System.out.println("Dungeon stop!");
		this.running = false;
	}
}

