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
	        		BitHeroesBot.getInstance().setConfiguration(configuration);
	        		BitHeroesBot.getInstance().updateBotConfiguration();
					BitHeroesBot.getInstance().run();
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
