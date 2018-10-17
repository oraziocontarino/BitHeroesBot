package be;

import java.awt.AWTException;

import lib.CustomRobot;

public class Test {
	static CustomRobot robot;
    public static void main(String... args) throws Exception {
    	Mission mission = new Mission();
    	mission.start();
    	//test1();
    }
    

    public static void test1() throws InterruptedException, AWTException {
        while (true) {
        	CustomRobot.getInstance().getMousePosition();
        }
    }

}