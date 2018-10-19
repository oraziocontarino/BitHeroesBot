

package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;

public class Test {
	
    public static void main(String... args) throws Exception {
    	Mission mission = new Mission();
    	Raid raid = new Raid();
    	System.out.println("starting mission");
    	mission.start(false);
    	System.out.println("starting raid");
    	raid.start(false);
    }

}


