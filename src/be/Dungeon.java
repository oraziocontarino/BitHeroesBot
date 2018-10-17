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
	
	protected Dungeon() {
		eroicoButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.75));
		eroicoButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.45));
		
		accettaSquadraButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.75));
		accettaSquadraButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.90));
		
		autoBox[0] = (int) (this.topLeftCorner[0] + (this.width*0.95));
		autoBox[1] = (int) (this.topLeftCorner[1] + (this.height*0.45));
		autoBox[2] = (int) (this.topLeftCorner[0] + (this.width*0.99));
		autoBox[3] = (int) (this.topLeftCorner[1] + (this.height*0.55));
		
		//autoBoxEnabledColor = "0x8dd61d";
		autoBoxEnabledColor = new Color(141, 214, 29);
		
		//refuseButtonColor = "0xf49745";
		autoBoxEnabledColor = new Color(244, 141, 53);
		
		refuseButton[0] = 0;
		refuseButton[1] = 0;
	}
	
	protected void state2() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(eroicoButton[0], eroicoButton[1]);
		this.state++;
		CustomRobot.getInstance().sleep(1000);
	}

	protected void state3() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(accettaSquadraButton[0], accettaSquadraButton[1]);
		this.state++;
		CustomRobot.getInstance().sleep(1000);
		this.updateStepStartTime();
	}
	
	protected void state4() throws InterruptedException, AWTException {
		if(isCompleted()) {
			this.restartGame();
			CustomRobot.getInstance().sleep(1000);
		}else {
			CustomRobot.getInstance().sleep(10000);
		}
	}
	protected boolean isCompleted() throws InterruptedException, AWTException {
		boolean autoDisabled = true;
		int maxTries = 20;
		int currentTry = 0;
		CustomRobot.getInstance().send("{enter}");
		CustomRobot.getInstance().sleep(500);
		
		while(autoDisabled && currentTry < maxTries) {
			if(CustomRobot.getInstance().pixelSearch(autoBox[0], autoBox[1], autoBox[2], autoBox[3], autoBoxEnabledColor)) {
				autoDisabled = false;
			}else {
				refuseEventCheck();
				CustomRobot.getInstance().send("{enter}");
				CustomRobot.getInstance().sleep(500);
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
		if(CustomRobot.getInstance().pixelSearch(this.topLeftCorner[0], this.topLeftCorner[1], this.bottomRightCorner[0], this.bottomRightCorner[1], refuseButtonColor)){
			CustomRobot.getInstance().mouseClick(refuseButton[0], refuseButton[1]);
			CustomRobot.getInstance().sleep(500);
		}
	}
	
}

