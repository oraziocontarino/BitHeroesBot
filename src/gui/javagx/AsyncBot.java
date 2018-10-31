package gui.javagx;

import java.awt.AWTException;

import org.json.JSONObject;

import be.BitHeroesBot;
import global.Utils;

public class AsyncBot {
	private static AsyncBot instance;
	private Thread thread;
	private JSONObject configuration;
	private AsyncBot(JSONObject configuration) {
		this.configuration = configuration;
	}

	public static AsyncBot getInstance(JSONObject configuration){
		if(instance == null){
			instance = new AsyncBot(configuration);
		}
		//update configuration
		instance.setConfiguration(configuration);
		
		//return AsyncBot instance
		return instance;
	}

	public static AsyncBot getInstance(){
		return getInstance(Utils.getDefaultConfiguration());
	}
	
	public void setConfiguration(JSONObject configuration) {
		this.configuration = configuration;
	}
	
	public void run() {
		this.thread = new Thread(new Runnable() {
	        public void run(){
	        	try {
					BitHeroesBot.getInstance(configuration).run();
				} catch (AWTException | InterruptedException e) {
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
