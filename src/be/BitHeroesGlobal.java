package be;

import java.awt.AWTException;
import java.awt.Color;

import lib.CustomRobot;

public class BitHeroesGlobal {
	protected int state;
	protected Color fishingBarColor;
	protected Color fishingCaputureColor;
	protected int[] topLeftCorner;
	protected int[] bottomRightCorner;
	protected int width; 
	protected int height;
	protected int[] center;
	protected int stepStartTime;
	
		
	protected BitHeroesGlobal() {
		state = 0;
		//Color "236BA7"
		fishingBarColor = new Color(35, 107, 167);
		
		//Color "0x4DFE00"
		fishingCaputureColor = new Color(77, 254, 0);
		
		topLeftCorner[0] = 18;
		topLeftCorner[1] = 458;
		
		bottomRightCorner[0] = 817;
		bottomRightCorner[1] = 970;
		
		width = bottomRightCorner[0] - topLeftCorner[0];
		height = bottomRightCorner[1] - topLeftCorner[1];
		
		center[0] = (int) (topLeftCorner[0] + (width*0.50));
		center[1] = (int) (topLeftCorner[1] + (height*0.50));
		
		stepStartTime = 0;
	}
	
	public int getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public void restartGame() throws InterruptedException, AWTException {
		CustomRobot.getInstance().send("{Esc}");
		CustomRobot.getInstance().sleep(500);
		CustomRobot.getInstance().send("{Esc}");
		CustomRobot.getInstance().sleep(500);
		CustomRobot.getInstance().send("{Esc}");
		CustomRobot.getInstance().sleep(500);
		CustomRobot.getInstance().send("{Esc}");
		CustomRobot.getInstance().sleep(500);
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

	public int[] getTopLeftCorner() {
		return topLeftCorner;
	}

	public void setTopLeftCorner(int[] topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}

	public int[] getBottomRightCorner() {
		return bottomRightCorner;
	}

	public void setBottomRightCorner(int[] bottomRightCorner) {
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

	public int[] getCenter() {
		return center;
	}

	public void setCenter(int[] center) {
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