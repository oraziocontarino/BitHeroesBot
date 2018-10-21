package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;
import lib.KeyBindingManager;

public class BitHeroesBot {
	private static BitHeroesBot instance;
	private Mission mission;
	private Raid raid;
	private boolean running;
	private BitHeroesBot(Point[] coords) throws AWTException, InterruptedException {
    	mission = new Mission(coords);
    	raid = new Raid(coords);
    	running = false;
	}
    
	public static BitHeroesBot getInstance(Point[] coords) throws AWTException, InterruptedException{
        if(instance == null){
            instance = new BitHeroesBot(coords);
        }
        return instance;
    }
	
	public void run() throws InterruptedException, AWTException {
		//CustomRobot.getInstance().detectGamePoistion();
    	running = true;
		while(running) {
    		//CustomRobot.getInstance().sleep(100);
			//System.out.println("test");
			
	    	System.out.println("starting raid");
	    	raid.start(false);
	    	if(!running) {
	    		return;
	    	}
	    	System.out.println("starting mission");
	    	mission.start(false);
	    	if(!running) {
	    		return;
	    	}
	    	System.out.println("Waiting 10 minutes...");
	    	CustomRobot.getInstance().sleep(10*60*1000);
	    	
    	}
	}
	
	public void stop() {
		this.running = false;
		raid.stop();
		mission.stop();
		System.out.println("Force stop!");
	}
	

}
