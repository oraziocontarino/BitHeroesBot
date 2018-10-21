

package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;

public class Test {
	
    public static void main(String... args) throws Exception {
    	int config = 0;
    	Point[] coords = new Point[2];
    	
		if(config == 0) {
			//HOST
			coords[0] = new Point(17, 454);
			coords[1] = new Point(817, 976);
		}else if(config == 1) {
			//VM
			coords[0] = new Point(13, 391);
			coords[1] = new Point(819, 910);
		}
		
    	BitHeroesBot.getInstance(coords).run();
    	//System.exit(1);
    }

}


