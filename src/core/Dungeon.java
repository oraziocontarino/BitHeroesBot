package core;

import java.awt.AWTException;
import java.awt.Color;

public class Dungeon {
	private String refuseButtonColor = "0xf49745";
	private int[] eroicoButton;
	private int[] accettaSquadraButton;
	private int[] autoBox;
	private String autoBoxEnabledColor;
	private int[] refuseButton;
	
	private Dungeon() {
		eroicoButton[0] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[0] + (BitHeroesGlobal.getInstance().getWidth()*0.75));
		eroicoButton[1] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[1] + (BitHeroesGlobal.getInstance().getHeight()*0.45));
		accettaSquadraButton[0] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[0] + (BitHeroesGlobal.getInstance().getWidth()*0.75));
		accettaSquadraButton[1] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[1] + (BitHeroesGlobal.getInstance().getHeight()*0.90));
		autoBox[0] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[0] + (BitHeroesGlobal.getInstance().getWidth()*0.95));
		autoBox[1] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[1] + (BitHeroesGlobal.getInstance().getHeight()*0.45));
		autoBox[2] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[0] + (BitHeroesGlobal.getInstance().getWidth()*0.99));
		autoBox[3] = (int) (BitHeroesGlobal.getInstance().getTopLeftCorner()[1] + (BitHeroesGlobal.getInstance().getHeight()*0.55));
		autoBoxEnabledColor = "0x8dd61d";
		//TODO: set coords
		refuseButton[0] = 0;
		refuseButton[1] = 0;
	}
	
	protected void state2() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(eroicoButton[0], eroicoButton[1]);
		BitHeroesGlobal.getInstance().nextStep();
		CustomRobot.getInstance().sleep(1000);
	}

	protected void state3() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(accettaSquadraButton[0], accettaSquadraButton[1]);
		BitHeroesGlobal.getInstance().nextStep();
		CustomRobot.getInstance().sleep(1000);
		BitHeroesGlobal.getInstance().updateStepStartTime();
	}
	
	protected void state4() throws InterruptedException, AWTException {
		if(isCompleted()) {
			BitHeroesGlobal.getInstance().restartGame();
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
			//local acoord = pixelsearch(autoBox[0], autoBox[1], autoBox[2], autoBox[3], autoBoxEnabledColor)
			//TODO: implement set color, or re-check color and set as constants!
			Color pixel = new Color(255);
			//pixel.setColor(autoBoxEnabledColor);
			if(CustomRobot.getInstance().pixelSearch(autoBox[0], autoBox[1], autoBox[2], autoBox[3], pixel)) {
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
	
	private void refuseEventCheck() throws AWTException, InterruptedException {
		//local acoord = pixelsearch(BitHeroesGlobal.getInstance().getTopLeftCorner()[0], BitHeroesGlobal.getInstance().getTopLeftCorner()[1], BitHeroesGlobal.getInstance().getBottomRightCorner()[0], BitHeroesGlobal.getInstance().getBottomRightCorner()[1], refuse_button_color, 10, 5)
		
		//TODO: implement set color, or re-check color and set as constants!
		Color pixel = new Color(255);
		//pixel.setColor(refuse_button_color);
		
		if(
			CustomRobot.getInstance().pixelSearch(
				BitHeroesGlobal.getInstance().getTopLeftCorner()[0], 
				BitHeroesGlobal.getInstance().getTopLeftCorner()[1], 
				BitHeroesGlobal.getInstance().getBottomRightCorner()[0], 
				BitHeroesGlobal.getInstance().getBottomRightCorner()[1], 
				pixel
			)
		){
			CustomRobot.getInstance().mouseClick(refuseButton[0], refuseButton[1]);
			CustomRobot.getInstance().sleep(500);
		}
	}
	
}

