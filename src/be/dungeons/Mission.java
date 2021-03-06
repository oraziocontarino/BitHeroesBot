package be.dungeons;

import java.awt.AWTException;
import java.awt.Point;
import org.json.JSONObject;

public class Mission extends Dungeon {
	private Point missionButton;
	private Point selectedMission;
	private String selectedZone;
	private Point enterDungeonButton;
	
	public Mission() throws AWTException{
		super();
		this.missionButton = new Point();
		this.selectedMission = new Point();
		this.enterDungeonButton = new Point();
		this.updateJobCoords();
	}
	
	@Override
	protected void updateJobCoords() {
		super.updateJobCoords();
		if(this.missionButton == null) {
			this.missionButton = new Point();
		}
		if(this.selectedMission == null) {
			this.selectedMission = new Point();
		}
		if(this.selectorPrevButton == null) {
			this.selectorPrevButton = new Point();
		}
		if(this.selectorNextButton == null) {
			this.selectorNextButton = new Point();
		}
		if(this.enterDungeonButton == null) {
			this.enterDungeonButton = new Point();
		}
		this.missionButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.05))
		);
		this.selectorPrevButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.07)),
				(int) (this.topLeftCorner.y + (this.height*0.53))
		);
		this.selectorNextButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.92)),
				(int) (this.topLeftCorner.y + (this.height*0.53))
		);
		this.enterDungeonButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.55)),
				(int) (this.topLeftCorner.y + (this.height*0.88))
		);
		
	}
    
	@Override
	protected void updateEnabledStatus(JSONObject configuration) {
		this.enabled = configuration.getJSONObject("stack").getBoolean("mission");
	}

	private void setMission(JSONObject configuration) throws Exception {
		this.selectedMission = MissionList.getMission(configuration);
		this.selectedZone = MissionList.getZone(configuration);
	}
	
	@Override
	public void updateConfiguration(JSONObject configuration) {
		try {
			this.updateCoords(configuration);
			this.setMission(configuration);
			this.updateEnabledStatus(configuration);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.missionButton.x, this.missionButton.y);
		this.state++;
		this.customRobot.sleep(500);
		for(int i = 0; i < 10; i++) {
			this.customRobot.mouseClick(this.selectorPrevButton.x, this.selectorPrevButton.y);
			this.customRobot.sleep(500);
		}
		String tmp = this.selectedZone.split("Z")[1];
		int index = Integer.valueOf(tmp);
		for(int i = 0; i < index-1; i++) {
			this.customRobot.mouseClick(this.selectorNextButton.x, this.selectorNextButton.y);
			this.customRobot.sleep(500);
		}
	}
	
	@Override
	protected void state1() throws InterruptedException, AWTException {
		System.out.println("Current mission: "+this.selectedMission);
		this.customRobot.mouseClick(this.selectedMission.x, this.selectedMission.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	protected void state2() throws InterruptedException, AWTException {
		if(this.selectedZone.endsWith("Z4")){
			state2Z4();
		}else {
			super.state2();
		}
	}
	
	@Override
	protected void state5() throws InterruptedException, AWTException {
		//System.out.println("restart! 0");
		this.customRobot.sleep(1000);
	}
	
	private void state2Z4() throws InterruptedException {
		this.customRobot.mouseClick(this.enterDungeonButton.x, this.enterDungeonButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	
	@Override
	public void main() throws Exception{
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
