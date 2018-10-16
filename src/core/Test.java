package core;

import java.awt.AWTException;

public class Test {
	static CustomRobot robot;
    public static void main(String... args) throws Exception {
    	 test1();
    }
    

    public static void test1() throws InterruptedException, AWTException {
        while (true) {
        	CustomRobot.getInstance().getMousePosition();
        }
    }

}