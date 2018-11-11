package be;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;

import org.json.JSONObject;

import global.Utils;
import lib.CustomRobot;

class BitHeroesGlobal {
	protected int state;
	protected Color fishingBarColor;
	protected Color fishingCaputureColor;
	protected Point topLeftCorner;
	protected Point bottomRightCorner;
	protected int width; 
	protected int height;
	protected Point center;
	protected int stepStartTime;
	protected CustomRobot customRobot;
	protected BitHeroesGlobal() throws AWTException {
		customRobot = CustomRobot.getInstance();
		
		state = 0;
		
		fishingBarColor = new Color(35, 107, 167);

		fishingCaputureColor = new Color(77, 254, 0);

		this.updateCoords(Utils.getDefaultConfiguration());

		
		stepStartTime = 0;
	}
	
	protected void updateCoords(JSONObject configuration) {
		this.topLeftCorner = Utils.getGameTopLeftCorner(configuration);
		
		this.bottomRightCorner = Utils.getGameBottomRightCorner(configuration);
		
		this.width = Utils.getGameWidth(this.bottomRightCorner, this.topLeftCorner);
		this.height = Utils.getGameHeight(this.bottomRightCorner, this.topLeftCorner);
		
		this.center = Utils.getGameCenter(this.topLeftCorner, this.width, this.height);
	}
	public int getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public void restartGame() throws InterruptedException, AWTException {
		int tries = 5;
		this.customRobot.mouseClick(this.topLeftCorner.x+5, this.topLeftCorner.y+5);
		
		for(int i=0; i<tries; i++) {
			this.customRobot.sleep(500);
			this.customRobot.send("{Esc}");
		}
		this.state = 0;
		this.stepStartTime = this.getTimestamp();
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void nextStep() {
		this.state ++;
	}

	public Color getFishingBarColor() {
		return fishingBarColor;
	}

	public void setFishingBarColor(Color fishingBarColor) {
		this.fishingBarColor = fishingBarColor;
	}

	public Color getFishingCaputureColor() {
		return fishingCaputureColor;
	}

	public void setFishingCaputureColor(Color fishingCaputureColor) {
		this.fishingCaputureColor = fishingCaputureColor;
	}

	public Point getTopLeftCorner() {
		return topLeftCorner;
	}

	public void setTopLeftCorner(Point topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}

	public Point getBottomRightCorner() {
		return bottomRightCorner;
	}

	public void setBottomRightCorner(Point bottomRightCorner) {
		this.bottomRightCorner = bottomRightCorner;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getStepStartTime() {
		return stepStartTime;
	}

	public void setStepStartTime(int stepStartTime) {
		this.stepStartTime = stepStartTime;
	}
	
	public void updateStepStartTime() {
		this.stepStartTime = this.getTimestamp();
	}
}