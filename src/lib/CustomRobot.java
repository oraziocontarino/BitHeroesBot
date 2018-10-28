package lib;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    
    public void sleep(int millis) {
    	try {
    		Thread.sleep(millis);
    	}catch(Exception e) {
    		//...
    	}
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
    
    public Point pixelSearch(int x1, int y1, int x2, int y2, Color pixelToSearch, int tolerance) {
    	int width = x2 - x1;
    	int height = y2 - y1;
        BufferedImage image = this.createScreenCapture(new Rectangle(x1, y1, width, height));
        //Color[][] colors = new Color[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (comparePixel(new Color(image.getRGB(x, y)), pixelToSearch, tolerance)){
                	//System.out.println("pixel found at COORDS: "+(x1+x)+", "+(y1+y));
                	return new Point((int)(x1+x), (int)(y1+y));
                }
            }
        }
        return null;
    }
    
    
    public boolean comparePixel(Color source, Color pixelToSearch, int tolerance) {
    	tolerance = Math.abs(tolerance);
    	
        int minR = Math.max(((int) source.getRed()) - tolerance, 0);
        int minG = Math.max(((int) source.getGreen()) - tolerance, 0);
        int minB = Math.max(((int) source.getBlue()) - tolerance, 0);
        int maxR = Math.min(((int) source.getRed()) + tolerance, 255);
        int maxG = Math.min(((int) source.getGreen()) + tolerance, 255);
        int maxB = Math.min(((int) source.getBlue()) + tolerance, 255);
    	
        if( 
        	colorInRange(pixelToSearch.getRed(), minR, maxR) &&
        	colorInRange(pixelToSearch.getGreen(), minG, maxG) &&
        	colorInRange(pixelToSearch.getBlue(), minB, maxB)
        ) {
        	//System.out.println("COLOR FOUND!");
        	return true;
        }
    	return false;
    }
    
    private boolean colorInRange(double colorComponent, double minRange, double maxRange) {
    	return (colorComponent >= minRange && colorComponent <= maxRange);
    }
}
