package be;

import java.awt.AWTException;
import java.awt.Color;

import lib.CustomRobot;

public class Dungeon extends BitHeroesGlobal {
	protected Color refuseButtonColor;
	protected int[] eroicoButton;
	protected int[] accettaSquadraButton;
	protected int[] autoBox;
	protected Color autoBoxEnabledColor;
	protected int[] refuseButton;
	protected CustomRobot customRobot;
	protected Dungeon() throws AWTException {
		this.customRobot = CustomRobot.getInstance();
		
		this.eroicoButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.75));
		this.eroicoButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.45));
		
		this.accettaSquadraButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.75));
		this.accettaSquadraButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.90));
		
		this.autoBox[0] = (int) (this.topLeftCorner[0] + (this.width*0.95));
		this.autoBox[1] = (int) (this.topLeftCorner[1] + (this.height*0.45));
		this.autoBox[2] = (int) (this.topLeftCorner[0] + (this.width*0.99));
		this.autoBox[3] = (int) (this.topLeftCorner[1] + (this.height*0.55));
		
		//autoBoxEnabledColor = "0x8dd61d";
		this.autoBoxEnabledColor = new Color(141, 214, 29);
		
		//refuseButtonColor = "0xf49745";
		this.autoBoxEnabledColor = new Color(244, 141, 53);
		
		this.refuseButton[0] = 0;
		this.refuseButton[1] = 0;
		
	}
	
	protected void state2() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.eroicoButton[0], this.eroicoButton[1]);
		this.state++;
		this.customRobot.sleep(1000);
	}

	protected void state3() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.accettaSquadraButton[0], this.accettaSquadraButton[1]);
		this.state++;
		this.customRobot.sleep(1000);
		this.updateStepStartTime();
	}
	
	protected void state4() throws InterruptedException, AWTException {
		if(isCompleted()) {
			this.restartGame();
			this.customRobot.sleep(1000);
		}else {
			this.customRobot.sleep(10000);
		}
	}
	protected boolean isCompleted() throws InterruptedException, AWTException {
		boolean autoDisabled = true;
		int maxTries = 20;
		int currentTry = 0;
		this.customRobot.send("{enter}");
		this.customRobot.sleep(500);
		
		while(autoDisabled && currentTry < maxTries) {
			if(this.customRobot.pixelSearch(this.autoBox[0], this.autoBox[1], this.autoBox[2], this.autoBox[3], this.autoBoxEnabledColor)) {
				autoDisabled = false;
			}else {
				refuseEventCheck();
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
			}
		}
	   if(currentTry == maxTries) {
		  return true;
	   } else {
		  return false;
	   }
	}
	
	protected void refuseEventCheck() throws AWTException, InterruptedException {
		//local acoord = pixelsearch(this.topLeftCorner[0], this.topLeftCorner[1], this.bottomRightCorner[0], this.bottomRightCorner[1], refuse_button_color, 10, 5)
		if(this.customRobot.pixelSearch(this.topLeftCorner[0], this.topLeftCorner[1], this.bottomRightCorner[0], this.bottomRightCorner[1], this.refuseButtonColor)){
			this.customRobot.mouseClick(this.refuseButton[0], this.refuseButton[1]);
			this.customRobot.sleep(500);
		}
	}
	
}

