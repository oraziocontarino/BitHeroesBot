package be;

import java.awt.AWTException;

import lib.CustomRobot;

public class Mission extends Dungeon {
private int[] missionButton;
private int[] ashvaleMission2;

	public Mission()  throws AWTException{
		this.missionButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.05));
		this.missionButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.05));
	
		this.ashvaleMission2[0] = (int) (this.topLeftCorner[0] + (this.width*0.30));
		this.ashvaleMission2[1] = (int) (this.topLeftCorner[1] + (this.height*0.35));
	}
	
	
	private void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.missionButton[0], this.missionButton[1]);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	private void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.ashvaleMission2[0], this.ashvaleMission2[1]);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	private void state5() throws InterruptedException, AWTException {
		System.out.println("restart! 0");
		this.customRobot.sleep(1000);
	}
	
	public void start() throws InterruptedException, AWTException {
		while(true) {
			//debug
			this.customRobot.getMousePosition();
			/*
			switch(this.state){
				case 0:
					System.out.println("state 0");
					//click "Mission" button
					this.state0();
				break;
				case 1:System.out.println("state 1");
					//click "Evoca" button
				this.state1();
				break;
					case 2:System.out.println("state 2");
					//click "Eroico" button
					this.state2();
				break;
					case 3:System.out.println("state 3");
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
					state5();
				break;
			}
			*/
		}
	}

}
