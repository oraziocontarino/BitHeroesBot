package be;

import java.awt.AWTException;

import lib.CustomRobot;

public class Mission extends Dungeon {
private int[] missionButton;
private int[] ashvaleMission2;

	public Mission() {
		missionButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.05));
		missionButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.05));
	
		ashvaleMission2[0] = (int) (this.topLeftCorner[0] + (this.width*0.30));
		ashvaleMission2[1] = (int) (this.topLeftCorner[1] + (this.height*0.35));
	}
	
	
	private void state0() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(missionButton[0], missionButton[1]);
		this.state++;
		CustomRobot.getInstance().sleep(1000);
	}
	
	private void state1() throws InterruptedException, AWTException {
		CustomRobot.getInstance().mouseClick(ashvaleMission2[0], ashvaleMission2[1]);
		this.state++;
		CustomRobot.getInstance().sleep(1000);
	}
	
	private void state5() throws InterruptedException, AWTException {
		System.out.println("restart! 0");
		CustomRobot.getInstance().sleep(1000);
	}
	
	public void start() throws InterruptedException, AWTException {
		while(true) {
			//debug
			CustomRobot.getInstance().getMousePosition();
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
