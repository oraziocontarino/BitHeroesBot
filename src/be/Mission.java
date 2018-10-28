package be;

import java.awt.AWTException;
import java.awt.Point;
import java.util.HashMap;

import lib.CustomRobot;

public class Mission extends Dungeon {
	private Point missionButton;
	private Point selectedMission;
	private String defaultKey;
	private HashMap<String, Point> missionList= new HashMap<String, Point>();
	
	public Mission(Point[] coords) throws AWTException{
		super(coords);
		
		this.missionButton = new Point (
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.05))
		);

		this.defaultKey = "Z2D2";
		
		this.missionList.put(
				"Z2D2", 
				new Point(
					(int) (this.topLeftCorner.x + (this.width*0.30)),
					(int) (this.topLeftCorner.y + (this.height*0.35))
				)
		);
		
		this.missionList.put(
				"Z1D2", 
				new Point(
					(int) (this.topLeftCorner.x + (this.width*0.73)),
					(int) (this.topLeftCorner.y + (this.height*0.25))
				)
		);
		
		this.selectMission(this.defaultKey);
	}
	
	public void selectMission(String missionKey) {
		missionKey = missionKey.trim().toUpperCase();
		this.defaultKey = this.defaultKey.trim().toUpperCase();
		this.selectedMission = this.missionList.containsKey(missionKey) ? this.missionList.get(missionKey) : this.missionList.get(defaultKey);
	}
	
	public void changeMission(String missionKey) {
		if(missionKey == null) {
			missionKey = this.defaultKey;
		}
		this.selectMission(missionKey);
	}
	
	@Override
	protected void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.missionButton.x, this.missionButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	protected void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.selectedMission.x, this.selectedMission.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	protected void state5() throws InterruptedException, AWTException {
		//System.out.println("restart! 0");
		this.customRobot.sleep(1000);
	}
	
	@Override
	public void main() throws InterruptedException, AWTException {
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
