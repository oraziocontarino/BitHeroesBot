package be.events;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import be.BitHeroesGlobal;
import lib.CustomRobot;

public class Gauntlet extends BitHeroesGlobal {
	private Point gauntletButton;
	private Point costListButton;
	private Point difficultyListButton;
	private Point scrollTopButton;
	private Point scrollDownButton;
	private Point[] listOptionButton;
	//todo: move to bitHeroesGlobal

	public Gauntlet() throws AWTException {
		super();
		this.customRobot = CustomRobot.getInstance();
		
		this.gauntletButton = new Point();
		this.costListButton = new Point();
		this.difficultyListButton = new Point();
		this.scrollTopButton = new Point();
		this.scrollDownButton = new Point();
		this.listOptionButton = new Point[5];
		for(int i = 0; i < 5; i++) {
			this.listOptionButton[i] = new Point();
		}
		this.reset();
	}
	
	@Override
	protected void updateJobCoords() {
		if(this.gauntletButton == null) {
			this.gauntletButton = new Point();
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
		for(int i = 0; i < 5; i++) {
			if(this.listOptionButton[i] == null) {
				this.listOptionButton[i] = new Point();
			}
		}
		//TODO: map gauntlet buttons
		this.gauntletButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.75)),
			(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.costListButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.75)),
			(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.difficultyListButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.75)),
			(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.scrollTopButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.75)),
			(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		this.scrollDownButton.setLocation(
			(int) (this.topLeftCorner.x + (this.width*0.75)),
			(int) (this.topLeftCorner.y + (this.height*0.45))
		);
		for(int i = 0; i < 5; i++) {
			if(this.listOptionButton[i] == null) {
				this.listOptionButton[i].setLocation(
					(int) (this.topLeftCorner.x + (this.width*0.75)),
					(int) (this.topLeftCorner.y + (this.height*0.45))
				);
			}
		}
		
	}

	@Override
	protected void main() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
