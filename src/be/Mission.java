package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;

public class Mission extends Dungeon {
	private Point missionButton;
	private Point ashvaleMission2;
	private Point bitValley2;
	private Point selectedMission;
	public Mission() throws AWTException{
		this.missionButton = new Point (
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.05))
		);
	
		this.ashvaleMission2 = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.30)),
				(int) (this.topLeftCorner.y + (this.height*0.35))
		);
		this.bitValley2 = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.73)),
				(int) (this.topLeftCorner.y + (this.height*0.25))
		);
		
		this.selectedMission = bitValley2;
	}
	
	
	private void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.missionButton.x, this.missionButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	private void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.selectedMission.x, this.selectedMission.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	private void state5() throws InterruptedException, AWTException {
		//System.out.println("restart! 0");
		this.customRobot.sleep(1000);
	}
	
	public void start() throws InterruptedException, AWTException {
		this.state=4;
		this.customRobot.sleep(1000);
		while(true) {
			//debug
			
			switch(this.state){
				case 0:
					System.out.println("state 0");
					//click "Mission" button
					this.state0();
				break;
				case 1:
					System.out.println("state 1");
					//click "Evoca" button
				this.state1();
				break;
					case 2:
					System.out.println("state 2");
					//click "Eroico" button
					this.state2();
				break;
					case 3:
					System.out.println("state 3");
					//click "Conferma squadra" button
					this.state3();
				break;
				case 4:
					System.out.println("state 4");
					//click "auto play dungeon"
					this.state4();
				break;
				case 5:
					System.out.println("test state");
					//this.customRobot.mouseMove(refuseExitDungeon.x, refuseExitDungeon.y);
					this.customRobot.getMousePosition();
					//this.refuseEventCheck();
					
					this.customRobot.sleep(1000);
					state5();
				break;
			}
			
		}
	}

}
