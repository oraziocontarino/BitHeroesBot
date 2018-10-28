package gui.javagx;

import java.awt.AWTException;

import org.json.JSONObject;

import be.BitHeroesBot;

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
		return instance;
	}

	public static AsyncBot getInstance(){
		return instance;
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
		thread.interrupt();
	}
	
	public void resume() {
		thread.notifyAll();
	}
	
	public void pause() throws InterruptedException {
		thread.wait();
	}
}
