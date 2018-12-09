package be.dungeons;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import org.json.JSONObject;

import be.BitHeroesGlobal;
import lib.CustomRobot;

public abstract class Dungeon extends BitHeroesGlobal {
	protected Color refuseButtonColor;
	protected Point eroicoButton;
	protected Point acceptTeamButton;
	protected Point selectorPrevButton;
	protected Point selectorNextButton;
	protected Point[] autoBox;
	protected Color autoBoxEnabledColor;
	protected CustomRobot customRobot;
	protected Point refuseExitDungeon;
	
	protected Dungeon() throws AWTException {
		super();
		this.customRobot = CustomRobot.getInstance();
		
		//autoBoxEnabledColor = "0x8dd61d";
		this.autoBoxEnabledColor = new Color(141, 214, 29);
		
		//refuseButtonColor = "0xf49745";
		this.refuseButtonColor = new Color(244, 141, 53);
		this.selectorPrevButton = new Point();
		this.selectorNextButton = new Point();
		
		this.updateJobCoords();
		this.reset();
		
	}

	@Override
	protected void updateJobCoords() {
		this.eroicoButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.75)),
				(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		
		this.acceptTeamButton = new Point(
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
	
	protected void state2() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.eroicoButton.x, this.eroicoButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}

	protected void state3() throws InterruptedException, AWTException {
		this.customRobot.sleep(1000);
		this.customRobot.mouseClick(this.acceptTeamButton.x, this.acceptTeamButton.y);
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
	
	
	protected boolean isCompleted() throws InterruptedException, AWTException {
		int maxTries = 5;		
		boolean isAutoPlaying = false;
		boolean wasAutoPlaying = false; 
		for(int i = 0; i<maxTries; i++) {

			//Init autoplay status
			wasAutoPlaying = this.isAutoPlaying();
			
			//Force change autoplay status
			this.customRobot.send("{enter}");
			this.customRobot.sleep(500);
			
			//Familiar capture
			for(int j=0; j < 3; j++) {
				isAutoPlaying = this.isAutoPlaying();
				if(wasAutoPlaying != isAutoPlaying) {
					break;
				}
				
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);	
				wasAutoPlaying = isAutoPlaying;
			}
			
			//Verify status, ensure state = auto (green box)
			if(!isAutoPlaying) {
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
			}
			//Found autoplay enabled, dungeon is running
			if(this.isAutoPlaying()) {
				return isCompletedCounter() || false;
			}
		}
		
		//Found autoplay not enabled, dungeon has been completed
		return isCompletedCounter() || true;
	}
	
	private boolean isAutoPlaying() {
		return this.customRobot.pixelSearch(this.autoBox[0].x, this.autoBox[0].y, this.autoBox[1].x, this.autoBox[1].y, this.autoBoxEnabledColor, 10) != null;
	}
	
	private boolean isCompletedCounter() {
		return this.running <= 0;
	}
	
	protected void refuseEventCheck() throws AWTException, InterruptedException {
//TODO: refactorize merchant refuse algoritm, rightnow it causes equipment swap issue due to random "searchpixel" click
/*
		Point searchedPixel = this.customRobot.pixelSearch(this.topLeftCorner.x, this.topLeftCorner.y, this.bottomRightCorner.x, this.bottomRightCorner.y, this.refuseButtonColor, 10);
		if(searchedPixel != null){
			//System.out.println("FOUND! at: ["+searchedPixel.x+", "+searchedPixel.y+"]");
			super.closeItemBoxes();
			this.customRobot.mouseClick(searchedPixel.x, searchedPixel.y);
			this.customRobot.sleep(1000);
			this.customRobot.send("{enter}");
			this.customRobot.sleep(1000);
			this.customRobot.send("{esc}");
			this.customRobot.sleep(1000);
			this.customRobot.mouseMove(0, 0);
			this.customRobot.sleep(1000);
			super.closeItemBoxes();
			searchedPixel = this.customRobot.pixelSearch(this.topLeftCorner.x, this.topLeftCorner.y, this.bottomRightCorner.x, this.bottomRightCorner.y, this.refuseButtonColor, 10);
			if(searchedPixel != null) {
				//Familar Capture
				this.customRobot.send("{enter}");
				this.customRobot.sleep(1000);
				this.customRobot.send("{enter}");
				this.customRobot.sleep(1000);
				//last or first enter may be missed, try again (faster then before) to ensure capture
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
			}else {
				this.customRobot.mouseClick(this.refuseExitDungeon.x, this.refuseExitDungeon.y);
				this.customRobot.sleep(1000);
			}
		}
		*/
	}
	
	
	public abstract void updateConfiguration(JSONObject configuration);
	protected abstract void state0() throws InterruptedException, AWTException;
	protected abstract void state1() throws InterruptedException, AWTException;
	protected abstract void state5() throws InterruptedException, AWTException;
	
	protected abstract void updateEnabledStatus(JSONObject configuration);
}

