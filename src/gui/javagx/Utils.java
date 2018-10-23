package gui.javagx;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import lib.CustomRobot;

public class Utils {
    
    public static Point[] detectGamePoistion() throws AWTException{
        System.out.println("Loading game position...");
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int width = (int) screenSize.getWidth();
    	int height = (int) screenSize.getHeight();
    	Color borderColor = new Color(0,0,0);
    	int tolerance = 0;
    	Point[] coords = new Point[2];
    	int patternSize = 10;
    	
        BufferedImage image = CustomRobot.getInstance().createScreenCapture(new Rectangle(0, 0, width, height));
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
            	if(coords[0] == null){
            		coords[0] = calculateTopLeftMatrix(patternSize, x, y, borderColor, tolerance, image);
            	}else if(coords[1] == null){
            		coords[1] = calculateBottomRightMatrix(patternSize, x, y, borderColor, tolerance, image);
            	}
            }
        }
        /*
        if(coords[0] != null) {
        	System.out.println("Found topLeft! x:"+coords[0].x+"; y:"+coords[0].y);
        }
        if(coords[1] != null) {
        	System.out.println("Found bottomRight! x:"+coords[1].x+"; y:"+coords[1].y);
        }
        */
        System.out.println("Done!");
        return coords;
       }
    
    private static Point calculateTopLeftMatrix(int patternSize, int x, int y, Color borderColor, int tolerance, BufferedImage image) {
    	Boolean[][] topLeftCorner = new Boolean[patternSize][patternSize];
    	Point coords = null;
    	try {
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				if(i == 0) {
    					//i=0, first row is black!
    					topLeftCorner[i][j] = CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
    				}else if(j == 0) {	
    					//i>0, first element is black!
						topLeftCorner[i][j] = CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);	
    				}else {
    					//i>0, j>0, should not find black
						topLeftCorner[i][j] = !CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
					}
    			}
    		}
    		
    		boolean success = true;
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				success = success && topLeftCorner[i][j];
    			}
    		}
    		
    		if(success) {
    			coords = new Point(x,y);
    		}
    	}catch(Exception e) {
    		//Out of bound, ignore it
    	}
    	return coords;
    }
    
    private static Point calculateBottomRightMatrix(int patternSize, int x, int y, Color borderColor, int tolerance, BufferedImage image) {
    	Boolean[][] bottomRightCorner = new Boolean[patternSize][patternSize];
    	Point coords = null;
    	int badPixel = 0;
    	try {
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				if(i == patternSize - 1) {
    					//i = patternSize-1, last row is black!
    					bottomRightCorner[i][j] = CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);
    				}else if(j == patternSize - 1) {	
    					//i < patternSize, last element is black!
    					bottomRightCorner[i][j] = CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance);	
    				}else {
    					//i < patternSize, j < patternSize, should not find black
    					if(CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x+i, y+j)), borderColor, tolerance)) {
    						badPixel ++;
    					}
    					bottomRightCorner[i][j] = true;
    				}
    			}
    		}
    		
    		boolean success = true;
    		for(int i = 0; i < patternSize; i++) {
    			for(int j = 0; j < patternSize; j++) {
    				success = success && bottomRightCorner[i][j];
    			}
    		}
    		
    		if(success && badPixel < 4) {
    			coords = new Point(x+patternSize,y+patternSize);
    			//System.out.println("BAD PIXEL:"+badPixel);
    		}
    	}catch(Exception e) {
    		//Out of bound, ignore it
    	}
    	return coords;
    }
    
   
}
