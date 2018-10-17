package lib;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CustomRobot extends Robot{
	private static CustomRobot instance;
	  
	private CustomRobot() throws AWTException {
		super();
		KeyBindingManager.getInstance();
		
	}
    
	public static CustomRobot getInstance() throws AWTException{
        if(instance == null){
            instance = new CustomRobot();
        }
        return instance;
    }
	
	public void getMousePosition() {
    	Point pos = MouseInfo.getPointerInfo().getLocation();
    	Color pixel = this.getPixelColor((int) pos.getX(), (int) pos.getY());
    	System.out.println("["+pos.getX()+":"+pos.getY()+"] - ["+pixel.getRed()+":"+pixel.getGreen()+":"+pixel.getBlue()+"]");
    }

    public void mouseClick(int x, int y) throws InterruptedException {
    	this.mouseMove(x, y);
    	this.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    	this.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    public void send(String key) throws InterruptedException {
    	Integer keyCode = null;
    	key = key.toLowerCase().trim();
    	switch(key) {
    		case "{enter}":
    			keyCode = KeyEvent.VK_ENTER;
    		break;
    		case "{esc}":
    			keyCode = KeyEvent.VK_ESCAPE;
    	}
    	this.keyPress(keyCode);
    	this.keyRelease(keyCode);
    }
    
    public void sleep(int millis) throws InterruptedException {
        Thread.sleep(millis);
    }
    
    public void test() {
    	try {
    	BufferedImage image = this.createScreenCapture(new Rectangle(0, 0, 400, 400));
    	File outputfile = new File("E:\\Desktop\\Screen.jpg");
    	ImageIO.write(image, "jpg", outputfile);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		//...
    	}
    }
    
    public boolean pixelSearch(int x1, int y1, int x2, int y2, Color pixel) {
    	int width = x2 - x1;
    	int height = y2 - y1;
        BufferedImage image = this.createScreenCapture(new Rectangle(x1, y1, width, height));
        //Color[][] colors = new Color[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if ((new Color(image.getRGB(x, y))).equals(pixel)){
                	return true;
                }
            }
        }
        return false;
    }
    
}
