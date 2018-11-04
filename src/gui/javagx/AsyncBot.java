package gui.javagx;

import java.awt.AWTException;
import org.json.JSONObject;
import be.BitHeroesBot;

public class AsyncBot {
	private static AsyncBot instance;
	private Thread thread;
	private JSONObject configuration;
	private AsyncBot() {}

	public static AsyncBot getInstance(){
		if(instance == null){
			instance = new AsyncBot();
		}
		//return AsyncBot instance
		return instance;
	}
	
	public void setConfiguration(JSONObject configuration) {
		this.configuration = configuration;
	}
	
	public void run() {
		this.thread = new Thread(new Runnable() {
	        public void run(){
	        	try {
	        		System.out.println("Setting configuration...");
	        		BitHeroesBot.getInstance().setConfiguration(configuration);
	        		System.out.println("Updating configuration...");
	        		BitHeroesBot.getInstance().updateBotConfiguration();
	        		System.out.println("Running bot...");
					BitHeroesBot.getInstance().run();
				} catch (Exception e) {
					System.out.println("Error occurred while starting bot!");
					e.printStackTrace();
				}
	        }
	    });
		thread.start();
	}
	
	public void interrupt() {
		if(this.isAlive()) {
			thread.interrupt();
		}
	}
	
	public void resume() {
		if(this.isAlive()) {
			thread.notifyAll();
		}
	}
	
	public void pause() throws InterruptedException {
		if(this.isAlive()) {
			thread.wait();
		}
	}
	
	private boolean isAlive() {
		return (this.thread != null && this.thread.isAlive());
	}
}
