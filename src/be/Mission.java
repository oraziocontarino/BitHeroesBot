package be;

import java.awt.AWTException;
import java.awt.Point;

import org.json.JSONObject;

public class Mission extends Dungeon {
	private Point missionButton;
	private Point selectedMission;
	private MissionList missionList;
	
	public Mission() throws AWTException{
		super();
		this.setMissionCoords();
	}
	
	private void setMissionCoords() {
		this.missionButton = new Point (
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.05))
		);

		this.missionList = new MissionList(this.topLeftCorner, this.width, this.height);
	}
	
	@Override 
	public void updateCoords(JSONObject configuration) {
		super.updateCoords(configuration);
		this.setMissionCoords();
	}

	public void setMission(JSONObject configuration) {
		String key = configuration.getJSONObject("selectedMission").getString("id");
		this.selectedMission = this.missionList.getMission(key);
		System.out.println("Mission set to: "+key);
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
