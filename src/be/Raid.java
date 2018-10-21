package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;

public class Raid extends Dungeon {
	private Point raidButton;
	private Point evocaButton;
			
	public Raid(Point[] coords) throws AWTException{
		super(coords);
		this.raidButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.65))
		);
		
		this.evocaButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.65)),
				(int) (this.topLeftCorner.y + (this.height*0.75))
		);
	}
	
	@Override
	protected void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.raidButton.x, this.raidButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	protected void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.evocaButton.x, this.evocaButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	protected void state5() throws InterruptedException, AWTException {
		System.out.println("restart! 0");
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	public void main() throws InterruptedException, AWTException {
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
