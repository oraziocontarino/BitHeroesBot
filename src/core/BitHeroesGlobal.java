package core;

import java.awt.AWTException;

public class BitHeroesGlobal {
	private int state;
	private String fishingBarColor;
	private String fishingCaputureColor;
	private int[] topLeftCorner;
	private int[] bottomRightCorner;
	private int width; 
	private int height;
	private int[] center;
	private int stepStartTime;
	
	private static BitHeroesGlobal instance;
		
	private BitHeroesGlobal() {
		state = 0;
		fishingBarColor = "236BA7";
		fishingCaputureColor = "0x4DFE00";
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
	
	public static BitHeroesGlobal getInstance(){
        if(instance == null){
            instance = new BitHeroesGlobal();
        }
        return instance;
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

	public String getFishingBarColor() {
		return fishingBarColor;
	}

	public void setFishingBarColor(String fishingBarColor) {
		this.fishingBarColor = fishingBarColor;
	}

	public String getFishingCaputureColor() {
		return fishingCaputureColor;
	}

	public void setFishingCaputureColor(String fishingCaputureColor) {
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