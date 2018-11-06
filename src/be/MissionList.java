package be;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;

import org.json.JSONObject;

import global.GameDimension;
import global.Utils;

public class MissionList {
	private static final String DEFAULT_KEY = "Z2D2";
	
	private static GameDimension getMissionPercentage(String key) {
		GameDimension percentage = new GameDimension();
		switch(key) {
			case "Z1D1":
				percentage.setSize(0.30, 0.70);
			break;
			case "Z1D2":
				percentage.setSize(0.73, 0.25);
			break;
			case "Z1D3":
				percentage.setSize(0.83, 0.65);
			break;
			case "Z1D4":
				percentage.setSize(0.53, 0.40);
			break;
			
			case "Z2D1":
				percentage.setSize(0.28, 0.50);
			break;
			case "Z2D2":
				percentage.setSize(0.70, 0.30);
			break;
			case "Z2D3":
				percentage.setSize(0.65, 0.77);
			break;
			case "Z2D4":
				percentage.setSize(0.50, 0.50);
			break;

			case "Z3D1":
				percentage.setSize(0.20, 0.40);
			break;
			case "Z3D2":
				percentage.setSize(0.50, 0.57);
			break;
			case "Z3D3":
				percentage.setSize(0.73, 0.70);
			break;
			case "Z3D4":
				percentage.setSize(0.75, 0.30);
			break;

			case "Z4D1":
				percentage.setSize(0.40, 0.80);
			break;
			case "Z4D2":
				percentage.setSize(0.35, 0.40);
			break;
			case "Z4D3":
				percentage.setSize(0.80, 0.40);
			break;
			case "Z4D4":
				percentage.setSize(0.50, 0.50);
			break;

			case "Z5D1":
				percentage.setSize(0.20, 0.40);
			break;
			case "Z5D2":
				percentage.setSize(0.50, 0.77);
			break;
			case "Z5D3":
				percentage.setSize(0.80, 0.43);
			break;
			case "Z5D4":
				percentage.setSize(0.70, 0.25);
			break;
			default:
				percentage = null;
		}
		return percentage;
	}
	
	public static Point getMission(JSONObject configuration) throws Exception {
		String key = configuration.getJSONObject("selectedMission").getString("id");
		
		key = key.trim().toUpperCase();
		GameDimension percentage = getMissionPercentage(key);
		if(percentage == null) {
			key = DEFAULT_KEY;
			percentage = getMissionPercentage(key);
		}
		if(percentage == null) {
			throw new Exception("INVALID MISSION KEY!");
		}
		Point topLeftCorner = Utils.getGaneTopLeftCorner(configuration);
		Point bottomRightCorner = Utils.getGameBottomRightCorner(configuration);
		int width = Utils.getGameWidth(bottomRightCorner, topLeftCorner);
		int height = Utils.getGameHeight(bottomRightCorner, topLeftCorner);
		System.out.println("key::"+key);
		System.out.println(configuration);
		return new Point(
				(int) (topLeftCorner.x + (width*percentage.getWidth())),
				(int) (topLeftCorner.y + (height*percentage.getHeight()))
		);
	}
	
}
