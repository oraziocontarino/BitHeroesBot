package global;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import org.json.JSONObject;

import lib.CustomRobot;

public class Utils {

	public static void test(JSONObject configuration) {
		try {
			//DO NOT DELETE THIS BLOCK
			Point topLeftCorner = getGameTopLeftCorner(configuration);
			Point bottomRightCorner = getGameBottomRightCorner(configuration);
			int width = getGameWidth(topLeftCorner, bottomRightCorner);
			int height = getGameHeight(topLeftCorner, bottomRightCorner);
			//END OF BLOCK

			long start = Calendar.getInstance().getTimeInMillis();
			Point[] test1 = detectGamePoistion();
			long delta = Calendar.getInstance().getTimeInMillis() - start;
			System.out.println("TEST 1 TIME: "+delta);
			System.out.println("TEST 1 POINT[0]: "+test1[0]);
			System.out.println("TEST 1 POINT[1]: "+test1[1]);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static Point[] detectGamePoistion() throws AWTException {
		System.out.println("Loading game position...");
		long start = Calendar.getInstance().getTimeInMillis();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		Color borderColor = new Color(0, 0, 0);
		int tolerance = 0;
		Point[] coords = new Point[2];
		int patternSize = 10;

		BufferedImage image = CustomRobot.getInstance().createScreenCapture(new Rectangle(0, 0, width, height));
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (coords[0] == null) {
					coords[0] = calculateTopLeftMatrix(patternSize, x, y, borderColor, tolerance, image);
				} else if (coords[1] == null  && isBlack(x + patternSize - 1, y + patternSize - 1, borderColor, tolerance, image)) {
					coords[1] = calculateBottomRightMatrix(patternSize, x, y, borderColor, tolerance, image);
					if (coords[1] != null && Math.abs(coords[1].y - coords[0].y) < 100) {
						coords[1] = null;
					}
				}
			}
		}
		/*
		 * if(coords[0] != null) {
		 * System.out.println("Found topLeft! x:"+coords[0].x+"; y:"+coords[0].y); }
		 * if(coords[1] != null) {
		 * System.out.println("Found bottomRight! x:"+coords[1].x+"; y:"+coords[1].y); }
		 */
		long delta = Calendar.getInstance().getTimeInMillis() - start;
		System.out.println("Done in: "+delta+" ms");
		return coords;
	}
	private static boolean isBlack(int x, int y, Color borderColor, int tolerance, BufferedImage image) {
		try {
			return CustomRobot.getInstance().comparePixel(new Color(image.getRGB(x, y)), borderColor, tolerance);
		} catch (Exception e) {
			return false;
		}
	}

	private static Point calculateTopLeftMatrix(int patternSize, int x, int y, Color borderColor, int tolerance,
			BufferedImage image) {
		//Boolean[][] topLeftCorner = new Boolean[patternSize][patternSize];
		Point coords = null;
		boolean success = false;
		try {
			int localX, localY;
			for (int i = 0; i < patternSize; i++) {
				for (int j = 0; j < patternSize; j++) {
					localX = x+i;
					localY = y+j;
					if (i == 0) {
						// i=0, first row is black!
						success = isBlack(localX, localY, borderColor, tolerance, image);
					} else if (j == 0) {
						// i>0, first element is black!
						success = isBlack(localX, localY, borderColor, tolerance, image);
					} else {
						// i>0, j>0, should not find black
						success = !isBlack(localX, localY, borderColor, tolerance, image);
					}
					if(!success) {
						return null;
					}
				}
			}
			coords = new Point(x, y);
		} catch (Exception e) {
			// Out of bound, ignore it
		}
		return coords;
	}

	private static Point calculateBottomRightMatrix(int patternSize, int x, int y, Color borderColor, int tolerance,
			BufferedImage image) {
		Point coords = null;
		int badPixel = 0;
		boolean success = false;
		try {
			int localX, localY;
			for (int i = 0; i < patternSize; i++) {
				for (int j = 0; j < patternSize; j++) {
					localX = x+i;
					localY = y+j;
					if (i == patternSize - 1) {
						// i = patternSize-1, last row is black!
						success = isBlack(localX, localY, borderColor, tolerance, image);
					} else if (j == patternSize - 1) {
						// i < patternSize, last element is black!
						success = isBlack(localX, localY, borderColor, tolerance, image);
					} else {
						// i < patternSize, j < patternSize, should not find black
						if (isBlack(localX, localY, borderColor, tolerance, image)) {
							badPixel++;
						}
						success = true;
					}
					if(!success || badPixel >= 4) {
						return null;
					}
				}
			}

			coords = new Point(x + patternSize, y + patternSize);

		} catch (Exception e) {
			// Out of bound, ignore it
		}
		return coords;
	}
	public static JSONObject getDefaultConfiguration() {

		JSONObject error = new JSONObject()
				.put("coords", false)
				.put("mission", false)
				.put("raid", false);

		JSONObject stack = new JSONObject()
				.put("mission", true)
				.put("raid", true)
				.put("trial", false)
				.put("gauntlet", false);

		JSONObject topLeft = new JSONObject()
				.put("x", 0)
				.put("y", 0);

		JSONObject bottomRight = new JSONObject()
				.put("x", 0)
				.put("y", 0);

		JSONObject selectedMission = getSelectedMissionConfigurationNode("Z1D1", "Z1D1");

		JSONObject selectedRaid = new JSONObject()
				.put("label", "R1 - Astorath")
				.put("id", "R1");
		
		JSONObject trial = new JSONObject()
				.put("cost", 5)
				.put("difficulty", 1);
		
		JSONObject gauntlet = new JSONObject()
				.put("cost", 5)
				.put("difficulty", 1);
		
		JSONObject delays = new JSONObject()
				.put("round", 0);
		
		return new JSONObject()
				.put("error", error)
				.put("stack", stack)
				.put("topLeft", topLeft)
				.put("bottomRight", bottomRight)
				.put("selectedMission", selectedMission)
				.put("selectedRaid", selectedRaid)
				.put("trial", trial)
				.put("gauntlet", gauntlet)
				.put("delays", delays);
	}

	public static JSONObject getSelectedMissionConfigurationNode(String label, String id) {
		return new JSONObject().put("label", label).put("id", id);
	}

	public static Point getGameTopLeftCorner(JSONObject configuration) {
		return new Point(configuration.getJSONObject("topLeft").getInt("x"),
				configuration.getJSONObject("topLeft").getInt("y"));
	}

	public static Point getGameBottomRightCorner(JSONObject configuration) {
		return new Point(configuration.getJSONObject("bottomRight").getInt("x"),
				configuration.getJSONObject("bottomRight").getInt("y"));
	}

	public static int getGameWidth(Point topLeftCorner, Point bottomRightCorner) {
		return bottomRightCorner.x - topLeftCorner.x;
	}

	public static int getGameHeight(Point topLeftCorner, Point bottomRightCorner) {
		return bottomRightCorner.y - topLeftCorner.y;
	}

	public static Point getGameCenter(Point topLeftCorner, int width, int height) {
		return new Point((int) (topLeftCorner.x + (width * 0.50)), (int) (topLeftCorner.y + (height * 0.50)));
	}
}
