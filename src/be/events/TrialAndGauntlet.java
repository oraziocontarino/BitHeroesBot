package be.events;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import org.json.JSONObject;

import be.BitHeroesGlobal;
import lib.CustomRobot;

public abstract class TrialAndGauntlet extends BitHeroesGlobal {
	private Point eventButton;
	private Point costListButton;
	private Point difficultyListButton;
	private Point scrollTopButton;
	private Point scrollDownButton;
	private Point[] listOptionButton;
	private Point closeListOptionButton;
	private Point startButton;
	private Point[] autoBox;
	protected int selectedCost;
	protected int selectedDifficulty;
	private Color autoBoxEnabledColor;
	protected Point acceptTeamButton;
	
	public TrialAndGauntlet() throws AWTException {
		super();
		this.customRobot = CustomRobot.getInstance();
		
		this.initInstances();
		this.updateJobCoords();
		this.selectedCost = 1;
		this.enabled = true;
		this.reset();
	}
	private void initInstances() {
		if(this.eventButton == null) {
			this.eventButton = new Point();
		}
		if(this.costListButton == null) {
			this.costListButton = new Point();
		}
		if(this.difficultyListButton == null) {
			this.difficultyListButton = new Point();
		}
		if(this.scrollTopButton == null) {
			this.scrollTopButton = new Point();
		}
		if(this.scrollDownButton == null) {
			this.scrollDownButton = new Point();
		}
		if(this.listOptionButton == null) {
			this.listOptionButton = new Point[5];
		}
		for(int i = 0; i < 5; i++) {
			if(this.listOptionButton[i] == null) {
				this.listOptionButton[i] = new Point();
			}
		}
		if(this.closeListOptionButton == null) {
			this.closeListOptionButton = new Point();
		}
		if(this.autoBox == null) {
			this.autoBox = new Point[2];
		}
		for(int i = 0; i < 2; i++) {
			if(this.autoBox[i] == null) {
				this.autoBox[i] = new Point();
			}
		}
		if(this.autoBoxEnabledColor == null) {
			this.autoBoxEnabledColor = new Color(141, 214, 29);
		}
		if(this.startButton == null) {
			this.startButton = new Point();
		}
		if(this.acceptTeamButton == null) {
			this.acceptTeamButton = new Point();
		}

	}
	@Override
	protected void updateJobCoords() {
		this.initInstances();
		this.eventButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.95)),
			(int) (this.topLeftCorner.y + (this.height*0.65))
		);
		this.costListButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.65)),
			(int) (this.topLeftCorner.y + (this.height*0.40))
		);
		this.difficultyListButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.65)),
			(int) (this.topLeftCorner.y + (this.height*0.72))
		);
		this.scrollTopButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.67)),
			(int) (this.topLeftCorner.y + (this.height*0.26))
		);
		this.scrollDownButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.67)),
			(int) (this.topLeftCorner.y + (this.height*0.835))
		);
		this.listOptionButton[0].setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.50)),
			(int) (this.topLeftCorner.y + (this.height*0.77))
		);
		this.listOptionButton[1].setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.50)),
			(int) (this.topLeftCorner.y + (this.height*0.65))
		);
		this.listOptionButton[2].setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.50)),
			(int) (this.topLeftCorner.y + (this.height*0.53))
		);
		this.listOptionButton[3].setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.50)),
			(int) (this.topLeftCorner.y + (this.height*0.41))
		);
		this.listOptionButton[4].setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.50)),
				(int) (this.topLeftCorner.y + (this.height*0.32))
		);
		this.closeListOptionButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.70)),
			(int) (this.topLeftCorner.y + (this.height*0.15))
		);
		this.autoBox[0].setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.95)),
				(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.autoBox[1].setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.99)),
				(int) (this.topLeftCorner.y + (this.height*0.55))
		);		
		this.startButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.70)),
				(int) (this.topLeftCorner.y + (this.height*0.50))
		);	
		this.acceptTeamButton.setLocation(
				(int) (this.topLeftCorner.x + (this.width*0.75)),
				(int) (this.topLeftCorner.y + (this.height*0.90))
		);	
	}
	
	@Override
	public void updateConfiguration(JSONObject configuration) {
		try {
			this.updateCoords(configuration);
			this.updateCost(configuration);
			this.updateDifficulty(configuration);
			this.updateEnabledStatus(configuration);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void state0() throws InterruptedException {
		this.customRobot.mouseClick(this.eventButton.x, this.eventButton.y);
		this.customRobot.sleep(1000);
		this.state++;
	}
	private void state1() throws InterruptedException {
		//cost
		this.customRobot.mouseClick(this.costListButton.x, this.costListButton.y);
		this.customRobot.sleep(1000);
		this.state++;
	}
	private void state2() throws InterruptedException {
		//cost
		this.customRobot.mouseClick(this.listOptionButton[5-this.selectedCost].x, this.listOptionButton[5-this.selectedCost].y);
		this.customRobot.sleep(1500);

		this.customRobot.mouseClick(this.closeListOptionButton.x, this.closeListOptionButton.y);
		this.customRobot.sleep(500);
		this.state++;
	}
	private void state3() throws InterruptedException {
		//difficulty
		this.customRobot.mouseClick(this.difficultyListButton.x, this.difficultyListButton.y);
		this.customRobot.sleep(500);
		this.state++;
	}
	private void state4() throws InterruptedException {
		//difficulty
		this.customRobot.mouseClick(this.scrollDownButton.x,  (int) (this.scrollDownButton.y*0.98));
		this.customRobot.sleep(250);
		this.customRobot.mouseClick(this.scrollDownButton.x,  (int) (this.scrollDownButton.y*0.98));
		this.customRobot.sleep(250);
		this.customRobot.mouseClick(this.scrollDownButton.x,  (int) (this.scrollDownButton.y*0.98));
		this.customRobot.sleep(500);
		for(int i=0; i<10; i++) {
			if(this.running <= 0) {
				return;
			}
			this.customRobot.mouseClick(this.scrollDownButton.x, this.scrollDownButton.y);
			this.customRobot.sleep(250);
		}
		this.state++;
	}
	private void state5() throws InterruptedException, AWTException {
		if(this.selectedDifficulty == 0 || this.selectedCost == 0) {
			this.stop();
			this.restartGame();
			return;
		}
		
		if(this.selectedDifficulty <=5) {
			this.customRobot.mouseClick(this.listOptionButton[this.selectedDifficulty-1].x, this.listOptionButton[this.selectedDifficulty-1].y);
			this.customRobot.sleep(500);
		}else {
			int clicks = this.selectedDifficulty - 5;
			for(int i = 0; i < clicks; i++) {
				if(this.running <= 0) {
					return;
				}
				this.customRobot.mouseClick(this.scrollTopButton.x, this.scrollTopButton.y);
				this.customRobot.sleep(250);
			}
			this.customRobot.sleep(500);
			this.customRobot.mouseClick(this.listOptionButton[this.selectedCost-1].x, this.listOptionButton[this.selectedCost-1].y);
			this.customRobot.sleep(500);
		}
		this.customRobot.sleep(500);
		this.customRobot.mouseClick(this.closeListOptionButton.x, this.closeListOptionButton.y);
		this.customRobot.sleep(500);
		this.state++;
	}
	private void state6() throws InterruptedException {
		this.customRobot.mouseClick(this.startButton.x, this.startButton.y);
		this.customRobot.sleep(500);
		this.state++;
	}
	
	protected void state7() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.acceptTeamButton.x, this.acceptTeamButton.y);
		this.customRobot.sleep(500);
		this.state++;
	}
	protected void state8() throws InterruptedException, AWTException {
		if(isCompleted()) {
			this.firstRun = false;
			this.restartGame();
			this.customRobot.sleep(1000);
		}else {
			this.customRobot.sleep(10000);
		}
	}
	@Override
	public void main() throws Exception{
		switch(this.state){
			case 0:
				System.out.println("state 0");
				//click "Trial/Gauntlet" button
				this.state0();
			break;
			case 1:
				//click cost
				System.out.println("state 1");
				this.state1();
			break;
			case 2:
				//select cost
				System.out.println("state 2");
				this.state2();
			break;
			case 3:
				//click difficulty
				System.out.println("state 3");
				this.state3();
			break;
			case 4:
				System.out.println("state 4");
				//scroll down difficulty
				state4();
			break;
			case 5:
				//select difficulty
				System.out.println("state 5");
				state5();
			break;
			case 6:
				//click on play button
				System.out.println("state 6");
				state6();
				this.customRobot.sleep(5000);
			break;
			case 7:
				//accept team
				System.out.println("state 7");
				state7();
			break;
			case 8:
				//wait untill end of dungeon
				System.out.println("state 8");
				state8();
			break;
			default:
				System.out.println("DEFAULT");
				this.customRobot.sleep(5000);
				
		}
	}
	protected boolean isCompleted() throws InterruptedException, AWTException {
		boolean autoDisabled = true;
		int maxTries = 20;
		int currentTry = 0;
		this.customRobot.sleep(1000);
		
		this.customRobot.send("{enter}");
		this.customRobot.sleep(500);

		while(autoDisabled && currentTry < maxTries) {
			if(this.running <= 0) {
				return true;
			}
			if(this.customRobot.pixelSearch(this.autoBox[0].x, this.autoBox[0].y, this.autoBox[1].x, this.autoBox[1].y, this.autoBoxEnabledColor, 10) != null) {
				autoDisabled = false;
			}else {
				this.customRobot.sleep(500);
				this.customRobot.send("{enter}");
				this.customRobot.sleep(500);
				currentTry++;
			}
		}
	   if(currentTry == maxTries) {
		  return true;
	   } else {
		  return false;
	   }
	}
	protected abstract void updateCost(JSONObject configuration);
	protected abstract void updateDifficulty(JSONObject configuration);



}
