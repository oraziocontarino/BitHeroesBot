package be;

import java.awt.Point;
import java.util.HashMap;

public class MissionList {
	private HashMap<String, Point> missionList = new HashMap<String, Point>();
	private Point topLeftCorner;
	private int width;
	private int height;
	private String defaultKey;
	public MissionList(Point topLeftCorner, int width, int height) {
		this.topLeftCorner = topLeftCorner;
		this.width = width;
		this.height = height;
		this.setDefaultKey("Z2D2");
		
		this.missionList.put(
				"Z2D2", 
				new Point(
					(int) (this.topLeftCorner.x + (this.width*0.30)),
					(int) (this.topLeftCorner.y + (this.height*0.50))
				)
		);
		
		this.missionList.put(
				"Z1D2", 
				new Point(
					(int) (this.topLeftCorner.x + (this.width*0.73)),
					(int) (this.topLeftCorner.y + (this.height*0.25))
				)
		);
	}
	private void setDefaultKey(String key) {
		this.defaultKey = key.trim().toUpperCase();
	}
	public Point getMission(String key) {
		if(key == null) {
			key = this.defaultKey;
		}else {
			key = key.trim().toUpperCase();
			if(!this.missionList.containsKey(key)) {
				key = this.defaultKey;
			}
		}
		return this.missionList.get(key);
	}
	
}
