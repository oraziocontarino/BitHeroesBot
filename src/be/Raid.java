package be;

import java.awt.AWTException;

import lib.CustomRobot;

public class Raid extends Dungeon {
	private int[] raidButton;
	private int[] evocaButton;
			
	private Raid()  throws AWTException{
		this.raidButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.05));
		this.raidButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.65));
		
		this.evocaButton[0] = (int) (this.topLeftCorner[0] + (this.width*0.65));
		this.evocaButton[1] = (int) (this.topLeftCorner[1] + (this.height*0.75));
	}
	

	public void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.raidButton[0], this.raidButton[1]);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	public void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.evocaButton[0], this.evocaButton[1]);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	public void state5() throws InterruptedException, AWTException {
		System.out.println("restart! 0");
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	
	public void start() throws InterruptedException, AWTException {
		while(true) {
			switch(this.state){
				case 0:
					System.out.println("state 0");
					//click "Raid" button
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
		}
	}
}
