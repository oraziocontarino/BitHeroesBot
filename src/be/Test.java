package be;

import java.awt.AWTException;
import java.awt.Point;

import lib.CustomRobot;

public class Test {
	
    public static void main(String... args) throws Exception {
    	Point location = new Point(4, 13);
    	Mission mission = new Mission();
    	mission.start();
    }

}