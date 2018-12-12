package be;

import org.json.JSONObject;

import lib.CustomRobot;

public class AsyncBot {
	private static AsyncBot instance;
	private Thread bitHeroesBotThread;
	private Thread watchdogThread;
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
	
	private void runBitHeroesBotThread() {
		if(this.bitHeroesBotThread != null && this.bitHeroesBotThread.isAlive()) {
			this.bitHeroesBotThread.interrupt();
		}
		this.bitHeroesBotThread = new Thread(new Runnable() {
	        public void run(){
	        	try {
	        		//System.out.println("Setting configuration...");
	        		//BitHeroesBot.getInstance().updateBotConfiguration(configuration);
	        		System.out.println("Running bot...");
					BitHeroesBot.getInstance().run(configuration);
				} catch (Exception e) {
					System.out.println("Error occurred while starting bot thread!");
					e.printStackTrace();
				}
	        }
	    });
		bitHeroesBotThread.start();
	}
	private void runWatchdogThread() {
		if(this.watchdogThread!= null && this.watchdogThread.isAlive()) {
			this.watchdogThread.interrupt();
		}
		this.watchdogThread = new Thread(new Runnable() {
	        public void run(){
	        	try {
	        		CustomRobot customRobot = CustomRobot.getInstance();
	        		while(true) {
	        			//TODO: implement watchdog logic here...
	        			customRobot.delay(10000);
	        		}
				} catch (Exception e) {
					System.out.println("Error occurred while starting watchdog thread!");
					e.printStackTrace();
				}
	        }
	    });
		bitHeroesBotThread.start();
	}
	public void run() {
		runBitHeroesBotThread();
		runWatchdogThread();
	}
	
	public void interrupt() {
		try {
			BitHeroesBot.getInstance().stop();
			bitHeroesBotThread.interrupt();
		}catch(Exception e) {
			//...
		}
	}
	
	public void resume() {
		if(this.isAlive()) {
			bitHeroesBotThread.notifyAll();
		}
	}
	
	public void pause() throws InterruptedException {
		if(this.isAlive()) {
			bitHeroesBotThread.wait();
		}
	}
	
	private boolean isAlive() {
		return (this.bitHeroesBotThread != null && this.bitHeroesBotThread.isAlive());
	}
}
