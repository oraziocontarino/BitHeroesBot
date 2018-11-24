package be.dungeons;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.json.JSONObject;
import lib.CustomRobot;

public class Raid extends Dungeon {
	private Point raidButton;
	private Point evocaButton;
	private Dimension selectorSize;
	private Point selectorRaidListCoords;
	private int selectorPadding;
	private Color selectorColor;
	private int selectedRaid;
	public Raid() throws AWTException{
		super();
		this.selectorColor = new Color(87, 255, 87);
		this.updateJobCoords();

	}
	@Override
	public void updateConfiguration(JSONObject configuration) {
		System.out.println("Update raid config...");
		this.updateCoords(configuration);
		this.updateEnabledStatus(configuration);
		this.updateSelectedRaid(configuration);
	}
	@Override
	protected void state0() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.raidButton.x, this.raidButton.y);
		this.customRobot.sleep(1000);
		this.initRaidSelector();
		this.customRobot.sleep(250);
		
		for(int i = 1; i < selectedRaid; i++) {
			CustomRobot.getInstance().mouseClick(this.selectorNextButton.x, this.selectorNextButton.y);
			CustomRobot.getInstance().delay(250);
		}
		this.state++;
		this.customRobot.sleep(1000);
	}
	@Override
	protected void state1() throws InterruptedException, AWTException {
		this.customRobot.mouseClick(this.evocaButton.x, this.evocaButton.y);
		this.state++;
		this.customRobot.sleep(1000);
	}
	@Override
	protected void state5() throws InterruptedException, AWTException {
		System.out.println("restart! 0");
		this.state++;
		this.customRobot.sleep(1000);
	}
	@Override
	protected void updateEnabledStatus(JSONObject configuration) {
		this.enabled = configuration.getJSONObject("stack").getBoolean("raid");
	}
	@Override
	protected void main() throws Exception {
		switch(this.state){
			case 0:
				System.out.println("state 0");
				//click "Raid" button		
				this.state0();
				break;
			case 1:
				System.out.println("state 1");
				//click "Evoca" button
				this.state1();
				break;
			case 2:System.out.println("state 2");
				//click "Eroico" button
				this.state2();
				break;
			case 3:System.out.println("state 3");
				//click "Conferma squadra" button
				this.state3();
				break;
			case 4:
				System.out.println("state 4");
				//click "auto play dungeon"
				this.state4();
				break;
			case 5:
				System.out.println("test state");
				state5();
			break;
		}
		
	}

	
	private void updateSelectedRaid(JSONObject configuration) {
		try {
		this.selectedRaid = Integer.valueOf(configuration.getJSONObject("selectedRaid").getString("id").split("R")[1]);
		}catch(Exception e) {
			this.selectedRaid = 1;
		}
	}
	
	@Override
	protected void updateJobCoords() {
		super.updateJobCoords();
		this.raidButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.05)),
				(int) (this.topLeftCorner.y + (this.height*0.65))
		);
		
		this.evocaButton = new Point(
				(int) (this.topLeftCorner.x + (this.width*0.65)),
				(int) (this.topLeftCorner.y + (this.height*0.75))
		);
		this.selectorNextButton = new Point(
				(int) (topLeftCorner.x + (width*0.80)),
				(int) (topLeftCorner.y + (height*0.53))
		);
		/*
		 * TODO: prev raid TO BE DONE...
		this.selectorPrevButton = new Point(
				(int) (topLeftCorner.x + (width*0.80)),
				(int) (topLeftCorner.y + (height*0.53))
		);
		*/
		this.selectorSize = new Dimension(
				(int) (this.width*0.50),
				(int) (this.height*0.05)
		);
		
		this.selectorRaidListCoords = new Point(
				(int) (topLeftCorner.x + (width*0.25)),
				(int) (topLeftCorner.y + (height*0.82))
		);
		this.selectorSize = new Dimension(
				(int) (width*0.50),
				(int) (height*0.05)
		);
		this.selectorPadding = (int) (width*0.025);
	}


	
	private void initRaidSelector() {
		Point prevCoords = null;
		Point currentCoords = null;
		boolean checkPreviusRaid = true;
		
		try {
            do {
    			BufferedImage image = CustomRobot.getInstance().createScreenCapture(
    					new Rectangle(
    							this.selectorRaidListCoords.x, 
    							this.selectorRaidListCoords.y, 
    							this.selectorSize.width, 
    							this.selectorSize.height
    					)
    			);
            	prevCoords = currentCoords;
            	currentCoords = scanRaidSelector(image);
            	if(prevCoords != null && currentCoords != null && prevCoords.equals(currentCoords)) {
            		checkPreviusRaid = false;
            	}
            }while(checkPreviusRaid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Point scanRaidSelector(BufferedImage image) throws InterruptedException, AWTException {
		for (int y = 0; y < image.getHeight(); y++) {
        	for (int x = 0; x < image.getWidth(); x++) {
            	Color pixel = new Color(image.getRGB(x, y));
            	boolean isGreen = CustomRobot.getInstance().comparePixel(pixel, this.selectorColor, 50);
            	if(isGreen){
            		CustomRobot.getInstance().mouseClick(
            				this.selectorRaidListCoords.x + x - this.selectorPadding,
            				this.selectorRaidListCoords.y + y
            		);
            		CustomRobot.getInstance().delay(250);
            		return new Point(x, y);
            	}
            }
        }
		return null;
	}
	
}
